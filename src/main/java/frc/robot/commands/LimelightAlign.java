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

import frc.robot.commands.DriveControl;
import static frc.robot.Robot.drivetrain;
import static frc.robot.Robot.limelight;

/**
 * Add your docs here.
 */
public class LimelightAlign extends Command {

    double angleModifier = .05;

    public LimelightAlign() {
        requires(drivetrain);
        requires(limelight);
    }

    @Override
    protected void initialize(){
        drivetrain.limelightPID.reset();
        drivetrain.limeControlling = true;
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}