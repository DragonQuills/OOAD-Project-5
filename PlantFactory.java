import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


class PlantFactory{
// methods
  public PlantPot get_plant(String name, String type){
    PlantPot plantPot;
    data = query_csv(type);
    plantPot.min_soil_humidity = data[0];
    plantPot.max_soil_humidity = data[1];
    plantPot.min_Temp = data[2];
    plantPot.max_Temp = data[3];

  }
  private PlantPot query_csv(String type){


    String csvFile = "fake_plant_data.csv";
    String line = "";
    String delimiter = ",";

    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))){
      while ((line = br.readLine()) != null) {
        String[] data = line.split(delimiter);
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
//I was not sure how we plan to handle the plant type input
//this seems like one possible way
  public PlantFactory(){

  }
}
