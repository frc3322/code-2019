/**
 *  _____    _____     _____     _____   
 * |___  \  |___  \   /  _  \   /  _  \
 *  ___|  |  ___|  | |__| |  | |__| |  |
 * |___   | |___   |     /  /      /  /
 *  ___|  |  ___|  |   /  /__    /  /__
 * |_____/  |_____/   |______|  |______|
 *
 */

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import static frc.robot.Robot.sideouttake;
import static frc.robot.Robot.wideintake;

public class IntakeIdle extends Command {
    
    public IntakeIdle() {
        requires(wideintake);
        requires(sideouttake);
    }

    @Override
    protected void execute() { 
        wideintake.intakeStop();
        sideouttake.outtakeStop();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}
