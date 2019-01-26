/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

import static frc.robot.Robot.hatchManip;

/**
 * Add your docs here.
 */
public class ExtendHatchManip extends Command{

    public ExtendHatchManip(){

        requires(hatchManip);

    }

    @Override
    protected void execute() {
        hatchManip.extendGrabber();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
