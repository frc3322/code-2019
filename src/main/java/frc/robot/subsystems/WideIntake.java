/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/**
 * Add your docs here.
 */
public class WideIntake extends Subsystem{

    WPI_TalonSRX intakeMotor = new WPI_TalonSRX(RobotMap.CAN.INTAKE_MOTOR);
    DoubleSolenoid intakeExtender = new DoubleSolenoid(RobotMap.PCM.INTAKE_EXTEND, RobotMap.PCM.INTAKE_RETRACT);

    public WideIntake() {

    }

    public void intakeStart() {
        intakeMotor.set(1);
    }

    public void intakeStop() {
        intakeMotor.set(0);
    }

    public void intakeExtend() {
        intakeExtender.set(DoubleSolenoid.Value.kForward);
    }

    public void intakeRetract() {
        intakeExtender.set(DoubleSolenoid.Value.kReverse);
    }

    public void toggleIntake() {
        if(isIntakeExtended()) {
            intakeRetract();
        }else{
            intakeExtend();
        }
    }

    public boolean isIntakeExtended() {
        return intakeExtender.get() == Value.kForward;
    }

    @Override
    protected void initDefaultCommand() {

    }
}
