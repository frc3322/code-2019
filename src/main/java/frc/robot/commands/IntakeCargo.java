/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import static frc.robot.Robot.wideintake;
import static frc.robot.Robot.sideouttake;
import static frc.robot.Robot.elevator;

/**
 * Add your docs here.
 */
public class IntakeCargo extends Command {

    public IntakeCargo() {
        requires(wideintake);
        requires(sideouttake);
        requires(elevator);
    }

    @Override
    protected void execute() {
        if (!wideintake.hasCargo() && !sideouttake.hasCargo() && elevator.atLevel0()) {
            wideintake.intakeStart();
            sideouttake.intakeCarriage();
        } else if (wideintake.hasCargo() && !elevator.atLevel0()) {
            wideintake.intakeStop();
            elevator.goToLevel(0);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sideouttake.intakeCarriage();
            wideintake.intakeStart();
        }
    }

    @Override
    protected boolean isFinished() {
        return sideouttake.hasCargo();
    }

    @Override
    protected void end() {
        wideintake.intakeStop();
        sideouttake.outtakeStop();
    }

}
