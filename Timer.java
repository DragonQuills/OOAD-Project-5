class Timer{
// attributes
  private Command light_on;
  private Command light_off;
  private float hours_on;
//methods
  public void cycle(){}
  public float get_hours_on(){return hours_on;}
  public void set_hours_on(float hours_on){}
//constructor
  public Timer(Command light_on, Command light_off, float hours_on){}
}
