//Imports fot the Subsystem and its functions
package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.*;
//import com.ctre.phoenix.motorcontrol.FeedbackDevice;

public class HazyLowFeeder extends SubsystemBase {

    private TalonSRX lowFeederTalon; 
    
    public HazyLowFeeder(){
        lowFeederTalon = new TalonSRX(RobotMap.LOWFEEDERTALON);
    }

    public void swallow() {
        lowFeederTalon.set(ControlMode.PercentOutput, RobotMap.FEEDERSPEED);
    }

    public void spit(){
        lowFeederTalon.set(ControlMode.PercentOutput, -RobotMap.FEEDERSPEED);

    }

    public void stop(){
        lowFeederTalon.set(ControlMode.PercentOutput, 0);
    }

}