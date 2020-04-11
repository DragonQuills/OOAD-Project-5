class WaterSensor{
// attributes
  private WaterReservoir res;
  private Command hose_on;
  private Command hose_off;
// methods
  // PlantPot will take a reading, then water if the humidity is below the min soil humitity.
  // PlantPot will continue to take a reading, then water until the level is at the desired humidity.
  public void take_reading(){}
  public void water_plant(){}
// constructor
  public WaterSensor(WaterReservoir res, Command hose_on, Command hose_off){}
}
