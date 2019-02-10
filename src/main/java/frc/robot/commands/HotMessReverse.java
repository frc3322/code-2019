/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * Add your docs here.
 */
public class HotMessReverse extends Command{

    public HotMessReverse() {
        requires(Robot.hotMess);
    }

    @Override
    protected void execute() {
        if(Timer.getFPGATimestamp() > 120) {
            Robot.hotMess.reverse();
        }
    }

    @Override
    protected void end() {
        Robot.hotMess.stop();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
