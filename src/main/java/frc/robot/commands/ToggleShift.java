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
import static frc.robot.Robot.drivetrain;

/**
 * Add your docs here.
 */
public class ToggleShift extends Command{

    public ToggleShift() {
        requires(drivetrain);
    }

    @Override
    protected void initialize() {
        drivetrain.toggleShift();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
