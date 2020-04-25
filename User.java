import java.util.ArrayList; //Referenced https://www.w3schools.com/java/java_arraylist.asp


class User{
// attributes
  private ArrayList<Room> rooms;
  public int id;

// methods
  public String status_report(){

  }

  public void add_room(Room r){
    rooms.add(r);
  }
  public boolean remove_room(String name){
    for( int i = 0; i < rooms.size(); i++){
      if (rooms.get(i).name == name){
        rooms.remove(i);
        return true;
      }
    }
    return false;
  }

  public boolean rename_room(String old_name, String new_name){
    for( int i = 0; i < rooms.size(); i++){
      if (rooms.get(i).name == old_name){
        rooms.get(i).name = new_name;
        return true;
      }
    }
    return false;
  }

  public String roomNames(){
    String roomString = "";
    for(int room = 0; room < rooms.size(); room++){
      roomString+=(room+1)+" "+rooms.get(room).name+"\n";
    }
    return roomString;
  }

  public int numRooms(){
    return rooms.size();
  }

  public Room get_room(int n){
    return rooms.get(n);
  }

  public User(int new_id){
    id = new_id;
    rooms = new ArrayList<Room>();
  }
}
