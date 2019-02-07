/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import static frc.robot.Robot.wideintake;

public class IntakeIdle extends Command {
    
    public IntakeIdle() {
        requires(wideintake);
    }

    @Override
    protected void execute() {
  
        wideintake.intakeStop();

    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}
