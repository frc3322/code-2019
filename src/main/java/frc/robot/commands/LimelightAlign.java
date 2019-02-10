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
        
    }
    
    @Override
    protected void execute() {
        drivetrain.drive(Robot.oi.lowerChassis.getRawAxis(1) * .75, drivetrain.PIDOutput);
        if(hatchManip.hasHatch()) {
            hatchManip.hatchGrab();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
