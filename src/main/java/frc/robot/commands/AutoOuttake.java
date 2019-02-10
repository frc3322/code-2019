/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.subsystems.SideOuttake;

import static frc.robot.Robot.sideouttake;

/**
 * Add your docs here.
 */
public class AutoOuttake extends Command {

    public boolean outtaking;

    // long millisecondsToRun = 1000; // This should run 1000ms = 1 s.
    // long initTime = 

    public AutoOuttake() {
        requires(Robot.sideouttake);
    }

    protected void execute() {
        Robot.drivetrain.driveStraight(Robot.oi.lowerChassis.getRawAxis(RobotMap.XBOX.STICK_L_Y_AXIS) * .55, RobotMap.XBOX.STICK_R_X_AXIS);
        if (sideouttake.getRightInfrared() || sideouttake.getLeftInfrared()) {
            outtaking = true;
            while(outtaking) {
                Robot.drivetrain.stop();
            }
            if (sideouttake.getRightInfrared()) {
                sideouttake.outtakeRight(0.75);
            } else {
                sideouttake.outtakeLeft(0.75);
            }
            //Timer.delay(2)?
            new java.util.Timer().schedule( 
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        sideouttake.outtakeStop();
                        outtaking = false;
                    }
                }, 
                2000 
            );
            
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}
