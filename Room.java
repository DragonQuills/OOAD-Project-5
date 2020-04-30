/*
Client for controller pattern
Also employes some elements of the facade pattern
*/

import java.util.ArrayList; //Referenced https://www.w3schools.com/java/java_arraylist.asp

class Room{
// attributes
  public String name;
  public int id;

  private ArrayList<PlantPot> plants;
  private ArrayList<WaterReservoir> reservoirs;
  private int lowest_temp;
  private int highest_temp;
  private ArrayList<User> observers;
  private AC ac;
  private Heater heater;
  private TempuratureSensor temp_sensor;

  // since storage handler is a singleton, it's fine that room has a copy
  private StorageHandler store;


//methods
  public ArrayList<PlantPot> get_plants(){
    return plants;
  }

  public PlantPot get_plant(int n){
    return plants.get(n);
  }

  public ArrayList<WaterReservoir> get_reservoir_list(){
    return reservoirs;
  }

  public WaterReservoir get_reservoir(int n){
    return reservoirs.get(n);
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

  public void remove_plant(int index){
    plants.remove(index);
  }

  public void remove_plant_by_id(int id){
    for( int i = 0; i < plants.size(); i++){
      if (plants.get(i).id == id){
        plants.remove(i);
      }
    }
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

  public void add_res(WaterReservoir wr){
    reservoirs.add(wr);
  }
  public boolean remove_res(String name){
    for( int i = 0; i < reservoirs.size(); i++){
      if (reservoirs.get(i).name == name){
        reservoirs.remove(i);
        return true;
      }
    }
    return false;
  }

  public void remove_reservoir_by_id(int id){
    for( int i = 0; i < reservoirs.size(); i++){
      if (reservoirs.get(i).id == id){
        reservoirs.remove(i);
      }
    }
  }

  public void remove_res(int index){
    reservoirs.remove(index);
  }

  public boolean rename_res(String old_name, String new_name){
    for( int i = 0; i < reservoirs.size(); i++){
      if (reservoirs.get(i).name == old_name){
        reservoirs.get(i).name = new_name;
        return true;
      }
    }
    return false;
  }

  public String reservoirNames(){
    String resString = "";
    for(int res = 0; res < reservoirs.size(); res++){
      resString+=(res+1)+". "+reservoirs.get(res).name+"\n";
    }
    return resString;
  }

  public int numRes(){
    return reservoirs.size();
  }

  public String plantNames(){
    String plantsString = "";
    for(int plant = 0; plant < plants.size(); plant++){
      plantsString+=(plant+1)+". "+plants.get(plant).name+"\n";
    }
    return plantsString;
  }

  public int numPlants(){
    return plants.size();
  }

  // Adds a status report to show the status of the room
  // and the reservoirs and plants in it
  
  public String status_report(){
    String report = "";
    report += "The room " + name + " is at " + temp_sensor.get_current_temp() + " degrees F.\n";
    for( WaterReservoir wr : reservoirs){
      report += "\t" + wr.status_report() + "\n";
    }
    for( PlantPot p : plants){
      report += "\t" + p.status_report() + "\n";
    }
    return report;
  }

  // simulates an hour passing for tempurature changes and simulastes
  // and hour passing for all plants in the room
  // and checks level of reservoirs
  public void hour_passed(int temp_change){
    temp_sensor.set_temp_modification(temp_change);
    if(temp_change > 0){
      System.out.println("It's hot outside...");
    }
    if(temp_change < 0){
      System.out.println("It's cold outside...");
    }
    if(temp_sensor.get_temp_modification_from_me()){
      System.out.println("But this room is regulating it's own tempurature.");
    }
    temp_sensor.hour_passed();
    System.out.println("The tempurature is " + temp_sensor.get_current_temp() + " degrees F.");
    temp_sensor.check_temp();
    store.tempReading(id, temp_sensor.get_current_temp());
    for(PlantPot p: plants){
      p.hour_passed();
    }
    for( WaterReservoir wr : reservoirs){
      System.out.println(wr.status_report());
    }
    System.out.println("");
  }

// temp getter functions for PlantFactory.conditions_ok_for_plant()
  public float get_lowest_temp(){
    return lowest_temp;
  }
  public float get_highest_temp(){
    return highest_temp;
  }
// constructor
  // regular constructor
  public Room(int new_id, String new_name, int new_lowest_temp, int new_highest_temp){
    id = new_id;
    name = new_name;
    lowest_temp = new_lowest_temp;
    highest_temp = new_highest_temp;

    plants = new ArrayList<PlantPot>();
    reservoirs = new ArrayList<WaterReservoir>();

    ac = new AC();
    heater = new Heater();

    Command heat_up = new HeatUpCommand(ac, heater);
    Command cool_down = new CoolDownCommand(ac, heater);
    Command same_temp = new SameTempCommand(ac, heater);
    temp_sensor = new TempuratureSensor(heat_up, cool_down, same_temp, lowest_temp, highest_temp);
    store = StorageHandler.getInstance();
  }

  // constructor to be used by the storage handler when creating a room from the database
  public Room(int new_id, String new_name, int new_lowest_temp, int new_highest_temp, int current_temp){
    id = new_id;
    name = new_name;
    lowest_temp = new_lowest_temp;
    highest_temp = new_highest_temp;

    plants = new ArrayList<PlantPot>();
    reservoirs = new ArrayList<WaterReservoir>();

    ac = new AC();
    heater = new Heater();

    Command heat_up = new HeatUpCommand(ac, heater);
    Command cool_down = new CoolDownCommand(ac, heater);
    Command same_temp = new SameTempCommand(ac, heater);
    temp_sensor = new TempuratureSensor(heat_up, cool_down, same_temp, lowest_temp, highest_temp);
    temp_sensor.set_current_temp(current_temp);

    store = StorageHandler.getInstance();
  }
}
