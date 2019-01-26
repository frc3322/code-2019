/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.SideOuttake;

import static frc.robot.Robot.sideouttake;

/**
 * Add your docs here.
 */
public class AutoOuttake extends Command {

    public AutoOuttake() {
        requires(Robot.sideouttake);
    }

    protected void execute() {
        // Robot.drivetrain.drive(.55, Robot.oi.lowerChassis.getRawAxis());
        if (sideouttake.getRightInfrared() || sideouttake.getLeftInfrared()) {
            //Robot.drivetrain.driveStop();
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
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}
