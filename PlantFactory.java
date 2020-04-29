import java.io.*;
import java.util.Arrays;
import java.util.ArrayList;

//Factory Pattern used to create new plants according to ideal specifications
class PlantFactory{
// methods
//get_plant() creates a new PlantPot object using data found by query_csv()
  public PlantPot get_plant(String name, String type){
    PlantPot plantPot = new PlantPot(name, type);
    ArrayList<String> data = query_csv(type);
    if (data.size() > 0){
      plantPot.set_min_soil_humidity(Float.parseFloat(data.get(1)));
      plantPot.set_desired_soil_humidity(Float.parseFloat(data.get(0)));
      plantPot.set_min_temp(Float.parseFloat(data.get(3)));
      plantPot.set_max_temp(Float.parseFloat(data.get(4)));

      Light new_light = new Light();
      Command light_on = new LightOnCommand(new_light);
      Command light_off = new LightOffCommand(new_light);
      Timer new_timer = new Timer(light_on, light_off, Integer.parseInt(data.get(2)));
      plantPot.set_timer(new_timer);
    }
    return plantPot;
  }

//query_csv() pulls data from our database of ideal growing conditions for various plant types
//Future iterations of this project could replace this with a query_api() function in order to provide more plant type options.
  private ArrayList<String> query_csv(String type){

    File csvFile = new File("./fake_plant_data.csv");
    String line = "";
    String delimiter = ",";

    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))){
      while ((line = br.readLine()) != null) {
        String[] arr = line.split(delimiter);
        String[] return_data = new String[5];
        if (arr[0].equals(type)){
          return_data = Arrays.copyOfRange(arr,1,6);
          ArrayList<String> data = new ArrayList<String>(Arrays.asList(return_data));
          return data;
        }

      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    ArrayList<String> empty = new ArrayList<String>();
    return empty;
  }

//conditions_ok_for_plant() determines if the selected room is safe for a certain plant
  public boolean conditions_ok_for_plant(PlantPot p, Room r){
    if (r.get_lowest_temp() > p.get_min_temp() && r.get_highest_temp() < p.get_max_temp()){
      return true;
    }
    else {
      return false;
    }
  }

// default constructor
  public PlantFactory(){

  }
}
