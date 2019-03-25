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

import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Command;
import static frc.robot.Robot.oi;
import static frc.robot.Robot.hotMess;



public class HotMessClimb extends Command{

    private final int RIGHT_AXIS;

    // double stopVal = 100;

    public HotMessClimb(){
        requires(hotMess);
        RIGHT_AXIS = RobotMap.XBOX.TRIGGER_R_AXIS;
    }

    @Override
    protected void execute() {
        hotMess.climb(-oi.upperChassis.getRawAxis(RIGHT_AXIS));
        //SmartDashboard.putNumber("HotMess Speed", RobotMap.XBOX)
    }

    @Override
    protected boolean isFinished() {
        return false; //hotMess.getEncoderVal() <= stopVal;
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