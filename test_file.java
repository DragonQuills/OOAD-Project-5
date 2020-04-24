class test_file{
  public static void main(String args[]){
    // test_single_plant();
    // test_temp_sensor();
    test_room();
  }

  public static void test_single_plant(){
    WaterReservoir res = new WaterReservoir("Reservoir 1", 100, 20);

    PlantPot rosemary = new PlantPot("Rosemary1", "rosemary");
    rosemary.set_water_reservoir(res);
    rosemary.set_min_soil_humidity(10);
    rosemary.set_desired_humidity(50);
    // rosemary.check_water();

    Light rosemary_light = new Light();
    Command light_on = new LightOnCommand(rosemary_light);
    Command light_off = new LightOffCommand(rosemary_light);

    Timer rosemary_timer = new Timer(light_on, light_off, 8);

    rosemary.set_timer(rosemary_timer);
    System.out.println(rosemary.status_report());

    for(int i = 0; i < 48; i++){
      int time = i%24;
      System.out.println("It is currently " + time + " o'clock.");
      rosemary.time_passes(1);
      rosemary.check_water();
      System.out.println("");
    }
    System.out.println(rosemary.status_report());
  }

  public static void test_temp_sensor(){
    AC ac = new AC();
    Heater heater = new Heater();

    Command heat_up = new HeatUpCommand(ac, heater);
    Command cool_down = new CoolDownCommand(ac, heater);
    Command same_temp = new SameTempCommand(ac, heater);
    TempuratureSensor temp_sensor = new TempuratureSensor(heat_up, cool_down, same_temp, 60, 75);

    temp_sensor.set_temp_modification(-3);

    for(int i = 0; i < 48; i++){
      int time = i%24;
      System.out.println("It is currently " + time + " o'clock.");
      temp_sensor.hour_passed();
      System.out.println("The tempurature is " + temp_sensor.get_current_temp() + " degrees F.");
      temp_sensor.check_temp();
      System.out.println("");

      // It got hot out!
      if (i >= 24){
        System.out.println("It's hot outside!");
        temp_sensor.set_temp_modification(3);
      }
    }
  }

  public static void test_room(){
    Room r1 = new Room(1, "Kitchen", 55, 85);

    WaterReservoir res = new WaterReservoir("Reservoir 1", 1000, 100);


    PlantPot rosemary = new PlantPot("Rosemary1", "rosemary");
    rosemary.set_water_reservoir(res);
    rosemary.set_min_soil_humidity(10);
    rosemary.set_desired_humidity(50);
    // rosemary.check_water();

    Light rosemary_light = new Light();
    Command light_on = new LightOnCommand(rosemary_light);
    Command light_off = new LightOffCommand(rosemary_light);

    Timer rosemary_timer = new Timer(light_on, light_off, 8);

    rosemary.set_timer(rosemary_timer);

    r1.add_res(res);
    r1.add_plant(rosemary);

    System.out.println(r1.status_report());
  }
}
