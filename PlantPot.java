class PlantPot{
//attributes
  public String name;
  public String plant_type;
  private Light light;
  private Hose hose;
  private WaterReservoir res;
  private Timer timer;
  private WaterSensor water_sensor;
  private int desired_soil_humidity; // this determines how much the plant will be watered
  private int min_soil_humidity; //this is the level at or below which the plant will be watered

//methods
  public void set_min_soil_humidity(){}
  public void set_amount_to_water(){}
  public void set_light_hours(){} //this doesn't change any of Plant's variables, it effects the Timer.
  public void check_water(){} //this checks the water sensor and then tells it to water the plant if the water level is too low
  public void set_water_reservoir(WaterReservoir res){}
//constructor
  public PlantPot(String name, String plant_type){}
}
