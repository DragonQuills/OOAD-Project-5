import java.util.Scanner;
import java.util.ArrayList;

public class UI {
	User user;
	Scanner scanner;
	StorageHandler storage;
	PlantFactory factory;

	UI(){
		scanner = new Scanner(System.in); //For terminal input, reference: https://alvinalexander.com/java/edu/pj/pj010005/
		storage = StorageHandler.getInstance();
		factory = new PlantFactory();
	}

	public void start(){
		int userId = -1;
		boolean newUser = false;
		while(userId == -1){
			System.out.println("Select an option:");
			System.out.println("1. Login");
			System.out.println("2. Register");
			int intInput = -1;
			try {
				intInput = scanner.nextInt();
			}
			catch (Exception e){

			}
			if(intInput == 1){
				userId = login();
				newUser = false;
			}
			else if(intInput == 2){
				//Redirect to register
				userId = register();
				newUser = true;
			}
			else{
				System.out.println("Input not valid. Please enter 1 or 2.");
			}
		}

		user = new User(userId);
		ArrayList<Room> userRooms = storage.roomsFromUser(userId);
		for(int room = 0; room < userRooms.size(); room++){
			ArrayList<WaterReservoir> reservoirs = storage.reservoirsFromRoom(userRooms.get(room).id);
			for(int res = 0; res < reservoirs.size(); res++){
				userRooms.get(room).add_res(reservoirs.get(res));
				ArrayList<PlantPot> plants = storage.plantsFromReservoir(reservoirs.get(res));
				for(int plant = 0; plant < plants.size(); plant++){
					userRooms.get(room).add_plant(plants.get(plant));
				}
			}
			user.add_room(userRooms.get(room));
		}

		if(!newUser){
			hoursPassed();
		}
	}

	private int login(){
		// int userId = -1;
		System.out.println("Enter a username: ");
        String username = scanner.next();
        System.out.println("Enter a password: ");
		String password = scanner.next();
		return storage.loginUser(username, password);
	}

	private int register(){
		System.out.println("Enter a username: ");
        String username = scanner.next();
        System.out.println("Enter a password: ");
		String password = scanner.next();
		return storage.registerUser(username, password);
	}

	private void hoursPassed(){
		System.out.println("How many hours have passed?");
		int hours = scanner.nextInt();
		scanner.nextLine();
		if(hours < 0){
			System.out.println("Not a valid input");
			return;
		}

		int temp_change = 0;
		while(true){
			System.out.println("What's the outdoor tempurature?:");
			String outdoor_temp = scanner.nextLine();
			try {
				int temp = Integer.parseInt(outdoor_temp);
				if (temp < 0 || temp > 120){
					System.out.println("Uh, are you sure? That doesn't seem right...");
					System.out.println("Please enter a temperature between 0 and 120 degrees Fahrenheit.");
				}
				else{
					temp_change = (temp - 60) / 20;
					break;
				}
			}
			catch(NumberFormatException e) {
				System.out.println("Input must be a number. Please try again:");
			}
		}

		for(int i = 0; i < hours; i++){
		int time = i%24 +1;
		System.out.println("It is currently " + time + " o'clock.");
			user.hour_passed(temp_change);
		}
	}

	public void mainMenu(){
		while(true){
			//Print main menu
			System.out.println("Select an option:");
			System.out.println("1. Add Room");
			System.out.println("2. Add Reservoir");
			System.out.println("3. Add Plant");
			System.out.println("4. View Rooms");
			System.out.println("5. Quit");
			int intInput = scanner.nextInt();
			scanner.nextLine();
			if(intInput == 1){
				addRoom();
			}
			else if(intInput == 2){
				addReservoir();
			}
			else if(intInput == 3){
				addPlant();
			}
			else if(intInput == 4){
				viewRooms();
			}
			else if(intInput == 5){
				return;
			}
			else{
				System.out.println("Not a valid input");
			}
		}
	}

