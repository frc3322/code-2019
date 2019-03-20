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
import frc.robot.subsystems.HatchManip;

import static frc.robot.Robot.elevator;
import static frc.robot.Robot.hatchManip;

public class ElevatorControl extends Command {

    public int cycleCounter;
    public boolean hasSeenSwitch;
    public double idleSpeed;

	public ElevatorControl(){
        requires(elevator);
        hasSeenSwitch = false;
        cycleCounter = 0;
	
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {

        cycleCounter++;
        idleSpeed = 0.2;
        if(elevator.getLimitSwitch() && hasSeenSwitch == false){
            hasSeenSwitch = true;
            cycleCounter = 0;
            elevator.move(0);
        //needs 200 encoder ticks to get off switch
        } else if (elevator.moveInput == 0 && elevator.currentHeight() > 200 && !elevator.getPIDController().isEnabled()){
            elevator.move(idleSpeed);
        } else {
            elevator.move(elevator.moveInput);
        }

        //prevents limit switch bounce
        if(cycleCounter >= 10){
            if(!elevator.getLimitSwitch()){
                hasSeenSwitch = false;
            }
        }

        if(cycleCounter > 10000){
            cycleCounter = 1;
        }

        if(elevator.moveInput < 0) {
            elevator.reset();
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
