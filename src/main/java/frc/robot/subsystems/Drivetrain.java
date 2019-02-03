/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.DriveControl;

/**
 * Code for drive train
 */
public class Drivetrain extends Subsystem {
  
    private DifferentialDrive robotDrive;

    private CANSparkMax leftBackMotor,
                         leftFrontMotor,
                         rightBackMotor,
                         rightFrontMotor;

    private CANEncoder leftBackEncoder,
                        leftFrontEncoder,
                        rightBackEncoder,
                        rightFrontEncoder;

    private DoubleSolenoid shiftSolenoid;

    double previousThrottle = 0,
            previousTurn = 0,
            maxTurnDelta = .05,
            maxThrottleDelta = .05;

    public int highThreshold, lowThreshold;

    private CANSparkMax[] motors = {leftBackMotor, rightBackMotor, leftFrontMotor, rightFrontMotor};

    private CANEncoder[] encoders = {leftBackEncoder, rightBackEncoder, leftFrontEncoder, rightFrontEncoder};

    private boolean straightModeStart, straightModeRun;
    private double runDelay;

    public Drivetrain() {

        leftBackMotor = new CANSparkMax(RobotMap.CAN.LEFT_BACK_MOTOR, MotorType.kBrushless);
        leftFrontMotor = new CANSparkMax(RobotMap.CAN.LEFT_FRONT_MOTOR, MotorType.kBrushless);
        rightBackMotor = new CANSparkMax(RobotMap.CAN.RIGHT_BACK_MOTOR, MotorType.kBrushless);
        rightFrontMotor = new CANSparkMax(RobotMap.CAN.RIGHT_FRONT_MOTOR, MotorType.kBrushless);

        leftBackEncoder = leftBackMotor.getEncoder();
        leftFrontEncoder = leftFrontMotor.getEncoder();
        rightBackEncoder = rightBackMotor.getEncoder();
        rightFrontEncoder = rightFrontMotor.getEncoder();

        shiftSolenoid = new DoubleSolenoid(RobotMap.PCM.PCM_ID, RobotMap.PCM.SHIFT_GEAR_1, RobotMap.PCM.SHIFT_GEAR_2);

        double leftEncoders = (leftBackEncoder.getPosition() + leftFrontEncoder.getPosition())/2;
        double rightEncoders = (rightBackEncoder.getPosition() + rightFrontEncoder.getPosition())/2;

        SpeedControllerGroup leftGroup = new SpeedControllerGroup(leftBackMotor, leftFrontMotor);
        SpeedControllerGroup rightGroup = new SpeedControllerGroup(rightBackMotor, rightFrontMotor);

        robotDrive = new DifferentialDrive(leftFrontMotor, rightFrontMotor);

        leftBackMotor.follow(leftFrontMotor);
        rightBackMotor.follow(rightFrontMotor);

        leftFrontMotor.setRampRate(.5);
        rightFrontMotor.setRampRate(.5);

        straightModeStart = false;
        straightModeRun = false;

    }

    public double getVoltage(int n) {
        return motors[n].getBusVoltage();
    }

    public double getMotorHeat(int n) {
        return motors[n].getMotorTemperature();
    }

    public double getOutputCurrent(int n) {
        return motors[n].getOutputCurrent();
    }

    public double getEncoder(int n) {
        return encoders[n].getPosition();
    }

    public double getVelocity(int n) {
        return encoders[n].getVelocity();
    }

    public void drive(double speed, double rotation){

        robotDrive.arcadeDrive(speed, rotation);

    }

    public void driveClamped(double throttle, double turn) {
        double deltaThrottle = throttle - previousThrottle;
        double deltaTurn = turn - previousTurn;

        // Limit change in throttle value
        // if current change in throttle value exceeds max, clamp it
        if (Math.abs(deltaThrottle) > maxThrottleDelta && (previousThrottle / deltaThrottle) > 0) {
            throttle = previousThrottle + ((deltaThrottle < 0)? -maxThrottleDelta : maxThrottleDelta);
        }

        // Limit change in turn value
        // if current change in turn value exceeds max, clamp it
        if(Math.abs(deltaTurn) > maxTurnDelta && (previousTurn / deltaTurn) > 0){
            turn = previousTurn + ((deltaTurn < 0)? -maxTurnDelta : maxTurnDelta);
        }

        drive(throttle, turn);

        previousThrottle = throttle;
        previousTurn = turn;
    }

    public void driveStraight(double speed, double rotation){

        if(Math.abs(speed) > 0.15 && Math.abs(rotation) < 0.15){
            if (!straightModeStart) {
                straightModeStart = true;

                runDelay = System.currentTimeMillis();
            }

            // Wait a bit before setting our desired angle
            if (System.currentTimeMillis() - runDelay > 250 && !straightModeRun) {
                //initialize pid code here
                straightModeRun = true;
            }

            if (straightModeRun) {
                //pid command for driving straight
                drive(speed, 0);
            } else {
                drive(speed, rotation);
            }


        }else{

            straightModeStart = false;
            straightModeRun = false;

            drive(speed, rotation);

        }

    }

    public void stop(){
        drive(0, 0);
    }

    public void toggleShift() {
        if(isHighGear()) {
            shiftLow();
        } else {
            shiftHigh();
        }
    }

    public void shiftHigh() {
        shiftSolenoid.set(Value.kForward);
    }

    public void shiftLow() {
        shiftSolenoid.set(Value.kReverse);
    }

    public double wheelRPM(CANEncoder e) {
        if(isHighGear()) {
            return leftFrontEncoder.getVelocity() / 5.1;
        } else {
            return leftFrontEncoder.getVelocity() / 11.03;
        }
    }

    public boolean isHighGear() {
        return shiftSolenoid.get() == Value.kForward;
    }

    public boolean isLowGear() {
        return shiftSolenoid.get() == Value.kReverse;
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new DriveControl());
    }

    public void autoShift() {
        if (wheelRPM(leftFrontEncoder) > highThreshold && wheelRPM(rightFrontEncoder) > highThreshold) {
            if (!isHighGear()) {
                shiftHigh();
            }
        } else if (wheelRPM(leftFrontEncoder) < lowThreshold && wheelRPM(rightFrontEncoder) < lowThreshold) {
            if (isHighGear()) {
                shiftLow();
            }
        }
        
    }

}