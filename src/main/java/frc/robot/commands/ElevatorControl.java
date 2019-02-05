/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import static frc.robot.Robot.elevator;

public class ElevatorControl extends Command {

	private static int level=1;

  	public ElevatorControl(boolean upOrDown) {

		requires(elevator);

		level += (upOrDown) ? ((level == 3) ? 0 : 1) : ((level == 1) ? 0 : -1);

	}

	public ElevatorControl(){
	
		requires(elevator);
	
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		elevator.goToLevel(level);
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
