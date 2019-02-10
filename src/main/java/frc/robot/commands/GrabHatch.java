package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import static frc.robot.Robot.hatchManip;

/**
 * Add your docs here.
 */
public class GrabHatch extends Command{

    public GrabHatch() {
        requires(hatchManip);
    }

    @Override
    protected void initialize() {
        hatchManip.toggleHatch();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}