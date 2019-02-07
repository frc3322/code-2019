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
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.SPI;

import com.kauailabs.navx.frc.AHRS;

import frc.robot.RobotMap;
import frc.robot.commands.DriveControl;


/**
 * Code for drive train
 */
public class Drivetrain extends PIDSubsystem {
  
    private DifferentialDrive robotDrive;

    private DoubleSolenoid shiftSolenoid;

    private final int LEFT_BACK = 0,
                      LEFT_FRONT = 1,
                      RIGHT_BACK = 2,
                      RIGHT_FRONT = 3;

    private static final double kP = 0,
                         kI = 0,
                         kD = 0;

    public double PIDOutput = 0;

    double previousThrottle = 0,
            previousTurn = 0,
            maxTurnDelta = .05,
            maxThrottleDelta = .05;

    public int upShiftMidpoint = 500,
                downShiftMidpoint = 1000;

    public AHRS navx;

    public boolean shiftPause;

    private CANSparkMax[] motors = new CANSparkMax[4];

    private CANEncoder[] encoders = new CANEncoder[4];

    private boolean straightModeStart, straightModeRun;
    private double runDelay;

    public Drivetrain() {
        super("TurnToAnglePID", kP, kI, kD);

        navx = new AHRS(SPI.Port.kMXP);

        motors[LEFT_BACK] = new CANSparkMax(RobotMap.CAN.LEFT_BACK_MOTOR, MotorType.kBrushless);
        motors[LEFT_FRONT] = new CANSparkMax(RobotMap.CAN.LEFT_FRONT_MOTOR, MotorType.kBrushless);
        motors[RIGHT_BACK] = new CANSparkMax(RobotMap.CAN.RIGHT_BACK_MOTOR, MotorType.kBrushless);
        motors[RIGHT_FRONT] = new CANSparkMax(RobotMap.CAN.RIGHT_FRONT_MOTOR, MotorType.kBrushless);

        encoders[LEFT_BACK] = motors[LEFT_BACK].getEncoder();
        encoders[LEFT_FRONT] = motors[LEFT_FRONT].getEncoder();
        encoders[RIGHT_BACK] = motors[RIGHT_BACK].getEncoder();
        encoders[RIGHT_FRONT] = motors[RIGHT_FRONT].getEncoder();

        shiftSolenoid = new DoubleSolenoid(RobotMap.PCM.PCM_ID, RobotMap.PCM.SHIFT_GEAR_1, RobotMap.PCM.SHIFT_GEAR_2);

        double leftEncoders = (encoders[LEFT_BACK].getPosition() + encoders[LEFT_FRONT].getPosition())/2;
        double rightEncoders = (encoders[RIGHT_BACK].getPosition() + encoders[RIGHT_FRONT].getPosition())/2;

        SpeedControllerGroup leftGroup = new SpeedControllerGroup(motors[LEFT_BACK], motors[LEFT_FRONT]);
        SpeedControllerGroup rightGroup = new SpeedControllerGroup(motors[RIGHT_BACK], motors[RIGHT_FRONT]);

        robotDrive = new DifferentialDrive(motors[LEFT_FRONT], motors[RIGHT_FRONT]);

        motors[LEFT_BACK].follow(motors[LEFT_FRONT]);
        motors[RIGHT_BACK].follow(motors[RIGHT_FRONT]);

        motors[LEFT_FRONT].setRampRate(.5);
        motors[RIGHT_FRONT].setRampRate(.5);

        straightModeStart = false;
        straightModeRun = false;

    }

    public void updateDrivetrain() {
        SmartDashboard.putNumber("WheelRPM Left", wheelRPM(LEFT_FRONT));
        SmartDashboard.putNumber("WheelRPM Right", wheelRPM(RIGHT_FRONT));
        SmartDashboard.putBoolean("Is High Gear", isHighGear());
        SmartDashboard.putBoolean("Is Low Gear", isLowGear());
        SmartDashboard.putNumber("Encoder Left", getEncoder(LEFT_FRONT));
        SmartDashboard.putNumber("Encoder Right", getEncoder(RIGHT_FRONT));
        SmartDashboard.putBoolean("Straight Mode", straightModeRun);
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
        shiftSolenoid.set(Value.kReverse);
    }

    public void shiftLow() {
        shiftSolenoid.set(Value.kForward);
    }

    public double wheelRPM(int n) {
        if(isHighGear()) {
            return encoders[n].getVelocity() / 5.1;
        } else {
            return encoders[n].getVelocity() / 11.03;
        }
    }

    public boolean isHighGear() {
        return shiftSolenoid.get() == Value.kReverse;
    }

    public boolean isLowGear() {
        return shiftSolenoid.get() == Value.kForward;
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new DriveControl());
    }

    public void autoShift() {
        if(straightModeRun) {
            if(Math.abs(wheelRPM(LEFT_FRONT)) > upShiftMidpoint && Math.abs(wheelRPM(RIGHT_FRONT)) > upShiftMidpoint){
                if (!isHighGear()) {
                    shiftHigh();         
                }
            }
            if(Math.abs(wheelRPM(LEFT_FRONT)) < downShiftMidpoint && Math.abs(wheelRPM(RIGHT_FRONT)) < downShiftMidpoint) {
                if (isHighGear()) {
                shiftLow();
                }
            }
        }
       
    }

    @Override
    public void setSetpoint(double setpoint) {
        super.setSetpoint(setpoint);
    }

    public double returnPIDInput(){
        return navx.getAngle();
    }

    public void usePIDOutput(double output){
        PIDOutput = output;
    }
}