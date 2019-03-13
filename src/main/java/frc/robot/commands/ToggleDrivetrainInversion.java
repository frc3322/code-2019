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
import static frc.robot.Robot.drivetrain;

public class ToggleDrivetrainInversion extends Command {
  public ToggleDrivetrainInversion() {
    requires(drivetrain);
  }

  @Override
  protected void initialize() {
      if (drivetrain.isHatchFront){
          drivetrain.isHatchFront = false;
      } else drivetrain.isHatchFront = true;
  }

  @Override
  protected boolean isFinished() {
    return true;
  }
}
