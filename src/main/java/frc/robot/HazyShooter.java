//Imports fot the Subsystem and its functions
package frc.robot;


import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.math.MathUtil;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class HazyShooter extends SubsystemBase {

    private static TalonSRX shooterTalon;
    

    public HazyShooter(){
        shooterTalon = new TalonSRX(RobotMap.SHOOTERTALONPORT);
        shooterTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
        shooterTalon.config_kP(0, 0.7);
        shooterTalon.config_kI(0,0.000015);
        shooterTalon.config_kD(0,0.35);
        shooterTalon.config_kF(0, -(12/6000));
    }
    
    public void UpdateRPM() {
        //System.out.println("SmartDashboard is a go");
        // SmartDashboard.putNumber("TPR", shooterTalon.getSelectedSensorVelocity());
        // SmartDashboard.putNumber("with pidx?", shooterTalon.getSelectedSensorVelocity(0));
    }

    public void shooterSpit(){ //Functions actually used by commands
        //shooterTalon.set(ControlMode.PercentOutput, val);
        shooterTalon.set(ControlMode.Velocity, -RobotMap.SHOOTERSETPOINT / (10.0/4096.0) / 60);
    }

    // public void shooterSwallow(){
    //     shooterTalon.set(ControlMode.PercentOutput, RobotMap.SHOOTERSPEED);
    // }

    public void stopShooter(){
        shooterTalon.set(ControlMode.PercentOutput, 0);
    }

    public double getShooterRPM(){
        return -shooterTalon.getSelectedSensorVelocity()*(10.0/4096.0)*60;
    }

    public void getSpeed(){
        double gearRatio = (8/21);
        double rotations = shooterTalon.getSelectedSensorVelocity() * (10.0/4096.0) * 60.0;
        double voltage = shooterTalon.getMotorOutputVoltage();
        //double wheelradius = 0.25; //in feet
        //double velocity = rotations * 2.0*Math.PI*wheelradius / 60.0;
        //System.out.println("RPM: " + rotations + " Voltage: " + voltage);
        SmartDashboard.putNumber("TPR", shooterTalon.getSelectedSensorVelocity()*(10.0/4096.0) * 60.0);
    }


}