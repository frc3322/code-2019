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

    public AutoOuttake() {
        requires(Robot.sideouttake);
    }

    protected void execute() {
        Robot.drivetrain.driveClamped(.55 * RobotMap.XBOX.STICK_L_Y_AXIS, RobotMap.XBOX.STICK_R_X_AXIS);
        if (sideouttake.getRightInfrared() || sideouttake.getLeftInfrared()) {
            outtaking = true;
            while(outtaking) {
                Robot.drivetrain.stop();
            }
            if (sideouttake.getRightInfrared()) {
                sideouttake.outtakeRight();
            } else {
                sideouttake.outtakeLeft();
            }
            //Take notice that this exists: Timeout.seconds(2);
            try {
                Thread.sleep(SideOuttake.outtakeTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sideouttake.outtakeStop();
            outtaking = false;
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}
