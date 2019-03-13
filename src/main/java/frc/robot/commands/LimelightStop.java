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

import static frc.robot.Robot.drivetrain;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Limelight.CameraMode;
import frc.robot.subsystems.Limelight.LightMode;

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
