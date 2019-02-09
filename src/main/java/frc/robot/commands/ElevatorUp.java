/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;
import frc.robot.subsystems.Elevator;

/**
 * Add your docs here.
 */
public class ElevatorUp extends Command{

    public ElevatorUp() {
        requires(Robot.elevator);
    }

    @Override
    protected void execute() {
        Robot.elevator.moveUp();
    }
 
    @Override
    protected boolean isFinished() {
        return false;
    }
}
