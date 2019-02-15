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
import frc.robot.Robot;
import frc.robot.subsystems.Limelight;

import static frc.robot.Robot.drivetrain;
import static frc.robot.Robot.limelight;
import static frc.robot.Robot.hatchManip;

/**
 * Add your docs here.
 */
public class LimelightAlign extends Command {

    double angleModifier = .05;

    public LimelightAlign() {
        requires(drivetrain);
        requires(limelight);
        requires(hatchManip);
    }

    @Override
    protected void initialize(){
        drivetrain.setSetpoint(drivetrain.getAngle() + 90);
    }
    
    @Override
    protected void execute() {
        //drivetrain.setSetpoint(drivetrain.getAngleToTarget());
        //drivetrain.drive(Robot.oi.lowerChassis.getRawAxis(1), drivetrain.PIDOutput);
    }
    @Override
    protected boolean isFinished() {
        return true;
    }
}
