import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;


class PlantFactory{
// methods
  public PlantPot get_plant(String name, String type){
    PlantPot plantPot = new PlantPot(name, type);
    String[] data = query_csv(type);
    plantPot.set_min_soil_humidity(Float.valueOf(data[0]));
    plantPot.set_desired_soil_humidity(Float.valueOf(data[1]));
    plantPot.set_light_hours(Float.valueOf(data[2]));
    plantPot.set_min_temp(Float.valueOf(data[3]));
    plantPot.set_max_temp(Float.valueOf(data[4]));
    return plantPot;
  }
  private Float[] query_csv(String type){

    String csvFile = "fake_plant_data.csv";
    String line = "";
    String delimiter = ",";

    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))){
      while ((line = br.readLine()) != null) {
        float[] data = line.split(delimiter);
        if (data[0] == type){
          float[] return_data = copyOfRange(data,1,4);
          return return_data;
        }

      }
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
  public boolean conditions_ok_for_plant(PlantPot p, Room r){
    if (r.lowest_temp > p.min_temp && r.highest_temp < p.max_temp){
      return true;
    }
    else {
      return false;
    }

  }
// constructor

  public PlantFactory(String name, String type){
    return get_plant(name, type)
  }
}
