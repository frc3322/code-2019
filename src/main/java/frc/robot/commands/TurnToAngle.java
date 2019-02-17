/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;

import static frc.robot.Robot.drivetrain;;

/**
 * Drive until the robot is the given angle away from the box. Uses a local
 * PID controller to run a simple PID loop that is only enabled while this
 * command is running. The input is the averaged values of the left and right
 * encoders.
 */
public class TurnToAngle extends Command {
	private PIDController m_pid;

	public TurnToAngle(double angle) {
		requires(drivetrain);
		m_pid = new PIDController(0.025, 0, 0, new PIDSource() {
			PIDSourceType m_sourceType = PIDSourceType.kDisplacement;

			@Override
			public double pidGet() {
				return drivetrain.navx.getYaw();
			}

			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
				m_sourceType = pidSource;
			}

			@Override
			public PIDSourceType getPIDSourceType() {
				return m_sourceType;
			}
		}, d -> drivetrain.tankDrive(-d/2, d/2));

		m_pid.setAbsoluteTolerance(5.0);
		m_pid.setInputRange(-180.0f,  180.0f);
		m_pid.setOutputRange(-1.0, 1.0);
		m_pid.setContinuous(true);
		m_pid.setSetpoint(angle);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		// Get everything in a safe starting state.
        drivetrain.navx.reset();
		m_pid.reset();
		m_pid.enable();
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return m_pid.onTarget();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		// Stop PID and the wheels
		m_pid.disable();
		drivetrain.stop();

	}
}