class WaterReservoir{
//attributes
  public String name;

  private float max_capacity;
  private float current_water;
  private float warning_level;
//methods
  // boolean so the plant can tell if watering was successful
  public boolean water_used(float amount_used){
    current_water -= amount_used;
    if(current_water <= 0){
      current_water = 0;
      return false;
    }
    else{
      return true;
    }
  }
  public void water_refilled(){
    current_water = max_capacity;
  }
  public boolean refill_needed(){
    return current_water<=warning_level;
  }

  public String report_current_level(){
    String report = "Current level is " + current_water + " oz. ";
    if (current_water <= warning_level){
      report += "This is below the warning level, please refill immediately.";
    }
    return report;
  }

//constructor
  public WaterReservoir(float max, float warning){
    max_capacity = max;
    current_water = max;
    warning_level = warning;
  }
}
