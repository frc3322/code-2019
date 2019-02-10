/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;
import static frc.robot.Robot.elevator;

public class ElevatorControl extends Command {

    private static int level=1;
    
    private final int UP_AXIS;
    private final int DOWN_AXIS;

    /*
  	public ElevatorControl(boolean upOrDown) {

		requires(elevator);

		//level += (upOrDown) ? ((level == 3) ? 0 : 1) : ((level == 1) ? 0 : -1);

    }
     */

	public ElevatorControl(){
        requires(elevator);
        
        this.UP_AXIS = RobotMap.XBOX.TRIGGER_L_AXIS;
        this.DOWN_AXIS = RobotMap.XBOX.TRIGGER_R_AXIS;
	
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
        double moveInput = Robot.oi.upperChassis.getRawAxis(UP_AXIS) - Robot.oi.upperChassis.getRawAxis(DOWN_AXIS) * elevator.downSpeedModifier;
        
        elevator.move(moveInput);
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
