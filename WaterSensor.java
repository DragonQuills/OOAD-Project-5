class WaterSensor{
// attributes
  // private WaterReservoir res;
  private float current_humidity;
  private Command hose_on;
  private Command hose_off;
// methods
  // PlantPot will take a reading, then water if the humidity is below the min soil humitity.
  // PlantPot will continue to take a reading, then water until the level is at the desired humidity.
  public float take_reading(){
    return current_humidity;
  }
  public void water_plant(){
    current_humidity += 5;

    // In a real greenhouse system, this would water for a certain amount of time
    // which would determine how much water the plant gets.
    // For this example, we're just having a constant amount of water.
    hose_on.execute();
    hose_off.execute();
  }
// constructor
  public WaterSensor(/*WaterReservoir new_res,*/ Command new_hose_on, Command new_hose_off){
    // res = new_res;
    hose_on = new_hose_on;
    hose_off = new_hose_off;
  }
}
