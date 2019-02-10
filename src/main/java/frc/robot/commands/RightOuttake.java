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

/**
 * Add your docs here.
 */
public class RightOuttake extends Command {

    protected void execute() {
        Robot.sideouttake.outtakeRight(.75);
        try {
            Thread.sleep(SideOuttake.outtakeTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Robot.sideouttake.outtakeStop();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
