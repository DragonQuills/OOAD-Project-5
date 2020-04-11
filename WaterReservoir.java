class WaterReservoir{
//attributes
  public String name;

  private float max_capacity;
  private float current_water;
  private float warning_level;
//methods
  public void water_used(float amount_used){}
  public void water_refilled(){}
  public boolean refill_needed(){return current_water<=warning_level;}

  //returns a string like "Current level is 52 oz, plenty left" or "Current level is 6 oz, refill needed!"
  public String report_current_level(){return "";}
//constructor
  public WaterReservoir(float max, float warning_level){}
}
