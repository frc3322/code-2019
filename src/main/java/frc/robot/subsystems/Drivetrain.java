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
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
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

    double previousThrottle = 0,
            previousTurn = 0,
            maxTurnDelta = .05,
            maxThrottleDelta = .05;

    public Drivetrain() {

        leftBackMotor = new CANSparkMax(RobotMap.CAN.LEFT_BACK_MOTOR, MotorType.kBrushless);
        leftFrontMotor = new CANSparkMax(RobotMap.CAN.LEFT_FRONT_MOTOR, MotorType.kBrushless);
        rightBackMotor = new CANSparkMax(RobotMap.CAN.RIGHT_BACK_MOTOR, MotorType.kBrushless);
        rightFrontMotor = new CANSparkMax(RobotMap.CAN.RIGHT_FRONT_MOTOR, MotorType.kBrushless);

        leftBackEncoder = leftBackMotor.getEncoder();
        leftFrontEncoder = leftFrontMotor.getEncoder();
        rightBackEncoder = rightBackMotor.getEncoder();
        rightFrontEncoder = rightFrontMotor.getEncoder();

        double leftEncoders = (leftBackEncoder.getPosition() + leftFrontEncoder.getPosition())/2;
        double rightEncoders = (rightBackEncoder.getPosition() + rightFrontEncoder.getPosition())/2;

        SpeedControllerGroup leftGroup = new SpeedControllerGroup(leftBackMotor, leftFrontMotor);
        SpeedControllerGroup rightGroup = new SpeedControllerGroup(rightBackMotor, rightFrontMotor);

        robotDrive = new DifferentialDrive(leftGroup, rightGroup);

    }

    public double getLeftBackEncoder() {
        return leftBackEncoder.getPosition();
    }

    public double getLeftFrontEncoder() {
        return leftFrontEncoder.getPosition();
    }

    public double getRightBackEncoder() {
        return rightBackEncoder.getPosition();
    }

    public double getRightFrontEncoder() {
        return rightFrontEncoder.getPosition();
    }

    public void drive(double leftSpeed, double rightSpeed){

        robotDrive.tankDrive(leftSpeed, rightSpeed);

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

    public void stop(){
        drive(0, 0);
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new DriveControl());
    }

}