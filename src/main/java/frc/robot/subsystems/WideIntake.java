package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
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
    DoubleSolenoid intakeExtender = new DoubleSolenoid(RobotMap.PCM.PCM_ID, RobotMap.PCM.INTAKE_EXTEND, RobotMap.PCM.INTAKE_RETRACT);
    DigitalInput cargoDetector = new DigitalInput(RobotMap.DIO.CARGO_DETECTOR_INTAKE);
    public WideIntake() {
    }

    public void intakeStart() {
        intakeMotor.set(-.6);
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

    public boolean hasCargo() {
        return cargoDetector.get();
    }

    @Override
    protected void initDefaultCommand() {

    }
}
