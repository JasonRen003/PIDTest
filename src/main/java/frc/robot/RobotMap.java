//Imports fot the Robot Map
package frc.robot;
import edu.wpi.first.wpilibj.I2C;

public class RobotMap { //Static port variables
    //Joysticks 
    public static final int LEFTJOYSTICKPORT = 2;
    public static final int RIGHTJOYSTICKPORT = 1;

    //Controller
    public static final int CONTROLLERPORT = 0;
    
    //HazyMecBase
    public static final int RIGHTFRONTTALONPORT = 0;
    public static final int LEFTFRONTTALONPORT = 2;
    public static final int RIGHTBACKTALONPORT = 1;
    public static final int LEFTBACKTALONPORT = 13;
    public static final double TICKSPERFEET = 3000.0;
    public static final int VISIONDELAY = 500;

    //Vision
    public static final double VISIONTURN = .001;
    public static final double VISIONVELTURN = 15.0;
    public static final double VISIONSPEED = .007;
    public static final double SHOOTDISTANCE = 204;
    public static final double RIGHTSIDEOFFSET = 10.0;
    
    //ColorSensor
    public static final I2C.Port COLORSENSORPORT = I2C.Port.kOnboard;

    //ColorArm
    public static final int ELBOWTALONPORT = 10;
    public static final int COLORWHEELTALONPORT = 11;
    public static final Double ELBOWP = 2.0;
    public static final Double ELBOWI = 0.0;
    public static final Double ELBOWD = 0.0;

    //PID
    public static final double DEADBAND = 0.02;

    //HazyIntake
    public static final int LIFTINTAKETALON = 4;
    public static final int SPININTAKEVICTOR = 5;
    public static final double LIFTTALONSPEED = 0.2;
    public static final double SPINVICTORSPEED = 0.5;

    //Ball Feeders
    public static final int LOWFEEDERTALON = 15;
    public static final int HIGHFEEDERTALON = 14;
    public static final double FEEDERSPEED = 0.7;

    //Shooter
    public static final int SHOOTERTALONPORT = 12;
    public static final double SHOOTERSETPOINT = 5600;
    
    //IMU
    public static final int PIGEONIMU = 0;

    //Auton
    public static final int STARTINGPOSITION = 0;
    
    public static final int TURNANGLE1 = 180;
    
    public static final double MAXVISIONSPEED = 0.5;
    
    public static final double DRIVEP = 0.08;
    public static final double DRIVEI = 0.0;
    public static final double DRIVED = 0.0;
    public static final double DRIVEF = 0.0;
    
    public static final double TRENCHP = 0.08;
    
    public static final double STRAFEP = 1.0;
    
    public static final double TURNP = 0.18;
    
    public static final double SIDETICKSPERFEET = 4600.0;
    public static final double INITIALFEET = 7.0;
    public static final double STARTAUTONDELAY = 500.0;
    public static final double TURN180TICKS = 18193.0;
    public static final double SHOOTRPM = 30000.0;
    //End Arm
    public static final int ENDARMTALONPORT = 3;

    //Serial Port
    public static final int BAUDRATE = 115200;
}
