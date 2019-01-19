/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class HatchManip extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  DoubleSolenoid hatchPiston = new DoubleSolenoid(RobotMap.PCM.hatchShrink, RobotMap.PCM.hatchEnlarge);

    public void shrink(){
        hatchPiston.set(DoubleSolenoid.Value.kForward);
    }

    public void enlarge(){
        hatchPiston.set(DoubleSolenoid.Value.kReverse);
    }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    enlarge();
  }
}
