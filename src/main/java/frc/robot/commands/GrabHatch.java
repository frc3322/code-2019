/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.HatchManip;

import static frc.robot.Robot.hatchManip;

/**
 * Add your docs here.
 */
public class GrabHatch extends Command{

    public void GrabHatch() {
        requires(hatchManip);
    }

    @Override
    protected void execute() {
        hatchManip.grabHatch();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}