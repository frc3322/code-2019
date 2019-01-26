package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import static frc.robot.Robot.hotMess;

public class HotMessClimb extends Command{

    double stopVal = 100;

    public HotMessClimb(){
        requires(hotMess);
    }

    @Override
    protected void execute() {
        hotMess.climb();
    }

    @Override
    protected boolean isFinished() {
        return hotMess.getEncoderVal() <= stopVal;
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