	private void addRoom(){
		System.out.println("Enter the name of your new room: ");
		String room_name = scanner.nextLine();
		String min_temp;
		String max_temp;
		int temp_min;
		int temp_max;
		while(true){
			System.out.println("Minimum Temperature of new room in degrees Fahrenheit: ");
			min_temp = scanner.nextLine();
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
			max_temp = scanner.nextLine();
			try {
				temp_max = Integer.parseInt(max_temp);
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
		Room newRoom = storage.createRoom(room_name, temp_min, temp_max, user.id);

		user.add_room(newRoom);

		System.out.println("Created room "+newRoom.name);
	}

	private void addReservoir(){
		System.out.println("Enter reservoir name: ");
		String reservoirName = scanner.next();
		System.out.println("Max capacity: ");
		int capacity = scanner.nextInt();
		System.out.println("Warning level: ");
		int warning = scanner.nextInt();
		scanner.nextLine();
		Room selectedRoom = selectRoom("Which room is the reservoir in?");
		WaterReservoir temp_res = storage.createReservoir(selectedRoom.id, reservoirName, capacity, warning);
		selectedRoom.add_res(temp_res);

		System.out.println("Created reservoir "+temp_res.name);
	}

	private Room selectRoom(String prompt){
		String roomNames = user.roomNames();
		int selectedRoom = -1;
		while(true){
			System.out.println(prompt);
			System.out.println(roomNames);
			int roomChoice = scanner.nextInt();
			scanner.nextLine();
			if(roomChoice > 0 && roomChoice <= user.numRooms()){
				selectedRoom = roomChoice - 1;
				break;
			}
			else{
				System.out.println("Not a valid room");
			}
		}
		return user.get_room(selectedRoom);
	}

	private WaterReservoir selectReservoir(String prompt, Room room){
		String resNames = room.reservoirNames();
		int selectedRes = -1;
		while(true){
			System.out.println(prompt);
			System.out.println(resNames);
			int resChoice = scanner.nextInt();
			scanner.nextLine();
			if(resChoice > 0 && resChoice <= room.numRes()){
				selectedRes = resChoice - 1;
				break;
			}
			else{
				System.out.println("Not a valid room");
			}
		}
		return room.get_reservoir(selectedRes);
	}

	private PlantPot selectPlant(String prompt, Room room){
		String plantNames = room.plantNames();
		int selectedPlant = -1;
		while(true){
			System.out.println(prompt);
			System.out.println(plantNames);
			int plantChoice = scanner.nextInt();
			scanner.nextLine();
			if(plantChoice > 0 && plantChoice <= room.numPlants()){
				selectedPlant = plantChoice - 1;
				break;
			}
			else{
				System.out.println("Not a valid room");
			}
		}
		return room.get_plant(selectedPlant);
	}

	private void addPlant(){
		ArrayList<Room> rooms_list = user.get_rooms_list();
		if (rooms_list.isEmpty()) {
			System.out.println("You cannot add a plant until you have added a room.");
			System.out.println("Exiting...");
			return;
		}

		Room selected_room = selectRoom("What room is the plant in?");

		WaterReservoir selected_reservoir = selectReservoir("What reservoir is the plant attached to?", selected_room);

		System.out.println("Enter plant name:");//This has to be first as input for PlantFactory
		String plant_name = scanner.nextLine();
		System.out.println("Enter plant type");
		String plant_type = scanner.nextLine();
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
			user_likes_data = scanner.nextLine();
		}
		//else, tell the user they must enter data
		else {
			System.out.println("No data about plant type "+ plant_type);
			user_likes_data = "n";
		}

		while(true){ 
			//If user likes data, continue with new_plant as-is
			if (user_likes_data.equals("y")){
				storage.createPlant(new_plant);
				break;
			}
			//Case: User wants to enter their own data
			if (user_likes_data.equals("n")){
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
						}System.out.println("Invalid Input. Please type y or n and press Enter.");
						user_likes_data = scanner.nextLine();
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
							Light new_light = new Light();
							Command light_on = new LightOnCommand(new_light);
							Command light_off = new LightOffCommand(new_light);
							Timer new_timer = new Timer(light_on, light_off,light);
							new_plant.set_timer(new_timer);
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
				storage.createPlant(new_plant);
				break;
			}
			//Catch invalid input, request new input
			//Should re-iterate at top of the "parent" while loop with new user_likes_data value
			else {
				System.out.println("Invalid Input. Please type y or n and press Enter.");
				user_likes_data = scanner.nextLine();
			}
		}
	}

