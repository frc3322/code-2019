/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import static frc.robot.Robot.elevator;

/**
 * Add your docs here.
 */
public class GoToLevel extends Command{

    public int level;

    public GoToLevel(int level) {
        requires(elevator);
        this.level = level;
    }

    @Override
    protected void execute() {
        elevator.goToLevel(level);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
