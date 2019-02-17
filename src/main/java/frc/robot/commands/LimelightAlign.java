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
        //drivetrain.getPIDController().enable();
        //drivetrain.setSetpoint(drivetrain.getAngle() + 270);

        
    }
    
    @Override
    protected void execute() {
        
    }

    @Override
    protected boolean isFinished() {
        return false;//drivetrain.onTarget();
    }

    @Override
    protected void end() {
        //drivetrain.getPIDController().disable();
        //drivetrain.stop();
    }
}
