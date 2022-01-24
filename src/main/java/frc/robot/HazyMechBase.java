//Imports fot the Subsystem and its functions
package frc.robot;

import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.math.MathUtil;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;



public class HazyMechBase extends SubsystemBase{
    
    private TalonSRX rightFrontTalon;
    private TalonSRX leftFrontTalon;
    private TalonSRX leftBackTalon;
    private TalonSRX rightBackTalon;
    
    
    public HazyMechBase(){
      rightFrontTalon = new TalonSRX(RobotMap.RIGHTFRONTTALONPORT);
      leftBackTalon = new TalonSRX(RobotMap.LEFTBACKTALONPORT);
      leftFrontTalon = new TalonSRX(RobotMap.LEFTFRONTTALONPORT);
      rightBackTalon = new TalonSRX(RobotMap.RIGHTBACKTALONPORT);
      leftFrontTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
    }

    protected void normalize(double[] wheelSpeeds) {
      double maxMagnitude = Math.abs(wheelSpeeds[0]);
      
      for (int i = 1; i < wheelSpeeds.length; i++) {
        double temp = Math.abs(wheelSpeeds[i]);
        if (maxMagnitude < temp) {
          maxMagnitude = temp;
        }
      }

      if (maxMagnitude > 1.0) {
        for (int i = 0; i < wheelSpeeds.length; i++) {
        wheelSpeeds[i] = wheelSpeeds[i] / maxMagnitude;
        }
      }
    }

    private double applyDeadband(double value, double deadband) {
      if (Math.abs(value) > deadband) {
        if (value > 0.0) 
          return (value - deadband) / (1.0 - deadband);
        else 
          return (value + deadband) / (1.0 - deadband);  
      } 
      else 
        return 0.0;
    }

    public void driveCartesian(double x, double y, double angle){
      
        y = MathUtil.clamp(y, -1.0, 1.0);
        y = applyDeadband(y, RobotMap.DEADBAND);
    
        x = MathUtil.clamp(x, -1.0, 1.0);
        x = applyDeadband(x, RobotMap.DEADBAND);
      
        double[] wheelSpeeds = new double[4];
        wheelSpeeds[0] = x + y + angle;
        wheelSpeeds[1] = -x + y - angle;
        wheelSpeeds[2] = -x + y + angle;
        wheelSpeeds[3] = x + y - angle;
    
        normalize(wheelSpeeds);
    
        leftFrontTalon.set(ControlMode.PercentOutput, -wheelSpeeds[0] );
        rightFrontTalon.set(ControlMode.PercentOutput, -wheelSpeeds[1] * -1);
        leftBackTalon.set(ControlMode.PercentOutput, -wheelSpeeds[2]);
        rightBackTalon.set(ControlMode.PercentOutput, -wheelSpeeds[3]*-1);
    }

    private double getSpeed(TalonSRX tally){
      double gearRatio = (1/13.2);
      double rotations = tally.getSelectedSensorVelocity() * (10.0/4096.0) * gearRatio * 60.0;
      double wheelradius = 0.25; //in feet
      double velocity = rotations * 2.0*Math.PI*wheelradius / 60.0;
      return velocity;
    }

    public void getGraph(){
      double volts = leftFrontTalon.getMotorOutputVoltage();
      double speed = getSpeed(leftFrontTalon);
      //System.out.println(speed);
      //SmartDashboard.putNumber("Speed", speed);
      //SmartDashboard.putNumber("Voltage", volts);
      System.out.println((double)Math.round(speed*100)/100 + ", " + (double)Math.round(volts*100)/100);
    }
    
}
