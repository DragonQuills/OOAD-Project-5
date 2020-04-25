import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;


class PlantFactory{
// methods
  public PlantPot get_plant(String name, String type){
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

    String csvFile = "fake_plant_data.csv";
    String line = "";
    String delimiter = ",";

    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))){
      while ((line = br.readLine()) != null) {
        String[] data = line.split(delimiter);
        String[] return_data = new String[5];
        if (data[0] == type){
          return_data = Arrays.copyOfRange(data,1,5);
        }
        return return_data;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }


  }

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
