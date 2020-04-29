//Command Patttern
//Implemented to control the physical objects that actually maintain the plants.
//This pattern was chosen because it provides encapsulation such that this system
//could be implemented on real hardware without any restructuring of the classes.


public interface Command {
  public void execute();
}

// Simulation of physical Objects used by Commands
class Light{
  public void on(){
    System.out.println("The light is on.");
  }
  public void off(){
    System.out.println("The light is off.");
  }
}


class AC {
  public void on() {
    System.out.println("The AC is on.");
  }
  public void off() {
    System.out.println("The AC is off.");
  }
}


class Heater{
  public void on(){
    System.out.println("The Heater is on");
  }
  public void off(){
    System.out.println("The Heater is off");
  }
}


class Hose{
  WaterReservoir res;
  public void on(){
    System.out.println("The hose is on.");
    res.water_used(5);
  }
  public void off(){
    System.out.println("The hose is off.");
  }

  Hose(WaterReservoir wr){res = wr;}
}


//Specific Commands
//Turn on the lights.
class LightOnCommand implements Command {
  Light light;

  public LightOnCommand(Light light) {
    this.light = light;
  }

  public void execute() {
    light.on();
  }
}

//Turn off the lights
class LightOffCommand implements Command {
  Light light;

  public LightOffCommand(Light light) {
    this.light = light;
  }

  public void execute(){
    light.off();
  }
}

//Start pumping water into plant(s)
class WaterOnCommand implements Command {
  Hose hose;

  public WaterOnCommand(Hose hose) {
    this.hose = hose;
  }

  public void execute(){
    hose.on();
  }
}

//Stop pumping water into plant(s)
class WaterOffCommand implements Command {
  Hose hose;

  public WaterOffCommand(Hose hose) {
    this.hose = hose;
  }

  public void execute(){
    hose.off();
  }
}

//Heat up the Room
//Turns on Heater, turns off AC.
class HeatUpCommand implements Command {
  AC ac;
  Heater heater;

  public HeatUpCommand(AC ac, Heater heater) {
    this.ac = ac;
    this.heater=heater;
  }

  public void execute(){
    ac.off();
    heater.on();
  }
}

//Cool down the Room
//Turns off heater, turns on AC.
class CoolDownCommand implements Command {
  AC ac;
  Heater heater;

  public CoolDownCommand(AC ac, Heater heater) {
    this.ac = ac;
    this.heater = heater;
  }

  public void execute(){
    ac.on();
    heater.off();
  }
}

//Keep Room the same temperature
//Turns off heater, turns off AC.
class SameTempCommand implements Command {
  AC ac;
  Heater heater;

  public SameTempCommand(AC ac, Heater heater){
    this.ac = ac;
    this.heater = heater;
  }

  public void execute(){
    ac.off();
    heater.off();
  }
}
