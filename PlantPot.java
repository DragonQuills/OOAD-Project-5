class PlantPot{
//attributes
  public String name;
  public String plant_type;
  public int id;

  private Light light;
  private WaterReservoir res;
  private Timer timer;
  private WaterSensor water_sensor;
  private float desired_soil_humidity; // this determines how much the plant will be watered
  private float min_soil_humidity; //this is the level at or below which the plant will be watered
  private float max_temp;
  private float min_temp;
  private StorageHandler store;

//methods
  public void set_min_soil_humidity(float new_min){
    min_soil_humidity = new_min;
  }
  public float get_min_soil_humidity(){
    return min_soil_humidity;
  }
  public void set_desired_soil_humidity(float new_humid){
    desired_soil_humidity = new_humid;
  }
  public float get_desired_soil_humidity(){
    return desired_soil_humidity;
  }

  //temp getter and setter functions for PlantFactory.conditions_ok_for_plant() and PlantFactory.get_plant()
  public void set_min_temp(float new_min_temp){
    min_temp = new_min_temp;
  }
  public float get_min_temp(){
    return min_temp;
  }
  public void set_max_temp(float new_max_temp){
    max_temp = new_max_temp;
  }
  public float get_max_temp(){
    return max_temp;
  }

  public float get_light_hours(){
    return timer.get_hours_on();
  }

  public WaterReservoir get_res(){
    return res;
  }

  //this doesn't change any of Plant's variables, it effects the Timer.
  public void set_light_hours(int new_hours_on){
    timer.set_hours_on(new_hours_on);
  }
  public void set_timer(Timer t){
    timer = t;
  }

  //this checks the water sensor and then tells it to water the plant if the water level is too low
  public void check_water(){
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
  public void hour_passed(){
    System.out.println("Passing time for " + name);
    timer.hour_passed();
    water_sensor.hour_passed();
    store.humidityReading(id, water_sensor.get_current_humidity());
    check_water();
    System.out.println("");
  }

  // Gives a staus report to the Room to print for the User
  public String status_report(){
    String status = name + " the " + plant_type + " plant";
    status += " has " + water_sensor.get_current_humidity() + " oz of water";
    status += " and the light is currently ";
    boolean light_on = timer.get_light_status();
    if (light_on){
      status += "on.";
    }
    else{
      status += "off.";
    }
    return status;
  }

//constructor
  //The constructor is very light because the factory will talk to a mock API to get
  // most of the data, the user only needs to enter the name and type of plant
  // normally.
  public PlantPot(String new_name, String new_plant_type){
      name = new_name;
      plant_type = new_plant_type;
      store = StorageHandler.getInstance();
  }

  //constructor used when creating a plant from the database
  public PlantPot(int newId, WaterReservoir newRes, String newName, String type, Float desiredHumidity, Float minHumidity, Float maxTemp, Float minTemp, Float currentHumidity){
    id = newId;
    res = newRes;
    name = newName;
    plant_type = type;
    desired_soil_humidity = desiredHumidity;
    min_soil_humidity = minHumidity;
    max_temp = maxTemp;
    min_temp = minTemp;
    
    store = StorageHandler.getInstance();
    water_sensor.set_current_humidity(currentHumidity);
  }
}
