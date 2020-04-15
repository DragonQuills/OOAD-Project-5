all: Command.java WaterReservoir.java WaterSensor.java #PlantPot.java Timer.java PlantFactory.java User.java Room.java TempuratureSensor.java test_file.java
	javac Command.java
	javac WaterReservoir.java
	# javac WaterSensor.java
	# javac PlantPot.java
	# javac Timer.java
	# #javac PlantFactory.java
	# javac User.java
	# javac Room.java
	# javac TempuratureSensor.java

	javac test_file.java

clean:
	rm *.class

run:
	java test_file
