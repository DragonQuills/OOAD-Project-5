class WaterSensor{
// attributes
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
    current_humidity += 10;

    // In a real greenhouse system, this would water for a certain amount of time
    // which would determine how much water the plant gets.
    // For this example, we're just having a constant amount of water.
    hose_on.execute();
    hose_off.execute();
  }

  // Used for the PlantPot status report
  public float get_current_humidity(){
    return current_humidity;
  }

  public void set_current_humidity(float new_current_humidity){
    current_humidity = new_current_humidity;
  }
  // normally this would be actual time passing and the water level lowering
  // but sicne we have no actual hardware we're simulating it this way.
  public void hour_passed(){
    current_humidity -= 1;
    if(current_humidity < 0){
      current_humidity = 0;
    }
  }
// constructor
  public WaterSensor(Command new_hose_on, Command new_hose_off){
    hose_on = new_hose_on;
    hose_off = new_hose_off;
    current_humidity = 0;
  }
}
