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

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import static frc.robot.Robot.drivetrain;

/**
 * Drive until the robot is the given angle away from the box. Uses a local
 * PID controller to run a simple PID loop that is only enabled while this
 * command is running. The input is the averaged values of the left and right
 * encoders.
 */
public class TurnToAngle extends Command {
	private PIDController m_pid;

    double calculatedP;
    double tolerance;
	public TurnToAngle(double angle) {
        requires(drivetrain);
        calculatedP = 0.265196 * Math.pow(0.796868, Math.abs(angle)) + 0.0341779;
        SmartDashboard.putNumber("input angle", angle);
        SmartDashboard.putNumber("Caclulated P", calculatedP);
		m_pid = new PIDController(calculatedP, 0, 0, new PIDSource() {
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
        if (angle >= 20){
            tolerance = 7;
        } else if(angle >= 15){
            tolerance = 5;
        } else if(angle < 15){
            tolerance = 3;
        }

		m_pid.setAbsoluteTolerance(tolerance);
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