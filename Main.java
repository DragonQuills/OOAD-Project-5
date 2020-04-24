import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in); //For terminal input, reference: https://alvinalexander.com/java/edu/pj/pj010005/
        boolean validInput = false;
        StorageHandler storage = StorageHandler.getInstance();
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

        while(true){
            //Print main menu
            System.out.println("Select an option:");
            System.out.println("1. Add Room");
            System.out.println("2. Add Reservoir");
            System.out.println("3. Add Plant");
            System.out.println("4. View Rooms");
            System.out.println("5. Quit");
            int intInput = scanner.nextInt();
            if(intInput == 1){
                //Add room
            }
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
                System.out.println("Select Room:");
                //List out the available Rooms

                System.out.println("Enter plant name:");//This has to be first as input for PlantFactory
                String plant_name = scanner.next();
                System.out.println("Enter plant type");
                String plant_type = scanner.next();
                System.out.println("Checking our database of recommendations...");
                PlantPot new_plant = PlantFactory.get_plant(plant_name, plant_type);
                if (new_plant)
                System.out.println("Accept? (y/n)")
                while(true){
                  String user_likes_data = scanner.next();
                  if (user_likes_data == "y"){
                    //continue with default data

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
                          Float.parseFloat(min_soil_humidity);
                          if (min_soil_humidity < 1 || min_soil_humidity > 99){
                            System.out.println("Humidity percentage must be between 0 and 100. Please try again.");
                          }
                          else{
                            break;
                          }
                        }
                        catch(NumberFormatException e) {
                          System.out.println("Input must be a number. Please try again:")
                        }
                      }
                      while(true){
                        System.out.println("Ideal/Maximum soil humidity percentage(0-100):");
                        String desired_soil_humidity = scanner.next();
                        try {
                          Float.parseFloat(min_soil_humidity);
                          if (desired_soil_humidity < 1 || desired_soil_humidity > 99){
                            System.out.println("Humidity percentage must be between 0 and 100. Please try again.");
                          }
                          else{
                            break;
                          }
                        }
                        catch(NumberFormatException e) {
                          System.out.println("Input must be a number. Please try again:")
                        }
                      }
                      //User Input Light Time Input Validation
                      while(true){
                        System.out.println("Number of hours of light per day:");
                        String light_hours = scanner.next();
                        try {
                          Float.parseFloat(light_hours);
                          if (light_hours < 1 || light_hours > 24){
                            System.out.println("Lights must be on for 1-24 hours per day. Please try again.");
                          }
                          else{
                            break;
                          }
                        }
                        catch(NumberFormatException e) {
                          System.out.println("Input must be a number. Please try again:")
                        }
                      }
                      //User Input Temperature Input Validation
                      while(true){
                        System.out.println("Minimum Room Temperature (Fahrenheit):");
                        String min_temp = scanner.next();
                        try {
                          Float.parseFloat(min_temp);
                          if (min_temp < 0 || min_temp > 120){
                            System.out.println("We are currently unable to provide the environment you have requested.");
                            System.out.println("Please enter a temperature between 0 and 120 degrees Fahrenheit.");
                          }
                          else{
                            break;
                          }
                        }
                        catch(NumberFormatException e) {
                          System.out.println("Input must be a number. Please try again:")
                        }
                      }
                      while(true){
                        System.out.println("Maximum Room Temperature (Fahrenheit):");
                        String max_temp = scanner.next();
                        try {
                          Float.parseFloat(max_temp);
                          if (max_temp < 0 || max_temp > 120){
                            System.out.println("We are currently unable to provide the environment you have requested.");
                            System.out.println("Please enter a temperature between 0 and 120 degrees Fahrenheit.");
                          }
                          else{
                            break;
                          }
                        }
                        catch(NumberFormatException e) {
                          System.out.println("Input must be a number. Please try again:")
                        }
                      }
                      String[] user_data_input = min_soil_humidity;
                      user_data_input.add(desired_soil_humidity);
                      user_data_input.add(light_time);
                      user_data_input.add(min_temp);
                      user_data_input.add(max_temp):

                    }

                  else {
                        System.out.println("Invalid Input. Please type y or n and press Enter.")
                      }
                  }

            }
            else if(intInput == 4){
                //View rooms
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
