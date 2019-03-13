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

import static frc.robot.Robot.elevator;

public class GoToLevel extends Command{

    public int level;

    public GoToLevel(int level) {
        requires(elevator);
        this.level = level;
    }

    @Override
    protected void initialize() {
        elevator.enable();
        elevator.goToLevel(level);
    }

    @Override
    protected void execute() {
        
    }

    @Override
    protected boolean isFinished() {
        if(elevator.moveInput != 0 || elevator.onTarget()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void end() {
        elevator.disable();
    }

}
