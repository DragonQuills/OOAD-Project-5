/*
Command pattern here!
*/

public interface Command {
  public void execute();
}


// simulation of hysical Objects used by Commands
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
class LightOnCommand implements Command {
  Light light;

  public LightOnCommand(Light light) {
    this.light = light;
  }

  public void execute() {
    light.on();
  }
}


class LightOffCommand implements Command {
  Light light;

  public LightOffCommand(Light light) {
    this.light = light;
  }

  public void execute(){
    light.off();
  }
}


class WaterOnCommand implements Command {
  Hose hose;

  public WaterOnCommand(Hose hose) {
    this.hose = hose;
  }

  public void execute(){
    hose.on();
  }
}


class WaterOffCommand implements Command {
  Hose hose;

  public WaterOffCommand(Hose hose) {
    this.hose = hose;
  }

  public void execute(){
    hose.off();
  }
}


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
