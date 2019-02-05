/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * Add your docs here.
 */
public class IntakeCargo extends Command {

    public IntakeCargo() {
        requires(Robot.wideintake);
        requires(Robot.sideouttake);
    }

    @Override
    protected void execute() {
        if (!Robot.wideintake.hasCargo() && !Robot.sideouttake.hasCargo() && Robot.elevator.atLevel0()) {
            Robot.wideintake.intakeStart();
            Robot.sideouttake.intakeCarriage();
        } else if (Robot.wideintake.hasCargo() && !Robot.elevator.atLevel0()) {
            Robot.wideintake.intakeStop();
            Robot.elevator.goToLevel(0);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Robot.sideouttake.intakeCarriage();
            Robot.wideintake.intakeStart();
        }
    }

    @Override
    protected boolean isFinished() {
        return Robot.sideouttake.hasCargo();
    }

    @Override
    protected void end() {
        Robot.wideintake.intakeStop();
        Robot.sideouttake.outtakeStop();
    }

}
