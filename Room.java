import java.util.ArrayList; //Referenced https://www.w3schools.com/java/java_arraylist.asp

class Room{
// attributes
  public String name;

  private ArrayList<PlantPot> plants;
  private ArrayList<WaterReservoir> reservoirs;
  private int lowest_temp;
  private int highest_temp;
  // private PlantFactory plant_factory;
  private ArrayList<User> observers;
  private AC ac;
  private Heater heater;
  private TempuratureSensor temp_sensor;


//methods
  // I think this is better off in the UI file?
  // private void manage_room(){}


  public ArrayList<PlantPot> get_plants(){
    return plants;
  }
  public void add_plant(PlantPot p){
    plants.add(p);
  }
  public boolean remove_plant(String name){
    for( int i = 0; i < plants.size(); i++){
      if (plants.get(i).name == name){
        plants.remove(i);
        return true;
      }
    }
    return false;
  }

  public boolean rename_plant(String old_name, String new_name){
    for( int i = 0; i < plants.size(); i++){
      if (plants.get(i).name == old_name){
        plants.get(i).name = new_name;
        return true;
      }
    }
    return false;
  }

  public void add_res(WaterReservoir res){}
  public void remove_res(String name){}
  public void rename_res(String name){}

  public void add_observer(User app){}
  public void remove_observer(User app){}
  private void notify_observers(){}
  // public void update(){}

// constructor
  public Room(String new_name, int new_lowest_temp, int new_highest_temp){
    name = new_name;
    lowest_temp = new_lowest_temp;
    highest_temp = new_highest_temp;

    plants = new ArrayList<PlantPot>();

    ac = new AC();
    heater = new Heater();

    Command heat_up = new HeatUpCommand(ac, heater);
    Command cool_down = new CoolDownCommand(ac, heater);
    Command same_temp = new SameTempCommand(ac, heater);
    temp_sensor = new TempuratureSensor(heat_up, cool_down, same_temp, 60, 75);
  }
}
