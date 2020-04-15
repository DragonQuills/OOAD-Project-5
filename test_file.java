class test_file{
  public static void main(String args[]){
    WaterReservoir res = new WaterReservoir("Reservoir 1", 100, 20);

    System.out.println(res.water_used(3));
    System.out.println(res.water_used(3));
    System.out.println(res.refill_needed());
    System.out.println(res.report_current_level());

    // res.water_refilled();

    Hose hose = new Hose(res);
    Command hose_on = new WaterOnCommand(hose);
    Command hose_off = new WaterOffCommand(hose);

    WaterSensor sensor = new WaterSensor(hose_on, hose_off);

    sensor.water_plant();
  }
}
