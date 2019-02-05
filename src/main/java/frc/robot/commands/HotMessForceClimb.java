package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import static frc.robot.Robot.hotMess;

public class HotMessForceClimb extends Command {

    public HotMessForceClimb() {
        requires(hotMess);
    }

    @Override
    protected void execute() {
        hotMess.climb(.5);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        hotMess.stop();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
