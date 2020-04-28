class TempuratureSensor{
// attributes
  private Command heat_room;
  private Command cool_room;
  private Command hold_temp;

  private int current_temp;
  private int min_temp;
  private int max_temp;
  private int median_temp;
  private int temp_modification;
  private boolean temp_modification_from_me;
// methods
  public int get_current_temp(){
    return current_temp;
  }
  public void set_current_temp(int new_current_temp){
    current_temp = new_current_temp;
  }
  public void set_min_temp(int new_min){
    min_temp = new_min;
  }
  public void set_max_temp(int new_max){
    max_temp = new_max;
  }
  public void set_temp_modification(int new_modification){
    if(temp_modification == 0){ // the heater and ac are off
      temp_modification = new_modification;
      temp_modification_from_me = false;
    }
  }
  public boolean get_temp_modification_from_me(){
    return temp_modification_from_me;
  }

  public void check_temp(){
    if(current_temp == median_temp){
      // Used to simulate external tempurature changes effecting the rooms
      if (temp_modification != 0 && temp_modification_from_me == true){
        System.out.println("Tempurature is at median tempurature, turning off AC and heater.");
        hold_temp.execute();
        temp_modification = 0;
        temp_modification_from_me = true;
      }
    }
    else if (current_temp < min_temp){
      System.out.println("Tempurature is too cold, turning AC off and heater on.");
      heat_room.execute();
      temp_modification = 1;
      temp_modification_from_me = true;
    }
    else if( current_temp > max_temp){
      System.out.println("Tempurature is too hot, turning AC on and heater off.");
      cool_room.execute();
      temp_modification = -1;
      temp_modification_from_me = true;
    }
  }

  public void hour_passed(){
    current_temp += temp_modification;
  }
// constructor
  public TempuratureSensor(Command heat, Command cool, Command hold, int new_min_temp, int new_max_temp){
    min_temp = new_min_temp;
    max_temp = new_max_temp;
    heat_room = heat;
    cool_room = cool;
    hold_temp = hold;

    current_temp = max_temp - 10;
    temp_modification = 0;
    median_temp = min_temp + (max_temp - min_temp)/2;
  }
}
