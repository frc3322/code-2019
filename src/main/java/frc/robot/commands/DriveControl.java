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

import static frc.robot.Robot.drivetrain;
import static frc.robot.Robot.oi;

/**
 * An example command.  You can replace me with your own command.
 */
public class DriveControl extends Command {

    private final int SPEED_AXIS;
    private final int ROTATION_AXIS;

    private double rotationModifier= 0;

    private double maxSpeed = 1;


    public DriveControl() {

        requires(drivetrain);

        SPEED_AXIS = RobotMap.XBOX.STICK_L_Y_AXIS;
        ROTATION_AXIS = RobotMap.XBOX.STICK_R_X_AXIS;

    }

    @Override
    protected void execute() {
        double speed = oi.lowerChassis.getRawAxis(SPEED_AXIS);

        if(speed > 0 || speed < 0) {
            rotationModifier = 0.775;
        } else {
            rotationModifier = 0.6;
        }

        double turn = -oi.lowerChassis.getRawAxis(ROTATION_AXIS) * rotationModifier;

        if(speed == 0) {
            drivetrain.shiftLow();
        }

        if(drivetrain.isHighGear()) {
            speed = speed * .85;
        }
        
        // speed = (Math.abs(speed) > deadZone) ? speed * Math.abs(Math.pow(speed, speedPow - 1)) : 0;
        // turn = (Math.abs(turn) > deadZone) ? turn * Math.abs(Math.pow(turn, turnPow - 1)) : 0;

        /*
        if(Math.abs(speed) > maxSpeed) {
            if(speed > 0) {
                speed = maxSpeed;
            } else if(speed < 0){
                speed = -maxSpeed;
            }
        }
        */
        if(drivetrain.limeControlling) {
            drivetrain.limeDrive(speed); 
        } else if(drivetrain.outtakeControlling) {
            drivetrain.driveStraight(speed * .4, turn * .4);
        } else {
            drivetrain.driveStraight(speed, turn);
        }
    }
        

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        drivetrain.stop();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
