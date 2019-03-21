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
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.GenericHID;

import static frc.robot.Robot.sideouttake;
import static frc.robot.Robot.oi;

public class OuttakeControl extends Command{

    private final int RIGHT_AXIS;
    private final int LEFT_AXIS;

    public boolean outtaking;
    public double lastOuttake;

    public OuttakeControl() {
        requires(sideouttake);
        RIGHT_AXIS = RobotMap.XBOX.TRIGGER_R_AXIS;
        LEFT_AXIS = RobotMap.XBOX.TRIGGER_L_AXIS;
    }

    @Override
    protected void execute() {
        if(oi.lowerChassis.getRawAxis(RIGHT_AXIS) > 0){
            sideouttake.outtakeRight(Math.sqrt(oi.lowerChassis.getRawAxis(RIGHT_AXIS)));
        } else if(oi.lowerChassis.getRawAxis(LEFT_AXIS) > 0){
            sideouttake.outtakeLeft(Math.sqrt(oi.lowerChassis.getRawAxis(LEFT_AXIS)));
        } else {
            sideouttake.outtakeStop();
        }

        if (sideouttake.getRightInfrared()) {
            outtaking = true;
            oi.lowerChassis.setRumble(GenericHID.RumbleType.kRightRumble, 1);
            lastOuttake = System.currentTimeMillis();
            if ((System.currentTimeMillis() - lastOuttake) >= 500) {
                outtaking = false;
                oi.lowerChassis.setRumble(GenericHID.RumbleType.kRightRumble, 0);
            }
        } else if (sideouttake.getLeftInfrared()) {
            outtaking = true;
            oi.lowerChassis.setRumble(GenericHID.RumbleType.kLeftRumble, 1);
            lastOuttake = System.currentTimeMillis();
            if ((System.currentTimeMillis() - lastOuttake) >= 500) {
                outtaking = false;
                oi.lowerChassis.setRumble(GenericHID.RumbleType.kLeftRumble, 0);
            }
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        sideouttake.outtakeStop();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
