class WaterReservoir{
//attributes
  public String name;
  public int id;

  private float max_capacity;
  private float current_water;
  private float warning_level;
  private StorageHandler store;
//methods

  // lowers the leve of water in the reservoir
  public void water_used(float amount_used){
    current_water -= amount_used;
    store.storeWaterLevel(id, current_water);
    if(current_water <= 0){
      current_water = 0;
    }
  }

  // used when the user refills the water
  public void water_refilled(){
    current_water = max_capacity;
    store.storeWaterLevel(id, current_water);
  }

  public boolean refill_needed(){
    return current_water<=warning_level;
  }

  public String status_report(){
    String report = "Reservoir " + name + "'s current level is " + current_water + " oz. ";
    if (refill_needed()){
      report += "This is below the warning level, please refill immediately.";
    }
    return report;
  }

  public Float get_max_capacity(){
    return max_capacity;
  }

  public Float get_warning_level(){
    return warning_level;
  }

//constructor
  public WaterReservoir(String new_name, float max, float warning){
    name = new_name;
    max_capacity = max;
    current_water = max;
    warning_level = warning;
  }

  //constructor w/ id
  public WaterReservoir(String new_name, float max, float warning, int newId){
    name = new_name;
    max_capacity = max;
    current_water = max;
    warning_level = warning;
    id = newId;
    store = StorageHandler.getInstance();
  }

  //Constructor for use when building from database
  public WaterReservoir(String new_name, float max, float warning, int inputId, float current){
    name = new_name;
    max_capacity = max;
    current_water = max;
    warning_level = warning;
    current_water = current;
    id = inputId;
    store = StorageHandler.getInstance();
  }
}
