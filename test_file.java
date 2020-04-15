class test_file{
  public static void main(String args[]){
    WaterReservoir res = new WaterReservoir("Reservoir 1", 100, 20);

    // System.out.println(res.water_used(3));
    // System.out.println(res.water_used(3));
    // System.out.println(res.refill_needed());
    // System.out.println(res.report_current_level());
    //
    // // res.water_refilled();
    //
    // Hose hose = new Hose(res);
    // Command hose_on = new WaterOnCommand(hose);
    // Command hose_off = new WaterOffCommand(hose);
    //
    // WaterSensor sensor = new WaterSensor(hose_on, hose_off);
    //
    // sensor.water_plant();

    PlantPot rosemary = new PlantPot("Rosemary1", "rosemary");
    rosemary.set_water_reservoir(res);
    rosemary.set_min_soil_humidity(10);
    rosemary.set_desired_humidity(50);
    rosemary.check_water();
  }
}
