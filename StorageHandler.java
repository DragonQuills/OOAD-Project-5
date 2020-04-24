import java.util.Scanner;
import java.io.*; //reference: https://www.tutorialspoint.com/java/java_files_io.htm
import java.nio.file.*;
import java.util.ArrayList;

//Referenced https://www.geeksforgeeks.org/singleton-class-java/

public class StorageHandler {
    private static StorageHandler instance = null;

    private File usersFile;
    private File reservoirsFile;
    private File roomsFile;
    private File plantsFile;
    private File ownersFile;

    private StorageHandler(){
        usersFile = new File("./storage/users.csv");
        reservoirsFile = new File("./storage/reservoirs.csv");
        roomsFile = new File("./storage/rooms.csv");
        plantsFile = new File("./storage/plants.csv");
        ownersFile = new File("./storage/owners.csv");
    }

    public static StorageHandler getInstance(){
        if(instance == null){
            instance = new StorageHandler();
        }

        return instance;
    }

    public int registerUser(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a username: ");
        String username = scanner.next();
        System.out.println("Enter a password: ");
        String password = scanner.next();
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
                    return -1;
                }
                if(Integer.parseInt(splitLine[0]) > maxId){
                    maxId = Integer.parseInt(splitLine[0]);
                }
            }
            br.close();        
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
            return -1;
        }     
        catch(IOException e){
            e.printStackTrace();
            return -1;
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
            return -1;
        }
        return maxId+1;
    }

    public int loginUser(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a username: ");
        String username = scanner.next();
        System.out.println("Enter a password: ");
        String password = scanner.next();
        try{
            FileInputStream f = new FileInputStream(usersFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(f));
            String line;
            boolean firstLine = true;
            while((line = br.readLine()) != null){
                String[] splitLine = line.split(",");
                if(firstLine){
                    firstLine = false;
                    continue;
                }
                if(splitLine[1].equals(username) && splitLine[2].equals(password)){
                    br.close(); 
                    return Integer.parseInt(splitLine[0]);
                }
            }
            br.close();        
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
            return -1;
        }     
        catch(IOException e){
            e.printStackTrace();
            return -1;
        }
        // This reaching this point means that username/password combo was not found
        System.out.println("Username does not exist or incorrect password was entered");
        return -1;
    }

    public WaterReservoir createReservoir(int userId, String name, int capacity, int warning){
        int maxId = getMaxId(reservoirsFile);

        try{
            FileWriter fw = new FileWriter(reservoirsFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            String line = (maxId+1)+","+String.valueOf(userId)+","+name+","+String.valueOf(capacity)+","+String.valueOf(warning);
            pw.println(line);
            pw.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }

        return new WaterReservoir(name, capacity, warning);
    }

    public ArrayList<WaterReservoir> getReservoirs(int ownerId){
        ArrayList<WaterReservoir> reservoirs = new ArrayList<WaterReservoir>();
        try{
            FileInputStream f = new FileInputStream(reservoirsFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(f));
            String line;
            boolean firstLine = true;
            while((line = br.readLine()) != null){
                String[] splitLine = line.split(",");
                if(firstLine){
                    firstLine = false;
                    continue;
                }
                if(Integer.parseInt(splitLine[1]) == ownerId){
                    String name = splitLine[2];
                    int capacity = Integer.parseInt(splitLine[3]);
                    int warning = Integer.parseInt(splitLine[4]);
                    reservoirs.add(new WaterReservoir(name, capacity, warning));
                }
            }
            br.close();        
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }     
        catch(IOException e){
            e.printStackTrace();
        }
        return reservoirs;
    }

    public Room createRoom(String name, int lowestTemp, int highestTemp){
        //TODO: Change return type to room
        int maxId = getMaxId(roomsFile);

        try{
            FileWriter fw = new FileWriter(roomsFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            String line = (maxId+1)+","+name+","+lowestTemp+","+highestTemp+",0"; //0 indicates a room that hasn't had a temperature measurement yet
            pw.println(line);
            pw.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }

        return new Room(maxId+1, name, lowestTemp, highestTemp);
    }

    public PlantPot createPlant(int reservoirId, WaterReservoir reservoir, String name, String type){
        int maxId = getMaxId(plantsFile);
        //room is not needed as reservoir manages that relation
        try{
            FileWriter fw = new FileWriter(plantsFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            String line = (maxId+1)+","+reservoirId+","+name+","+type+",0"; //0 indicates a plant with no recorded moisture
            pw.println(line);
            pw.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }

        //TODO: New constructor for plant?  I am not sure how this function will best interact with the factory.
        // Maybe a function that is called at runtime that bypasses the factory w/ information stored in the database

        PlantPot newPlant = new PlantPot(name, type);
        newPlant.set_water_reservoir(reservoir);

        return newPlant;
    }

    public void deleteById(File file, int id){
        //Use function overloading in place of default parameters
        deleteById(file, id, 0);
    }

    public void deleteById(File file, int id, int index){
        ArrayList<String> original = new ArrayList<String>();
        try{
            FileInputStream f = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(f));
            String line;
            while((line = br.readLine()) != null){
                original.add(line);
            }
            br.close();        
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }     
        catch(IOException e){
            e.printStackTrace();
        }

        try{
            FileWriter fw = new FileWriter(file, false); //False overwrites
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            for(int i = 0; i < original.size(); i++){
                if(original.get(i).split(",")[index].equals(String.valueOf(id))){
                    continue;
                }
                pw.println(original.get(i));
            }
            pw.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
    }

    private int getMaxId(File file){
        int maxId = 0;
        try{
            FileInputStream f = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(f));
            String line;
            boolean firstLine = true;
            while((line = br.readLine()) != null){
                String[] splitLine = line.split(",");
                if(firstLine){
                    firstLine = false;
                    continue;
                }
                if(Integer.parseInt(splitLine[0]) > maxId){
                    maxId = Integer.parseInt(splitLine[0]);
                }
            }
            br.close();        
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }     
        catch(IOException e){
            e.printStackTrace();
        }
        return maxId;
    }

    public boolean addOwnership(int user, int room){
        try{
            FileInputStream f = new FileInputStream(ownersFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(f));
            String line;
            boolean firstLine = true;
            while((line = br.readLine()) != null){
                String[] splitLine = line.split(",");
                if(firstLine){
                    firstLine = false;
                    continue;
                }
                if(Integer.parseInt(splitLine[0]) == user && Integer.parseInt(splitLine[1]) == room){
                    System.out.println("Relation already exists");
                    return false;
                }
            }
            br.close();        
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }     
        catch(IOException e){
            e.printStackTrace();
        }

        try{
            FileWriter fw = new FileWriter(ownersFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            String line = user+","+room;
            pw.println(line);
            pw.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }

        return true;
    }

    private Room roomFromId(int roomId){
        try{
            FileInputStream f = new FileInputStream(roomsFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(f));
            String line;
            boolean firstLine = true;
            while((line = br.readLine()) != null){
                String[] splitLine = line.split(",");
                if(firstLine){
                    firstLine = false;
                    continue;
                }
                if(Integer.parseInt(splitLine[0]) == roomId){
                    br.close();
                    int id = Integer.parseInt(splitLine[0]);
                    String name = splitLine[1];
                    int lowest = Integer.parseInt(splitLine[2]);
                    int highest = Integer.parseInt(splitLine[3]);
                    return new Room(id, name, lowest, highest);
                }
            }
            br.close();        
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }     
        catch(IOException e){
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<Room> roomsFromUser(int userId){
        ArrayList<Room> rooms = new ArrayList<Room>();

        try{
            FileInputStream f = new FileInputStream(ownersFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(f));
            String line;
            boolean firstLine = true;
            while((line = br.readLine()) != null){
                String[] splitLine = line.split(",");
                if(firstLine){
                    firstLine = false;
                    continue;
                }
                if(Integer.parseInt(splitLine[0]) == userId){
                    rooms.add(roomFromId(Integer.parseInt(splitLine[1])));
                }
            }
            br.close();        
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }     
        catch(IOException e){
            e.printStackTrace();
        }

        return rooms;
    }
}