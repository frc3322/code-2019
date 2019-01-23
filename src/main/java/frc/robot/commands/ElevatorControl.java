/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.RobotMap;

import frc.robot.subsystems.Elevator;
import frc.robot.OI;
import frc.robot.Robot;

public class ElevatorControl extends Command {

    private final int UP_AXIS;
    private final int DOWN_AXIS;

    private Elevator elevator;

  public ElevatorControl() {
      this.elevator = new Elevator(); //call with up and down speeds if needed

      this.UP_AXIS = RobotMap.XBOX.TRIGGER_L_AXIS;
      this.DOWN_AXIS = RobotMap.XBOX.TRIGGER_R_AXIS;


  }

  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double moveInput = Robot.m_oi.upperChassis.getRawAxis(UP_AXIS) - Robot.m_oi.upperChassis.getRawAxis(DOWN_AXIS);
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
