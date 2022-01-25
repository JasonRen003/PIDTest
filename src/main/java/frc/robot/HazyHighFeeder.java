//Imports fot the Subsystem and its functions
package frc.robot;


import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.math.MathUtil;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class HazyHighFeeder extends SubsystemBase {

    private TalonSRX highFeederTalon; 
    
    public HazyHighFeeder(){
        highFeederTalon = new TalonSRX(RobotMap.HIGHFEEDERTALON);
    }
    
    public void spit() {
        highFeederTalon.set(ControlMode.PercentOutput, RobotMap.FEEDERSPEED);
    }

    public void swallow() {
        highFeederTalon.set(ControlMode.PercentOutput, -RobotMap.FEEDERSPEED);
    }

    public void stop(){
        highFeederTalon.set(ControlMode.PercentOutput, 0);
    }
    
}