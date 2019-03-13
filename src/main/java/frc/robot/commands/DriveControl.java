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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;

import static frc.robot.Robot.drivetrain;
import static frc.robot.Robot.oi;

public class DriveControl extends Command {

    private final int SPEED_AXIS;
    private final int ROTATION_AXIS;

    private double rotationModifier= 0;


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
    
        if(drivetrain.limeControlling) {
            SmartDashboard.putBoolean("Limelight Controlling", true);
            drivetrain.limeDrive(speed); 
        } else {
            SmartDashboard.putBoolean("Limelight Controlling", false);
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
