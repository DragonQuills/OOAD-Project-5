//Command Patttern

public interface Command {
  public void execute();
}

//Physical Objects used by Commands


//Theoretically we could use a pattern to implement on and off functions
//We don't need to but its an option
class Light{
  public void on(){}
  public void off(){}
}

class AC {
  public void on() {}
  public void off() {}
}

class Heater{
  public void on(){}
  public void off(){}
}

class Hose{
  WaterReservoir res;
  public void on(){
    System.out.println("The hose is on.");
    res.water_used(5);
    System.out.println(res.report_current_level());
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
