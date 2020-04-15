class PlantPot{
//attributes
  public String name;
  public String plant_type;

  private Light light;
  private Hose hose;
  private WaterReservoir res;
  private Timer timer;
  private WaterSensor water_sensor;
  private float desired_soil_humidity; // this determines how much the plant will be watered
  private float min_soil_humidity; //this is the level at or below which the plant will be watered

//methods
  public void set_min_soil_humidity(float new_min){
    min_soil_humidity = new_min;
  }
  public void set_desired_humidity(float new_humid){
    desired_soil_humidity = new_humid;
  }
  public void set_light_hours(){} //this doesn't change any of Plant's variables, it effects the Timer.

  //this checks the water sensor and then tells it to water the plant if the water level is too low
  public void check_water(){
    float current_water_level = water_sensor.take_reading();
    if(current_water_level < min_soil_humidity){
      while(current_water_level < desired_soil_humidity){
        water_sensor.water_plant();
        current_water_level = water_sensor.take_reading();
      }
    }
  }

  public void set_water_reservoir(WaterReservoir new_res){
    res = new_res;
    hose = new Hose(res);
    Command hose_on = new WaterOnCommand(hose);
    Command hose_off = new WaterOffCommand(hose);
    water_sensor = new WaterSensor(hose_on, hose_off);
  }

//constructor
  public PlantPot(String new_name, String new_plant_type){
      name = new_name;
      plant_type = new_plant_type;
  }
}
