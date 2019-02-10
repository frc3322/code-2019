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
import frc.robot.subsystems.SideOuttake;
import java.util.Timer;
import java.util.TimerTask;

import static frc.robot.Robot.sideouttake;
import static frc.robot.Robot.drivetrain;
import static frc.robot.Robot.oi;

public class AutoOuttake extends Command {

    public boolean outtaking;

    // long millisecondsToRun = 1000; // This should run 1000ms = 1 s.
    // long initTime = 

    public AutoOuttake() {
        requires(sideouttake);
    }

    protected void execute() {
        drivetrain.driveStraight(oi.lowerChassis.getRawAxis(RobotMap.XBOX.STICK_L_Y_AXIS) * .55, oi.lowerChassis.getRawAxis(RobotMap.XBOX.STICK_R_X_AXIS));
        if (sideouttake.getRightInfrared() || sideouttake.getLeftInfrared()) {
            outtaking = true;
            while(outtaking) {
                drivetrain.stop();
            }
            if (sideouttake.getRightInfrared()) {
                sideouttake.outtakeRight(0.75);
            } else {
                sideouttake.outtakeLeft(0.75);
            }
            //Timer.delay(2)?
            new Timer().schedule( 
                new TimerTask() {
                    @Override
                    public void run() {
                        sideouttake.outtakeStop();
                        outtaking = false;
                    }
                }, 
                2000 
            );
            
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}
