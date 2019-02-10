package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import static frc.robot.Robot.elevator;

/**
 * Add your docs here.
 */
public class GoToLevel extends Command{

    public int level;

    public GoToLevel(int level) {
        requires(elevator);
        this.level = level;
    }

    @Override
    protected void execute() {
        elevator.goToLevel(level);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
