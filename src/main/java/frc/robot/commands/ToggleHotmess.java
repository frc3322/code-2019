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
import static frc.robot.Robot.hotMess;

public class ToggleHotmess extends Command{

    public ToggleHotmess() {
        
    }

    @Override
    protected void initialize() {
        hotMess.toggleHotMess();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
