package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import static frc.robot.Robot.wideintake;

/**
 * Add your docs here.
 */
public class ToggleIntake extends Command{

    public ToggleIntake(){

        requires(wideintake);

    }

    @Override
    protected void initialize() {
        wideintake.toggleIntake();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
