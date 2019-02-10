/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import static frc.robot.Robot.wideintake;

/**
 * Add your docs here.
 */
public class ToggleIntake extends Command{

    public ToggleIntake(){

        requires(wideintake);

    }

    @Override
    protected void initialize() {
        wideintake.toggleIntake();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
