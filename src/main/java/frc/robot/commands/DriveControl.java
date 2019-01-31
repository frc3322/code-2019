/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

import static frc.robot.Robot.drivetrain;

/**
 * An example command.  You can replace me with your own command.
 */
public class DriveControl extends Command {

    private final int LEFT_AXIS;
    private final int ROTATION_AXIS;

    private int pow = 1;
    private double deadZone = 0.1;

    public DriveControl() {

        requires(drivetrain);

        LEFT_AXIS = RobotMap.XBOX.STICK_L_Y_AXIS;
        ROTATION_AXIS = RobotMap.XBOX.STICK_R_X_AXIS;

    }

    @Override
    protected void execute() {

        double leftSpeed = Robot.oi.lowerChassis.getRawAxis(LEFT_AXIS);
        double rightSpeed = Robot.oi.lowerChassis.getRawAxis(ROTATION_AXIS);
        
        leftSpeed = (Math.abs(leftSpeed) > deadZone) ? leftSpeed * Math.abs(Math.pow(leftSpeed, pow - 1)) : 0;
        rightSpeed = (Math.abs(rightSpeed) > deadZone) ? rightSpeed * Math.abs(Math.pow(rightSpeed, pow - 1)) : 0;

        drivetrain.driveClamped(leftSpeed, rightSpeed);
        
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
