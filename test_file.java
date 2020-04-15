class test_file{
  public static void main(String args[]){
    WaterReservoir res = new WaterReservoir(5, 2);

    System.out.println(res.water_used(3));
    System.out.println(res.water_used(3));
    System.out.println(res.refill_needed());
    System.out.println(res.report_current_level());

  }
}
