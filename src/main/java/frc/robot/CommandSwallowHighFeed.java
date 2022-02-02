///Imports fot the Command
package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.*;

public class CommandSwallowHighFeed extends CommandBase {

    private HazyHighFeeder c_hazyHighFeeder;
    public CommandSwallowHighFeed(HazyHighFeeder feeder){
        c_hazyHighFeeder = feeder;
        addRequirements(c_hazyHighFeeder);
    }

    public void execute(){
        c_hazyHighFeeder.swallow();
    }
}