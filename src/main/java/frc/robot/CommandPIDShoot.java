//Imports fot the Command
package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.*;

public class CommandPIDShoot extends CommandBase{

    private HazyShooter c_hazyShooter;
    private XboxController c_controller;
    // double P = 0.51;
    // int I = 0;
    // double D = 0;
    // int integral, previous_error= 0;

    double setpoint = RobotMap.SHOOTERSETPOINT;
    

    public void PID(){
        
    }

    public CommandPIDShoot(HazyShooter shooter, XboxController controller){
        c_hazyShooter = shooter;
        c_controller = controller;
        addRequirements(c_hazyShooter);
    }

    @Override
    public void execute(){
        c_hazyShooter.shooterSpit(-c_controller.getRightTriggerAxis());
        //System.out.println("Pressed Shooter Spit");
    }

    // @Override
    // protected boolean isFinished(){
    //     return true;
    // }


}