	private void viewRooms(){
		Room selectedRoom = selectRoom("Select a room:");
		//At this point a room has been selected
		System.out.println(selectedRoom.status_report());
		roomMenu(selectedRoom);
	}

	private void roomMenu(Room selectedRoom){
		boolean quitSubmenu = false;
		while(!quitSubmenu){
			System.out.println("Select an option:");
			System.out.println("1 View plants");
			System.out.println("2 View reservoirs");
			System.out.println("3 Rename room");
			System.out.println("4 Delete room");
			System.out.println("5 Return to main menu");
			int menuChoice = scanner.nextInt();
			scanner.nextLine();
			if(menuChoice == 1){
				PlantPot selectedPlant = selectPlant("Select a plant", selectedRoom);
				System.out.println(selectedPlant.status_report());
				plantMenu(selectedPlant, selectedRoom);
			}
			else if(menuChoice == 2){
				WaterReservoir selectedRes = selectReservoir("Select a reservoir", selectedRoom);
				System.out.println(selectedRes.status_report());
				reservoirMenu(selectedRes, selectedRoom);
			}
			else if(menuChoice == 3){
				System.out.println("What would you like to call the room?");
				String name = scanner.nextLine();
				selectedRoom.name = name;
			}
			else if(menuChoice == 4){
				System.out.println("Deleted room " +selectedRoom.name);

				storage.deleteRoom(selectedRoom.id);
				user.remove_room(selectedRoom.name);
				return;
			}
			else if(menuChoice == 5){
				return;
			}
			else{
				System.out.println("Not a valid choice");
			}
		}
	}

	private void plantMenu(PlantPot plant, Room room){
		while(true){
			System.out.println("Select an option: ");
			System.out.println("1. Delete plant");
			System.out.println("2. Rename plant");
			System.out.println("3. Quit menu");

			int choice = scanner.nextInt();
			scanner.nextLine();
			if(choice == 1){
				storage.deletePlant(plant.id);
				room.remove_plant_by_id(plant.id);
				return;
			}
			else if(choice == 2){
				System.out.println("What would you like to call the plant?");
				String name = scanner.nextLine();
				plant.name = name;
				storage.changePlantName(plant.id, name);
			}
			else if(choice == 3){
				return;
			}
			else{
				System.out.println("Not a valid input");
			}
		}
		
	}

	private void reservoirMenu(WaterReservoir reservoir, Room room){
		while(true){
			System.out.println("Select an option: ");
			System.out.println("1. Delete reservoir");
			System.out.println("2. Rename reservoir");
			System.out.println("3. Refill reservoir");
			System.out.println("4. Quit menu");

			int choice = scanner.nextInt();
			scanner.nextLine();
			if(choice == 1){
				System.out.println("Deleted reservoir "+reservoir.name+" and attached plants");
				storage.deleteReservoir(reservoir.id);
				ArrayList<Integer> plantsToDelete = new ArrayList<Integer>();
				ArrayList<PlantPot> listOfPlants = room.get_plants();
				for(int p = 0; p < listOfPlants.size(); p++){
					if(listOfPlants.get(p).resMatches(reservoir)){
						plantsToDelete.add(p);
					}
				}
				for(int d = plantsToDelete.size()-1; d >= 0; d--){
					room.remove_plant(plantsToDelete.get(d));
				}
				room.remove_reservoir_by_id(reservoir.id);
				return;
			}
			else if(choice == 2){
				System.out.println("What would you like to call the reservoir?");
				String name = scanner.nextLine();
				reservoir.name = name;
				storage.changeReservoirName(reservoir.id, name);
			}
			else if(choice == 3){
				reservoir.water_refilled();
			}
			else if(choice == 4){
				return;
			}
			else{
				System.out.println("Not a valid input");
			}
		}
	}
}