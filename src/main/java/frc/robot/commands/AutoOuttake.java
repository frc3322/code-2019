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

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static frc.robot.Robot.sideouttake;

import static frc.robot.Robot.oi;

public class AutoOuttake extends Command {

    private boolean outtaking = false;

    private double lastOuttake;

    public AutoOuttake() {
        requires(sideouttake);
    }

    protected void execute() {
        if (sideouttake.getRightInfrared()) {
            outtaking = true;
            oi.lowerChassis.setRumble(GenericHID.RumbleType.kRightRumble, 1);
            lastOuttake = System.currentTimeMillis();
            sideouttake.outtakeRight(0.75);
            SmartDashboard.putBoolean("Outtaking Right", true);
        } else if (sideouttake.getLeftInfrared()) {
            outtaking = true;
            oi.lowerChassis.setRumble(GenericHID.RumbleType.kLeftRumble, 1);
            lastOuttake = System.currentTimeMillis();
            sideouttake.outtakeLeft(0.75);
            SmartDashboard.putBoolean("Outtaking Left", true);
        }

        while (outtaking) {
            if ((System.currentTimeMillis() - lastOuttake) >= 250) {
                oi.lowerChassis.setRumble(GenericHID.RumbleType.kRightRumble, 0);
                oi.lowerChassis.setRumble(GenericHID.RumbleType.kLeftRumble, 0);
            }
            if ((System.currentTimeMillis() - lastOuttake) >= 500) {
                sideouttake.outtakeStop();
                SmartDashboard.putBoolean("Outtaking Right", false);
                SmartDashboard.putBoolean("Outtaking Left", false);
                outtaking = false;
            }
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
