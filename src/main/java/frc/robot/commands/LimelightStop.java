/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import static frc.robot.Robot.drivetrain;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Limelight.CameraMode;
import frc.robot.subsystems.Limelight.LightMode;

/**
 * Add your docs here.
 */
public class LimelightStop extends Command{
    @Override
    protected void initialize() {
        Limelight.setLedMode(LightMode.eOff);
        Limelight.setCameraMode(CameraMode.eDriver);
        drivetrain.limeControlling = false;
        drivetrain.limelightPID.disable();
    }
    
    @Override
    protected boolean isFinished() {
        return true;
    }

}
