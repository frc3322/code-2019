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
import static frc.robot.Robot.wideintake;
import static frc.robot.Robot.sideouttake;
import static frc.robot.Robot.elevator;

/**
 * Add your docs here.
 */
public class IntakeCargo extends Command {

    public IntakeCargo() {
        requires(wideintake);
        requires(sideouttake);
        requires(elevator);
    }

    @Override
    protected void execute() {
        if(!elevator.atLevel0()) {
            elevator.goToLevel(0);
        }
        wideintake.intakeStart();
        sideouttake.intakeCarriage();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}
