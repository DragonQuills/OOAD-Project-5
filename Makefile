all: Command.java WaterReservoir.java WaterSensor.java PlantPot.java Timer.java TempuratureSensor.java test_file.java Room.java StorageHandler.java User.java PlantFactory.java UI.java Main.java
	javac Command.java
	javac WaterReservoir.java
	javac WaterSensor.java
	javac PlantPot.java
	javac StorageHandler.java
	javac Timer.java
	javac PlantFactory.java
	javac User.java
	javac Room.java
	javac TempuratureSensor.java
	javac UI.java
	javac Main.java
	javac test_file.java

clean:
	rm *.class

run: all
	# java test_file
	java Main
