import java.util.Scanner;
import java.io.*; //reference: https://www.tutorialspoint.com/java/java_files_io.htm
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

    public int registerUser(Scanner scanner){
        // Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a username: ");
        String username = scanner.next();
        System.out.println("Enter a password: ");
        String password = scanner.next();
        // scanner.close();
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
                    br.close();
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

    public int loginUser(Scanner scanner){
        // Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a username: ");
        String username = scanner.next();
        System.out.println("Enter a password: ");
        String password = scanner.next();
        // scanner.close();
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

    public WaterReservoir createReservoir(int roomId, String name, int capacity, int warning){
        int maxId = getMaxId(reservoirsFile);

        try{
            FileWriter fw = new FileWriter(reservoirsFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            String line = (maxId+1)+","+String.valueOf(roomId)+","+name+","+String.valueOf(capacity)+","+String.valueOf(warning)+","+String.valueOf(capacity); //Assume a reservoir is added while full
            pw.println(line);
            pw.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }

        return new WaterReservoir(name, capacity, warning, maxId+1);
    }

    public Room createRoom(String name, int lowestTemp, int highestTemp, int userId){
        int maxId = getMaxId(roomsFile);

        try{
            FileWriter fw = new FileWriter(roomsFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            String line = (maxId+1)+","+name+","+lowestTemp+","+highestTemp+","+(highestTemp-10); //0 indicates a room that hasn't had a temperature measurement yet
            pw.println(line);
            pw.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }

        addOwnership(userId, maxId+1);

        return new Room(maxId+1, name, lowestTemp, highestTemp);
    }

    public void createPlant(PlantPot plant){
        int maxId = getMaxId(plantsFile);
        int reservoirId = plant.get_res().id;
        String name = plant.name;
        String type = plant.plant_type;
        Float desiredHumidity = plant.get_desired_soil_humidity();
        Float minHumidity = plant.get_min_soil_humidity();
        Float maxTemp = plant.get_max_temp();
        Float minTemp = plant.get_min_temp();
        Float lightTime = plant.get_light_hours();
        //room is not needed as reservoir manages that relation
        try{
            FileWriter fw = new FileWriter(plantsFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            String line = (maxId+1)+","+reservoirId+","+name+","+type+","+desiredHumidity+","+minHumidity+","+maxTemp+","+minTemp+",0,"+String.valueOf(lightTime); //0 indicates a plant with no recorded moisture
            pw.println(line);
            pw.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }

        plant.id = maxId+1;
    }

    private void deleteById(File file, int id){
        //Use function overloading in place of default parameters
        deleteById(file, id, 0);
    }

    private void deleteById(File file, int id, int index){
        ArrayList<String> original = originalFile(file);

        try{
            FileWriter fw = new FileWriter(file, false); //False flag overwrites the file
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

    public void deletePlant(int id){
        deleteById(plantsFile, id);
    }

    public void deleteReservoir(int id){
        ArrayList<Integer> plantsToDelete = new ArrayList<Integer>();
        try{
            FileInputStream f = new FileInputStream(plantsFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(f));
            String line;
            boolean firstLine = true;
            while((line = br.readLine()) != null){
                String[] splitLine = line.split(",");
                if(firstLine){
                    firstLine = false;
                    continue;
                }
                if(Integer.parseInt(splitLine[1]) == id){
                    plantsToDelete.add(Integer.parseInt(splitLine[0]));
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

        for(int i = 0; i < plantsToDelete.size(); i++){
            deletePlant(plantsToDelete.get(i));
        }

        deleteById(reservoirsFile, id);
    }

    public void deleteRoom(int id){
        ArrayList<Integer> resToDelete = new ArrayList<Integer>();
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
                if(Integer.parseInt(splitLine[1]) == id){
                    resToDelete.add(Integer.parseInt(splitLine[0]));
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

        for(int i = 0; i < resToDelete.size(); i++){
            deleteReservoir(resToDelete.get(i));
        }

        deleteById(roomsFile, id);
        deleteById(ownersFile, id, 1);
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
                    br.close();
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
                    int current = Integer.parseInt(splitLine[4]);
                    return new Room(id, name, lowest, highest, current);
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

    public ArrayList<WaterReservoir> reservoirsFromRoom(int roomId){
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
                if(Integer.parseInt(splitLine[1]) == roomId){
                    int id = Integer.parseInt(splitLine[0]);
                    String name = splitLine[2];
                    float capacity = Float.parseFloat(splitLine[3]);
                    float warning = Float.parseFloat(splitLine[4]);
                    float current = Float.parseFloat(splitLine[5]);
                    reservoirs.add(new WaterReservoir(name, capacity, warning, id, current));
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

    public ArrayList<PlantPot> plantsFromReservoir(WaterReservoir res){
        ArrayList<PlantPot> plants = new ArrayList<PlantPot>();
        int resId = res.id;

        try{
            FileInputStream f = new FileInputStream(plantsFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(f));
            String line;
            boolean firstLine = true;
            while((line = br.readLine()) != null){
                String[] splitLine = line.split(",");
                if(firstLine){
                    firstLine = false;
                    continue;
                }
                if(Integer.parseInt(splitLine[1]) == resId){
                    int id = Integer.parseInt(splitLine[0]);
                    String name = splitLine[2];
                    String type = splitLine[3];
                    Float desHum = Float.parseFloat(splitLine[4]);
                    Float minHum = Float.parseFloat(splitLine[5]);
                    Float maxTem = Float.parseFloat(splitLine[6]);
                    Float minTem = Float.parseFloat(splitLine[7]);
                    Float curHum = Float.parseFloat(splitLine[8]);
                    int lightHours = Math.round(Float.parseFloat(splitLine[9]));
                    plants.add(new PlantPot(id, res, name, type, desHum, minHum, maxTem, minTem, curHum, lightHours));
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

        return plants;
    }

    public void tempReading(int id, int temp){
        ArrayList<String> original = originalFile(roomsFile);

        try{
            FileWriter fw = new FileWriter(roomsFile, false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            for(int i = 0; i < original.size(); i++){
                if(original.get(i).split(",")[0].equals(String.valueOf(id))){
                    String[] splitLine = original.get(i).split(",");
                    String line = splitLine[0]+","+splitLine[1]+","+splitLine[2]+","+splitLine[3]+","+String.valueOf(temp);
                    pw.println(line);
                }
                else{
                    pw.println(original.get(i));
                }

            }
            pw.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void humidityReading(int id, Float humidity){
        ArrayList<String> original = originalFile(plantsFile);

        try{
            FileWriter fw = new FileWriter(plantsFile, false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            for(int i = 0; i < original.size(); i++){
                if(original.get(i).split(",")[0].equals(String.valueOf(id))){
                    String[] splitLine = original.get(i).split(",");
                    String line = splitLine[0]+","+splitLine[1]+","+splitLine[2]+","+splitLine[3]+","+splitLine[4]+","+splitLine[5]+","+splitLine[6]+","+splitLine[7]+","+String.valueOf(humidity)+","+splitLine[9];
                    pw.println(line);
                }
                else{
                    pw.println(original.get(i));
                }

            }
            pw.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void storeWaterLevel(int id, float level){
        ArrayList<String> original = originalFile(reservoirsFile);

        try{
            FileWriter fw = new FileWriter(reservoirsFile, false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            for(int i = 0; i < original.size(); i++){
                if(original.get(i).split(",")[0].equals(String.valueOf(id))){
                    String[] splitLine = original.get(i).split(",");
                    String line = splitLine[0]+","+splitLine[1]+","+splitLine[2]+","+splitLine[3]+","+splitLine[4]+","+String.valueOf(level);
                    pw.println(line);
                }
                else{
                    pw.println(original.get(i));
                }

            }
            pw.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    private ArrayList<String> originalFile(File file){
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
        return original;
    }
}
