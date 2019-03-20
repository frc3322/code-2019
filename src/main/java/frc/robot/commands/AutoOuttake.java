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
import static frc.robot.Robot.sideouttake;

import static frc.robot.Robot.oi;

public class AutoOuttake extends Command {

    private boolean outtaking;

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
            while (outtaking) {
                if ((System.currentTimeMillis() - lastOuttake) >= 500) {
                    sideouttake.outtakeStop();
                    outtaking = false;
                    oi.lowerChassis.setRumble(GenericHID.RumbleType.kRightRumble, 0);
                }
            }
        } else if (sideouttake.getLeftInfrared()) {
            outtaking = true;
            oi.lowerChassis.setRumble(GenericHID.RumbleType.kLeftRumble, 1);
            lastOuttake = System.currentTimeMillis();
            sideouttake.outtakeLeft(0.75);
            while (outtaking) {
                if ((System.currentTimeMillis() - lastOuttake) >= 500) {
                    sideouttake.outtakeStop();
                    outtaking = false;
                    oi.lowerChassis.setRumble(GenericHID.RumbleType.kLeftRumble, 0);
                }
            }
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}
