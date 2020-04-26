import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
<<<<<<< HEAD
=======
import java.util.Arrays;
>>>>>>> master


class PlantFactory{
// methods
  public PlantPot get_plant(String name, String type){
<<<<<<< HEAD
    PlantPot plantPot;
    data = query_csv(type);
    plantPot.min_soil_humidity = data[0];
    plantPot.max_soil_humidity = data[1];
    plantPot.min_Temp = data[2];
    plantPot.max_Temp = data[3];

  }
  private PlantPot query_csv(String type){

=======
    PlantPot plantPot = new PlantPot(name, type);
    String[] data = query_csv(type);
    plantPot.set_min_soil_humidity(Float.parseFloat(data[0]));
    plantPot.set_desired_soil_humidity(Float.parseFloat(data[1]));
    plantPot.set_light_hours(Integer.parseInt(data[2]));
    plantPot.set_min_temp(Float.parseFloat(data[3]));
    plantPot.set_max_temp(Float.parseFloat(data[4]));
    return plantPot;
  }

  private String[] query_csv(String type){
>>>>>>> master

    String csvFile = "fake_plant_data.csv";
    String line = "";
    String delimiter = ",";

    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))){
      while ((line = br.readLine()) != null) {
        String[] data = line.split(delimiter);
<<<<<<< HEAD
        if (data[0] == type){
          String max_soil_humidity = data[1];
          String min_soil_humidity = data[2];
          String min_temp = data[3];
          String max_Temp = data[4];
          ArrayList<String> return_data = new ArrayList<String>();
          return_data.add(min_soil_humidity);
          return_data.add(max_soil_humidity);
          return_data.add(min_temp);
          return_data.add(max_temp);
          return return_data;
        }

=======
        String[] return_data = new String[5];
        if (data[0] == type){
          return_data = Arrays.copyOfRange(data,1,5);
          return return_data;
        }
        
>>>>>>> master
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

<<<<<<< HEAD
  }
  public boolean conditions_ok_for_plant(PlantPot p, Room r){
    if (r.lowest_temp > p.min_temp && r.highest_temp < p.max_temp){
=======
    String[] empty = new String[0];
    return empty;
  }

  public boolean conditions_ok_for_plant(PlantPot p, Room r){
    if (r.get_lowest_temp() > p.get_min_temp() && r.get_highest_temp() < p.get_max_temp()){
>>>>>>> master
      return true;
    }
    else {
      return false;
    }
<<<<<<< HEAD

  }
// constructor
//I was not sure how we plan to handle the plant type input
//this seems like one possible way
=======
  }

// default constructor
>>>>>>> master
  public PlantFactory(){

  }
}
