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

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * Add your docs here.
 */
public class HotMessReverse extends Command{

    public HotMessReverse() {
        requires(Robot.hotMess);
    }

    @Override
    protected void execute() {
        if(Timer.getFPGATimestamp() > 120) {
            Robot.hotMess.reverse();
        }
    }

    @Override
    protected void end() {
        Robot.hotMess.stop();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
