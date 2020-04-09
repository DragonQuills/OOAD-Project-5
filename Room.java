import java.util.ArrayList; //Referenced https://www.w3schools.com/java/java_arraylist.asp

class Room{
// attributes
  public String name;

  private ArrayList<PlantPot> plants;
  private ArrayList<WaterReservoir> reservoirs;
  private int lowest_temp;
  private int highest_temp;
  private PlantFactory plant_factory;
  private ArrayList<App> observers;
  private AC ac;
  private Heater heater;
  private TempuratureSensor temp_sensor;


//methods
  private void manage_room(){}
  private void notify_observers(){}

  public ArrayList<PlantPot> get_plants(){return plants;}
  public void add_plant(Plant p){}
  public void remove_plant(String name){}
  public void rename_plant(String old_name, String new_name){}
  public void add_res(WaterReservoir res){}
  public void remove_res(String name){}
  public void rename_res(String name){}
  public void add_observer(App app){}
  public void remove_observer(App app){}
  public void update(){}

// constructor
  public Room(String name, TempuratureSensor temp_sensor, int lowest_temp, int highest_temp){}
}
