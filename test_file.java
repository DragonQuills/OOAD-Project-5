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

    Light rosemary_light = new Light();
    Command light_on = new LightOnCommand(rosemary_light);
    Command light_off = new LightOffCommand(rosemary_light);

    Timer rosemary_timer = new Timer(light_on, light_off, 8);

    rosemary.set_timer(rosemary_timer);

    for(int i = 0; i < 48; i++){
      int time = i%24;
      System.out.println("It is currently " + time + " o'clock.");
      rosemary.time_passes(1);
      rosemary.check_water();
    }
  }
}
