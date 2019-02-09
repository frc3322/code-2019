/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import static frc.robot.Robot.hatchManip;

/**
 * Add your docs here.
 */
public class GrabHatch extends Command{

    public GrabHatch() {
        requires(hatchManip);
    }

    @Override
    protected void initialize() {
        hatchManip.toggleHatch();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}