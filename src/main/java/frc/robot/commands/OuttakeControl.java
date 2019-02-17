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
import frc.robot.RobotMap;

import static frc.robot.Robot.sideouttake;
import static frc.robot.Robot.oi;

/**
 * Add your docs here.
 */
public class OuttakeControl extends Command{

    private final int RIGHT_AXIS;
    private final int LEFT_AXIS;

    public OuttakeControl() {
        requires(sideouttake);
        RIGHT_AXIS = RobotMap.XBOX.TRIGGER_R_AXIS;
        LEFT_AXIS = RobotMap.XBOX.TRIGGER_L_AXIS;
    }

    @Override
    protected void execute() {
        if(oi.upperChassis.getRawAxis(RIGHT_AXIS) > 0){
            sideouttake.outtakeRight(Math.sqrt(oi.lowerChassis.getRawAxis(RIGHT_AXIS)));
        } else if(oi.upperChassis.getRawAxis(LEFT_AXIS) > 0){
            sideouttake.outtakeLeft(Math.sqrt(oi.lowerChassis.getRawAxis(LEFT_AXIS)));
        } else {
            sideouttake.outtakeStop();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        sideouttake.outtakeStop();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
