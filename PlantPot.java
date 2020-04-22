class PlantPot{
//attributes
  public String name;
  public String plant_type;

  private Light light;
  // private Hose hose;
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
  public void set_light_hours(){ //this doesn't change any of Plant's variables, it effects the Timer.


  }

  public void set_timer(Timer t){
    timer = t;
  }

  //this checks the water sensor and then tells it to water the plant if the water level is too low
  public void check_water(){
    System.out.println("Checking water for " + name + "...");
    float current_water_level = water_sensor.take_reading();
    if(current_water_level < min_soil_humidity){
      while(current_water_level < desired_soil_humidity){
        System.out.println("Water is too low at " + current_water_level + ", watering plant.");
        water_sensor.water_plant();
        current_water_level = water_sensor.take_reading();
      }
      System.out.println("Water is now at " + current_water_level + ", finished watering.");
    }
  }

  // this connects the plant to a new water reservoir and sets up the sensors and hose and whatnot
  public void set_water_reservoir(WaterReservoir new_res){
    res = new_res;
    Hose hose = new Hose(res);
    Command hose_on = new WaterOnCommand(hose);
    Command hose_off = new WaterOffCommand(hose);
    water_sensor = new WaterSensor(hose_on, hose_off);
  }

  // Used to make water "evaporate" and turn the light on or off.
  public void time_passes(int hours){
    for(int i = 0; i < hours; i++){
      timer.hour_passed();
      water_sensor.hour_passed();
    }
  }
//constructor
  public PlantPot(String new_name, String new_plant_type){
      name = new_name;
      plant_type = new_plant_type;
  }
}
