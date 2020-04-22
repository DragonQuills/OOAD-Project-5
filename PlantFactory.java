import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


class PlantFactory{
// methods
  public PlantPot get_plant(String name, String type){
    PlantPot plantPot;
    plantPot.min_soil_humidity = query_api(type)[0];
    plantPot.max_soil_humidity = query_api(type)[1];

  }
  private PlantPot query_api(String type){


    String csvFile = "./fake_plant_data.csv";
    String line = "";
    String delimiter = ",";

    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))){
      while ((line = br.readLine()) != null) {
        String[] data = line.split(delimiter);
        if data[0] == type{
          String light_time = data[1];
          String max_soil_humidity = data[2];
          String min_soil_humidity = data[3];
          String min_temp = data[4];
          String max_Temp = data[5];
          return [min_soil_humidity,max_soil_humidity];
        }

      }
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
  public boolean conditions_ok_for_plant(PlantPot p, Room r){
    if (r.lowest_temp > p.min_temp && r.highest_temp < p.max_temp){
      return True;
    }
    else {
      return False;
    }

  }
// constructor
//I was not sure how we plan to handle the plant type input
//this seems like one possible way
  public PlantFactory(){
    S
  }
}
