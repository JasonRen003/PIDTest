package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import static edu.wpi.first.wpilibj.XboxController.Button;
public class RobotContainer {

    //Robot's controllers that drivers use
    XboxController hazyController = new XboxController(RobotMap.CONTROLLERPORT);
    Trigger hazyTriggers = new Trigger();
    Joystick leftJoystick = new Joystick(RobotMap.LEFTJOYSTICKPORT);
    Joystick rightJoystick = new Joystick(RobotMap.RIGHTJOYSTICKPORT);

    //Chassis subystem and command setup
    HazyMechBase hazyMechBase = new HazyMechBase();
    HazyShooter hazyShooter = new HazyShooter();
    CommandMecanum commandMecanum = new CommandMecanum(hazyMechBase, leftJoystick, rightJoystick);
    CommandShoot commandShoot = new CommandShoot(hazyShooter,hazyController);
    CommandPIDShoot commandPIDShoot = new CommandPIDShoot(hazyShooter, hazyController);
    public RobotContainer(){

        configureButtonBindings();
        hazyMechBase.setDefaultCommand(commandMecanum);
        //hazyShooter.setDefaultCommand(commandShoot);
        
    }

    //Use this method to define button->command mappings
    private void configureButtonBindings(){
        new JoystickButton(hazyController, Button.kA.value).whenPressed(commandPIDShoot);
    } 


}
