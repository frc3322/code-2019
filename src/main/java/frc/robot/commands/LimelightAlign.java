/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.subsystems.Limelight;
import frc.robot.OI;
import frc.robot.subsystems.DriveTrain;

import static frc.robot.Robot.driveTrain;
import static frc.robot.Robot.limelight;

/**
 * Add your docs here.
 */
public class LimelightAlign extends Command {

    double angleModifier = .1;

    public void LimelightAlign() {
        requires(driveTrain);
        requires(limelight);
    }

    @Override
    protected void execute() {
        driveTrain.drive(Robot.m_oi.lowerChassis.getRawAxis(1), Limelight.getTx() * angleModifier);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
