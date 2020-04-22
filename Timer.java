class Timer{
// attributes
  private Command light_on;
  private Command light_off;
  private int hours_on;
  private int time_left;
  private boolean is_light_on;
//methods
  public void hour_passed(){
    time_left --;
    if(time_left <= 0){
      if(is_light_on){
        light_off.execute();
        time_left = hours_on;
        is_light_on = false;
      }
      else{
        light_on.execute();
        time_left = 24 - hours_on;
        is_light_on = true;
      }
    }
  }
  
  public float get_hours_on(){return hours_on;}
  public void set_hours_on(float hours_on){}
//constructor
  public Timer(Command new_light_on, Command new_light_off, float new_hours_on){
    light_on = new_light_on;
    light_off = new_light_off;
    hours_on = new_hours_on;

    light_off.execute();
    time_left = 24 - hours_on;
    is_light_on = false;
  }
}
