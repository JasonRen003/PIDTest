///Imports fot the Command
package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.*;

public class CommandSpitLowFeed extends CommandBase {

    private HazyLowFeeder c_hazyLowFeeder;
    public CommandSpitLowFeed(HazyLowFeeder feeder){
        c_hazyLowFeeder = feeder;
        addRequirements(c_hazyLowFeeder);
    }

    public void execute(){
        c_hazyLowFeeder.spit();
    }

    
}