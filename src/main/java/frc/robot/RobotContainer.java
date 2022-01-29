package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import static edu.wpi.first.wpilibj.XboxController.Button;
public class RobotContainer {

    //Robot's controllers that drivers use
    XboxController hazyController = new XboxController(RobotMap.CONTROLLERPORT);
    Joystick leftJoystick = new Joystick(RobotMap.LEFTJOYSTICKPORT);
    Joystick rightJoystick = new Joystick(RobotMap.RIGHTJOYSTICKPORT);

    //Chassis subystem and command setup
    HazyMechBase hazyMechBase = new HazyMechBase();
    HazyShooter hazyShooter = new HazyShooter();
    HazyHighFeeder hazyHighFeeder = new HazyHighFeeder();
    CommandMecanum commandMecanum = new CommandMecanum(hazyMechBase, leftJoystick, rightJoystick);
    CommandShoot commandShoot = new CommandShoot(hazyShooter,hazyController);
    CommandPIDShoot commandPIDShoot = new CommandPIDShoot(hazyShooter, hazyController);
    CommandHighFeedDefault commandHighFeedDefault = new CommandHighFeedDefault(hazyHighFeeder);
    CommandSpitHighFeed commandSpitHighFeed = new CommandSpitHighFeed(hazyHighFeeder);

    //set default commands inside RobotContainer constructor
    //This constructor is called once in Robotinit and should set up all button-> command bindings and default commands
    public RobotContainer(){
        configureButtonBindings();
        hazyMechBase.setDefaultCommand(commandMecanum);
        hazyShooter.setDefaultCommand(commandShoot);
        hazyHighFeeder.setDefaultCommand(commandHighFeedDefault);
    }

    //Use this method to define button->command mappings
    private void configureButtonBindings(){

        new JoystickButton(hazyController, Button.kA.value).toggleWhenPressed(commandPIDShoot);
        new JoystickButton(hazyController, Button.kB.value).whenHeld(commandSpitHighFeed);

    } 


}
