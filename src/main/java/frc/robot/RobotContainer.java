package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import static edu.wpi.first.wpilibj.XboxController.Button;

import javax.lang.model.util.ElementScanner6;
public class RobotContainer {

    //Robot's controllers that drivers use
    XboxController hazyController = new XboxController(RobotMap.CONTROLLERPORT);
    Joystick leftJoystick = new Joystick(RobotMap.LEFTJOYSTICKPORT);
    Joystick rightJoystick = new Joystick(RobotMap.RIGHTJOYSTICKPORT);

    //Chassis subystem and command setup
    HazyMechBase hazyMechBase = new HazyMechBase();
    HazyShooter hazyShooter = new HazyShooter();
    
    CommandMecanum commandMecanum = new CommandMecanum(hazyMechBase, leftJoystick, rightJoystick);
    CommandShoot commandShoot = new CommandShoot(hazyShooter,hazyController);
    CommandPIDShoot commandPIDShoot = new CommandPIDShoot(hazyShooter, hazyController);
    
    //Ball feeder subsystem and command setup
    HazyHighFeeder hazyHighFeeder = new HazyHighFeeder();
    HazyLowFeeder hazyLowFeeder = new HazyLowFeeder();
    CommandHighFeedDefault commandHighFeedDefault = new CommandHighFeedDefault(hazyHighFeeder);
    CommandSpitHighFeed commandSpitHighFeed = new CommandSpitHighFeed(hazyHighFeeder);
    CommandSwallowHighFeed commandSwallowHighFeed = new CommandSwallowHighFeed(hazyHighFeeder);
    CommandLowFeedDefault commandLowFeedDefault = new CommandLowFeedDefault(hazyLowFeeder);
    CommandSpitLowFeed commandSpitLowFeed = new CommandSpitLowFeed(hazyLowFeeder);
    CommandSwallowLowFeed commandSwallowLowFeed = new CommandSwallowLowFeed(hazyLowFeeder);

    //set default commands inside RobotContainer constructor
    //This constructor is called once in Robotinit and should set up all button-> command bindings and default commands
    public RobotContainer(){
        configureButtonBindings();
        hazyMechBase.setDefaultCommand(commandMecanum);
        hazyShooter.setDefaultCommand(commandShoot);
        hazyHighFeeder.setDefaultCommand(commandHighFeedDefault);
        hazyLowFeeder.setDefaultCommand(commandLowFeedDefault);
    }

    //Use this method to define button->command mappings
    private void configureButtonBindings(){

        new JoystickButton(hazyController, Button.kRightBumper.value).toggleWhenPressed(commandPIDShoot);
        new JoystickButton(rightJoystick, 1).whileHeld(commandSwallowLowFeed);
        //new JoystickButton(rightJoystick, 1).whileHeld(commandSwallowIntake);
        new JoystickButton(leftJoystick, 1).whileHeld(commandSpitLowFeed);
        //new JoystickButton(leftJoystick, 1).whileHeld(commandSpitIntake);

    }

    public void DPadWrapper(){ //You can't make the dpad a joystick button to use "whileHeld" or "whilePressed" functions on, so we have to create a wrapper to run ourselves
        if(hazyController.getPOV() == 90){
            hazyHighFeeder.spit();
        }
        else if(hazyController.getPOV() == 270){
            hazyHighFeeder.swallow();
        }
        else{
            hazyHighFeeder.stop();
        }
    }


}
