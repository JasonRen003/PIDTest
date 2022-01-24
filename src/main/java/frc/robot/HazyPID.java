// package frc.robot;

// import edu.wpi.first.wpilibj2.command.Subsystem;

// public class HazyPID {

//     Subsystem hazySubsystem;
//     public HazyPID(Subsystem subby){
//         int P, I, D = 1;
//         int integral, previous_error, setpoint = 0;
//         hazySubsystem = subby;
//     }

//     public void PIDcalc(int setpoint)
//     {
//         this.setpoint = setpoint;
//     }

//     public void PID(){
//         error = setpoint - gyro.getAngle(); // Error = Target - Actual
//         this.integral += (error*.02); // Integral is increased by the error*time (which is .02 seconds using normal IterativeRobot)
//         derivative = (error - this.previous_error) / .02;
//         this.rcw = P*error + I*this.integral + D*derivative;
//     }
// }
