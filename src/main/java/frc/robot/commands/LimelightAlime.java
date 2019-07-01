/**
 *  _____    _____     _____     _____   
 * |___  \  |___  \   /  _  \   /  _  \
 *  ___|  |  ___|  | |__| |  | |__| |  |
 * |___   | |___   |     /  /      /  /
 *  ___|  |  ___|  |   /  /__    /  /__
 * |_____/  |_____/   |______|  |______|
 *
 */

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Limelight.CameraMode;
import frc.robot.subsystems.Limelight.LightMode;

import static frc.robot.Robot.drivetrain;
import static frc.robot.Robot.limelight;

public class LimelightAlime extends Command {

    double angleModifier = .05;

    public LimelightAlime() {
        requires(drivetrain);
        requires(limelight);
    }

    @Override
    protected void initialize(){
        Limelight.setLedMode(LightMode.eOn);
        Limelight.setCameraMode(CameraMode.eVision);
        drivetrain.limelightPID.reset();
        drivetrain.limeControlling = true;
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
