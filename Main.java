import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in); //For terminal input, reference: https://alvinalexander.com/java/edu/pj/pj010005/
        boolean validInput = false;
        StorageHandler storage = StorageHandler.getInstance();
        PlantFactory factory = new PlantFactory();
        int userId = -1;
        while(!validInput){
            System.out.println("Select an option:");
            System.out.println("1. Login");
            System.out.println("2. Register");
            int intInput = scanner.nextInt();
            if(intInput == 1){
                while(userId == -1){
                    userId = storage.loginUser();
                }
                validInput = true;
            }
            else if(intInput == 2){
                //Redirect to register
                while(userId == -1){
                    userId = storage.registerUser();
                }
                validInput = true;
            }
            else{
                System.out.println("Input not valid");
            }
        }

        User user = new User(userId);
        ArrayList<Room> userRooms = storage.roomsFromUser(userId);
        for(int room = 0; room < userRooms.size(); room++){
            ArrayList<WaterReservoir> reservoirs = storage.reservoirsFromRoom(userRooms.get(room).id);
            for(int res = 0; res < reservoirs.size(); res++){
                userRooms.get(room).add_res(reservoirs.get(res));
                ArrayList<PlantPot> plants = storage.plantFromReservoir(reservoirs.get(res));
                for(int plant = 0; plant < plants.size(); plant++){
                    userRooms.get(room).add_plant(plants.get(plant));
                }
            }
            user.add_room(userRooms.get(room));
        }

        while(true){
            //Print main menu
            System.out.println("Select an option:");
            System.out.println("1. Add Room");
            System.out.println("2. Add Reservoir");
            System.out.println("3. Add Plant");
            System.out.println("4. View Rooms");
            System.out.println("5. Quit");
            int intInput = scanner.nextInt();
            //Add Room
            if(intInput == 1){
              System.out.println("Enter the name of your new room: ");
              String room_name = scanner.next();
              String min_temp;
              String max_temp;
              int temp_min;
              int temp_max;
              while(true){
                System.out.println("Minimum Temperature of new room: ");
                min_temp = scanner.next();
                try {
                  temp_min = Integer.parseInt(min_temp);
                  if (temp_min < 1 || temp_min > 120){
                    System.out.println("Temperature must be between 0 and 120. Please try again.");
                  }
                  else{
                    break;
                  }
                }
                catch(NumberFormatException e) {
                  System.out.println("Input must be a number. Please try again:");
                }
              }
              while(true){
                System.out.println("Maximum Temperature of new room: ");
                max_temp = scanner.next();
                try {
                  temp_max = Integer.parseInt(min_temp);
                  if (temp_max < 1 || temp_max > 120){
                    System.out.println("Temperature must be between 0 and 120. Please try again.");
                  }
                  else{
                    break;
                  }
                }
                catch(NumberFormatException e) {
                  System.out.println("Input must be a number. Please try again:");
                }
              }
              storage.createRoom(room_name, temp_min, temp_max, userId);
            }
            //Add Reservoir
            else if(intInput == 2){
                System.out.println("Enter reservoir name: ");
                String reservoirName = scanner.next();
                System.out.println("Max capacity: ");
                int capacity = scanner.nextInt();
                System.out.println("Warning level: ");
                int warning = scanner.nextInt();
                storage.createReservoir(userId, reservoirName, capacity, warning);
            }
            else if(intInput == 3){
                //Add plant
                System.out.println("Here are your available Rooms:");

                //Display existing room options and request room selection for new plant
                ArrayList<Room> rooms_list = user.get_rooms_list();
                
                for (Room room : rooms_list) {
                  System.out.println(room.status_report());
                }
				System.out.println("Which room would you like to add your plant to?");
				boolean validRoom = false;
				int roomIndex = -1;
                while(!validRoom){
                  String input_room = scanner.next();
                  for (int i = 0; i < rooms_list.size(); i++){
                    if (rooms_list.get(i).name == input_room){
					  roomIndex = i;
					  validRoom = true;
                      break;
                    }
                  }
                  System.out.println("Room"+input_room+" does not exist, please try again.");
				}
				
				Room selected_room = rooms_list.get(roomIndex);

                //Display Reservoirs available in selected room and request reservoir selection for new plant
                System.out.println("Here are your available reservoirs in " + selected_room.name);
                ArrayList<WaterReservoir> reservoir_list = selected_room.get_reservoir_list();
                for (WaterReservoir reservoir : reservoir_list){
                  System.out.println(reservoir.status_report());
                }
                System.out.println("Which reservoir would you like to use for your new plant?");
				
				boolean validRes = false;
				int resIndex = -1;
                while(!validRes) {
                  String input_reservoir = scanner.next();
                  for (int i = 0; i < reservoir_list.size(); i++){
                    if (reservoir_list.get(i).name == input_reservoir){
					  validRes = true;
					  resIndex = i;
                      break;
                    }
                  }
                  System.out.println("Reservoir" + input_reservoir + " does not exist. Please try again.");
				}
				
				WaterReservoir selected_reservoir = reservoir_list.get(resIndex);

                System.out.println("Enter plant name:");//This has to be first as input for PlantFactory
                String plant_name = scanner.next();
                System.out.println("Enter plant type");
                String plant_type = scanner.next();
                System.out.println("Checking our database of recommendations...");
                PlantPot new_plant = factory.get_plant(plant_name, plant_type);
                selected_room.add_plant(new_plant);
                new_plant.set_water_reservoir(selected_reservoir);

                //If data is found in query_csv(), print that data
                String user_likes_data = "";
                if (new_plant.get_min_temp() > 0) {
                  System.out.println("Here's what we found:");
                  System.out.println("Minimum Soil Humidity: "+ new_plant.get_min_soil_humidity());
                  System.out.println("Desired/Max Soil Humidity: "+ new_plant.get_desired_soil_humidity());
                  System.out.println("Light Hours: "+ new_plant.get_light_hours());
                  System.out.println("Minimum Room Temperature: "+ new_plant.get_min_temp());
                  System.out.println("Maximum Room Temperature: "+ new_plant.get_max_temp());
                  System.out.println("Accept? (y/n)");
                  user_likes_data = scanner.next();
                }
                //else, tell the use they must enter data
                else {
                  System.out.println("No data about plant type "+ plant_type+ ". Please enter custom data: ");
                  user_likes_data = "n";
                }


                while(true){
                  //If user likes data, continue with new_plant as-is
                  if (user_likes_data == "y"){
                    break;
                  }
                  //Case: User wants to enter their own data
                  if (user_likes_data == "n"){
                      System.out.println("Please enter your custom maintenance settings:");
                      //User Input Soil Humidity Input Validation
                      while(true){
                        System.out.println("Minimum soil humidity percentage (0-100):");
                        String min_soil_humidity = scanner.next();
                        try {
                          Float min_soil = Float.parseFloat(min_soil_humidity);
                          if (min_soil < 1 || min_soil > 99){
                            System.out.println("Humidity percentage must be between 0 and 100. Please try again.");
                          }
                          else{
                            new_plant.set_min_soil_humidity(min_soil);
                            break;
                          }
                        }
                        catch(NumberFormatException e) {
                          System.out.println("Input must be a number. Please try again:");
                        }
                      }
                      while(true){
                        System.out.println("Ideal/Maximum soil humidity percentage(0-100):");
                        String desired_soil_humidity = scanner.next();
                        try {
                          Float desired_soil = Float.parseFloat(desired_soil_humidity);
                          if (desired_soil < 1 || desired_soil > 99){
                            System.out.println("Humidity percentage must be between 0 and 100. Please try again.");
                          }
                          else{
                            new_plant.set_desired_soil_humidity(desired_soil);
                            break;
                          }
                        }
                        catch(NumberFormatException e) {
                          System.out.println("Input must be a number. Please try again:");
                        }
                      }
                      //User Input Light Time Input Validation
                      while(true){
                        System.out.println("Number of hours of light per day:");
                        String light_hours = scanner.next();
                        try {
                          int light = Integer.parseInt(light_hours);
                          if (light < 1 || light > 24){
                            System.out.println("Lights must be on for 1-24 hours per day. Please try again.");
                          }
                          else{
                            new_plant.set_light_hours(light);
                            break;
                          }
                        }
                        catch(NumberFormatException e) {
                          System.out.println("Input must be a number. Please try again:");
                        }
                      }
                      //User Input Temperature Input Validation
                      while(true){
                        System.out.println("Minimum Room Temperature (Fahrenheit):");
                        String min_temp = scanner.next();
                        try {
                          Float temp = Float.parseFloat(min_temp);
                          if (temp < 0 || temp > 120){
                            System.out.println("We are currently unable to provide the environment you have requested.");
                            System.out.println("Please enter a temperature between 0 and 120 degrees Fahrenheit.");
                          }
                          else{
                            new_plant.set_min_temp(temp);
                            break;
                          }
                        }
                        catch(NumberFormatException e) {
                          System.out.println("Input must be a number. Please try again:");
                        }
                      }
                      while(true){
                        System.out.println("Maximum Room Temperature (Fahrenheit):");
                        String max_temp = scanner.next();
                        try {
                          Float temp = Float.parseFloat(max_temp);
                          if (temp < 0 || temp > 120){
                            System.out.println("We are currently unable to provide the environment you have requested.");
                            System.out.println("Please enter a temperature between 0 and 120 degrees Fahrenheit.");
                          }
                          else{
                            new_plant.set_max_temp(temp);
                            break;
                          }
                        }
                        catch(NumberFormatException e) {
                          System.out.println("Input must be a number. Please try again:");
                        }
                      }

                    }
                  //Catch invalid input, request new input
                  //Should re-iterate at top of the "parent" while loop with new user_likes_data value
                  else {
                        System.out.println("Invalid Input. Please type y or n and press Enter.");
                        user_likes_data = scanner.next();
                      }
                  }

            }
            else if(intInput == 4){
                boolean quitView = false;
                while(!quitView){
                    boolean validRoom = false;
                    int roomChoice = -1;
                    while(!validRoom){
                        System.out.println("Select a room: ");
                        System.out.println(user.roomNames());
                        roomChoice = scanner.nextInt();
                        if(roomChoice > 0 && roomChoice <= user.numRooms()){
                            validRoom = true;
                        }
                        else{
                            System.out.println("Not a valid room");
                        }
                    }
                    Room selectedRoom = user.get_room(roomChoice-1);
					//At this point a room has been selected
					boolean quitSubmenu = false;
                    while(!quitSubmenu){
                        System.out.println("Select an option:");
                        System.out.println("1 View plants");
                        System.out.println("2 View reservoirs");
						System.out.println("3 Delete room");
						System.out.println("4 Return to main menu");
						int menuChoice = scanner.nextInt();
						if(menuChoice == 1){
							boolean quitPlants = false;
							ArrayList<PlantPot> plants = selectedRoom.get_plants();
							while(!quitPlants){
								for(int i = 0; i < plants.size(); i++){
									System.out.println((i+1)+" Delete "+plants.get(i).name);
								}
								System.out.println((plants.size()+1)+" Return to main menu");
								int plantChoice = scanner.nextInt();
								if(plantChoice > 0 && plantChoice <= plants.size()){
									storage.deletePlant(selectedRoom.get_plant(plantChoice-1).id);
								}
								else if(plantChoice == (plants.size()+1)){
									quitPlants = true;
								}
								else{
									System.out.println("Not a valid choice");
								}
							}
						}
						else if(menuChoice == 2){
							boolean quitRes = false;
							ArrayList<WaterReservoir> resList = selectedRoom.get_reservoir_list();
							while(!quitRes){
								for(int i = 0; i < resList.size(); i++){
									System.out.println((i+1)+" Delete "+resList.get(i).name);
								}
								System.out.println((resList.size()+1)+" Return to main menu");
								int resChoice = scanner.nextInt();
								if(resChoice > 0 && resChoice <= resList.size()){
									storage.deleteReservoir(selectedRoom.get_reservoir(resChoice-1).id);
								}
								else if(resChoice == (resList.size()+1)){
									quitRes = true;
								}
								else{
									System.out.println("Not a valid choice");
								}
							}
						}
						else if(menuChoice == 3){
							storage.deleteRoom(selectedRoom.id);
							user.remove_room(selectedRoom.name);
							quitView = true;
						}
						else if(menuChoice == 4){
							quitView = true;
							break;
						}
						else{
							System.out.println("Not a valid choice");
						}
                    }
                }
            }
            else if(intInput == 5){
                //Quit
                break;
            }
            else{
                System.out.println("Input not valid. Please type a number 1-5 and press Enter.");
            }

        }
        scanner.close();
    }
}
