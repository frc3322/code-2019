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

import static frc.robot.Robot.hotMess;

public class HotMessIdle extends Command {

    public HotMessIdle() {
        requires(hotMess);
    }

    @Override
    protected void execute() {
        hotMess.stop();
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
