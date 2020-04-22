import java.util.Scanner;
import java.io.*; //reference: https://www.tutorialspoint.com/java/java_files_io.htm
import java.nio.file.*;

public class Main {

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in); //For terminal input, reference: https://alvinalexander.com/java/edu/pj/pj010005/
        boolean validInput = false;
        while(!validInput){
            System.out.println("Select an option:");
            System.out.println("1. Login");
            System.out.println("2. Register");
            int intInput = scanner.nextInt();
            if(intInput == 1){
                //Redirect to login
                validInput = true;
            }
            else if(intInput == 2){
                //Redirect to register
                registerUser();
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
                //Add reservoir
            }
            else if(intInput == 3){
                //Add plant
            }
            else if(intInput == 4){
                //View rooms
            }
            else if(intInput == 5){
                //Quit
                break;
            }
            else{
                System.out.println("Input not valid");
            }

        }
        scanner.close();
    }

    private static boolean registerUser(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a username: ");
        String username = scanner.next();
        System.out.println("Enter a password: ");
        String password = scanner.next();
        File usersFile = new File("./storage/users.csv");
        //Ref: https://tutoref.com/how-to-read-and-write-files-in-java-8/
        int maxId;
        try{
            FileInputStream f = new FileInputStream(usersFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(f));
            String line;
            maxId = 0;
            boolean firstLine = true;
            while((line = br.readLine()) != null){
                String[] splitLine = line.split(",");
                if(firstLine){
                    firstLine = false;
                    continue;
                }
                if(splitLine[1].equals(username)){
                    System.out.println("Username already registered");
                    return false;
                }
                if(Integer.parseInt(splitLine[0]) > maxId){
                    maxId = Integer.parseInt(splitLine[0]);
                }
            }
            br.close();        
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
            return false;
        }     
        catch(IOException e){
            e.printStackTrace();
            return false;
        }   
        //If the code has reached this point that username is not registered
        try{
            FileWriter fw = new FileWriter(usersFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            String line = (maxId+1)+","+username+","+password;
            pw.println(line);
            pw.close();
        }
        catch(IOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}