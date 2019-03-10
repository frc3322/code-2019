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

public class ElevatorControl extends Command {

    public int cycleCounter;
    public boolean hasSeenSwitch;
    public double idleSpeed;

    /*
  	public ElevatorControl(boolean upOrDown) {

		requires(elevator);

		//level += (upOrDown) ? ((level == 3) ? 0 : 1) : ((level == 1) ? 0 : -1);

    }
     */

	public ElevatorControl(){
        requires(elevator);
        hasSeenSwitch = false;
        cycleCounter = 0;
	
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {

        /*
        if(elevator.canMoveUp == false) {
            moveInput = Math.min(0, moveInput);
        }
        if(elevator.canMoveDown == false){
            moveInput = Math.max(0, moveInput);
        }
        if(moveInput > 0){
            elevator.canMoveDown = true;
        }
        if(moveInput < 0){
            elevator.canMoveUp = true;
        }
        */
        cycleCounter++;
        idleSpeed = 0.15;
        if(elevator.getLimitSwitch() && hasSeenSwitch == false){
            hasSeenSwitch = true;
            cycleCounter = 0;
            elevator.move(0);
        } else if (elevator.moveInput == 0){
            elevator.move(idleSpeed);
        } else {
            elevator.move(elevator.moveInput);
        }

        //prevents limit switch bounce
        if(cycleCounter >= 5){
            if(!elevator.getLimitSwitch()){
                hasSeenSwitch = false;
            }
        }

        if(cycleCounter > 10000){
            cycleCounter = 1;
        }

	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		elevator.stop();
	}

	@Override
	protected void interrupted() {
		super.interrupted();
	}
}
