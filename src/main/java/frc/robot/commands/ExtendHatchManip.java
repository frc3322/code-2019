package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import static frc.robot.Robot.hatchManip;

/**
 * Add your docs here.
 */
public class ExtendHatchManip extends Command{

    public ExtendHatchManip(){

        requires(hatchManip);

    }

    @Override
    protected void initialize() {
        hatchManip.extendGrabber();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
