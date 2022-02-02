//Imports fot the Subsystem and its functions
package frc.robot;

import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.math.MathUtil;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.SerialPort;



public class HazyMechBase extends SubsystemBase{
    
    private TalonSRX rightFrontTalon;
    private TalonSRX leftFrontTalon;
    private TalonSRX leftBackTalon;
    private TalonSRX rightBackTalon;

    private Solenoid solenoidToLight;

    private SerialPort visionPort;

    private double offset; 
    private boolean delayed;
    private boolean turnDelay;
    private double distance;
    private double milStart;
    private double lastData;
    
    //Constructor includes PID (if necessary) value setup for motors and initialization of all motors in subsystem
    public HazyMechBase(){
      rightFrontTalon = new TalonSRX(RobotMap.RIGHTFRONTTALONPORT);
      leftBackTalon = new TalonSRX(RobotMap.LEFTBACKTALONPORT);
      leftFrontTalon = new TalonSRX(RobotMap.LEFTFRONTTALONPORT);
      rightBackTalon = new TalonSRX(RobotMap.RIGHTBACKTALONPORT);
      leftFrontTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

      solenoidToLight = new Solenoid(PneumaticsModuleType.CTREPCM, 0);
    }

    //A Necessary setup function for drive cartesian
    //Sets a max speed value that the wheels can't exceed
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

    //Setup function for DriveCartesian
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


    //Mecanum drive function that is called by the default 
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

    //Get's Talon speed from encoder
    private double getSpeed(TalonSRX tally){
      double gearRatio = (1/13.2); 
      double rotations = tally.getSelectedSensorVelocity() * (10.0/4096.0) * gearRatio * 60.0; //Get selectedSensorVelocity returns in encoder ticks per 100ms, we need to use dimensional analysis to convert that to rpm (4096 encoder ticks per rotation for talons)
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
      //System.out.println((double)Math.round(speed*100)/100 + ", " + (double)Math.round(volts*100)/100);
    }

    public void goToTarget(){
      solenoidToLight.set(true);

      if (delayed){
        milStart = java.lang.System.currentTimeMillis();
        delayed = false;
      }
      if(java.lang.System.currentTimeMillis() > milStart + RobotMap.VISIONDELAY){
        double travelDistance;
        if(distance == -1.0)
          travelDistance = 0.0;
        else
          travelDistance = RobotMap.SHOOTDISTANCE - distance;
        //System.out.println(java.lang.System.currentTimeMillis()-lastData);
        double turnPower = clamp(RobotMap.VISIONTURN * offset);
        if(turnPower > -0.105 && turnPower < 0.0 && Math.abs(offset) >= 10.0)
          turnPower = -0.105;
        else if(turnPower < 0.105 && turnPower > 0.0 && Math.abs(offset) >= 10.0)
          turnPower = 0.105;
        
        if(Math.abs(offset) < 10.0)
          turnPower = 0.0;
        
        double forwardPower =clamp( -travelDistance*RobotMap.VISIONSPEED);
        System.out.println("turn: " + turnPower + " forward: " + forwardPower);
        driveCartesian(0, -forwardPower, -turnPower);
      }
    }

    public void turnToTarget(){
      solenoidToLight.set(true);
      // rightFrontTalon.config_kP(0, RobotMap.DRIVEP, 30);
      // rightBackTalon.config_kP(0, RobotMap.DRIVEP, 30);
      // leftFrontTalon.config_kP(0, RobotMap.DRIVEP, 30);
      // leftBackTalon.config_kP(0, RobotMap.DRIVEP, 30);
      // leftBackTalon.selectProfileSlot(slotIdx, pidIdx);
      if (delayed){
        milStart = java.lang.System.currentTimeMillis();
        delayed = false;
      }
      if(java.lang.System.currentTimeMillis() > milStart + RobotMap.VISIONDELAY){
        double turnPower = RobotMap.VISIONVELTURN * (offset-RobotMap.RIGHTSIDEOFFSET);
        rightFrontTalon.set(ControlMode.Velocity,turnPower);
        rightBackTalon.set(ControlMode.Velocity,turnPower);
        leftFrontTalon.set(ControlMode.Velocity,turnPower);
        leftBackTalon.set(ControlMode.Velocity,turnPower);
      }
    }

    public void toggleDelayed(){
      delayed = true;
    }

    public void toggleTurnDelay(){
      turnDelay = true;
    }

    public void readData(){
      String data = visionPort.readString();
      
      //System.out.println(data);
      if(data.equals("none")){
        offset = 0.0;
        distance = -1.0;
      }
      if(!data.equals("") && !data.equals("none")){
        try{
        offset = Double.parseDouble(data.substring(8,data.indexOf("distance")));
        distance = Double.parseDouble(data.substring(data.indexOf("distance")+10));
        if(distance > 2000)
          distance = -1;   
        
        
        else{
          lastData = java.lang.System.currentTimeMillis();
        }
      }
        catch (Exception e){
          e.printStackTrace();
        }
      }
        
    }

    private double clamp(double input){
      if(input>RobotMap.MAXVISIONSPEED)
        return RobotMap.MAXVISIONSPEED; 
      else if (input < -RobotMap.MAXVISIONSPEED)
        return -RobotMap.MAXVISIONSPEED;
      return input;
    }

    
}
