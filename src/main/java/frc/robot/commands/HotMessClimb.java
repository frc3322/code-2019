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
    private final int LEFT_AXIS;
    double speed;

    // double stopVal = 100;

    public HotMessClimb(){
        requires(hotMess);
        RIGHT_AXIS = RobotMap.XBOX.STICK_R_Y_AXIS;
        LEFT_AXIS = RobotMap.XBOX.STICK_L_Y_AXIS;      
    }

    @Override
    protected void execute() {
        if(Math.abs(oi.upperChassis.getRawAxis(RIGHT_AXIS)) > .1 && Math.abs(oi.upperChassis.getRawAxis(LEFT_AXIS)) > .1){
            hotMess.hotMessUp();
            speed = ((oi.upperChassis.getRawAxis(RIGHT_AXIS)+oi.upperChassis.getRawAxis(LEFT_AXIS))/2);
        } else {
            hotMess.hotMessDown();
            speed = 0;
        }

        hotMess.climb(speed);
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