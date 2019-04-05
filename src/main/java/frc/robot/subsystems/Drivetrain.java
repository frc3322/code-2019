/**
 *  _____    _____     _____     _____   
 * |___  \  |___  \   /  _  \   /  _  \
 *  ___|  |  ___|  | |__| |  | |__| |  |
 * |___   | |___   |     /  /      /  /
 *  ___|  |  ___|  |   /  /__    /  /__
 * |_____/  |_____/   |______|  |______|
 *
 */

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.SPI;

import frc.robot.RobotMap;
import frc.robot.commands.DriveControl;

import static frc.robot.Robot.limelight;

/**
 * Code for drive train
 */
public class Drivetrain extends Subsystem {
  
    private DifferentialDrive robotDrive;

    private final int LEFT_BACK = 0,
                      LEFT_FRONT = 1,
                      RIGHT_BACK = 2,
                      RIGHT_FRONT = 3;
                    
    double previousThrottle = 0,
            previousTurn = 0,
            maxTurnDelta = .05,
            maxThrottleDelta = .05;

    public AHRS navx;

    public boolean shiftPause;

    public boolean isHatchFront = false;

    private CANSparkMax[] motors = new CANSparkMax[4];

    private CANEncoder[] encoders = new CANEncoder[4];

    private boolean straightModeStart, straightModeRun;
    private double runDelay;

    private PIDController pidForDriveStraight;
    private double pidOutputForDriveStraight;

    public PIDController limelightPID;
    private double limelightPIDOutput;

    public boolean limeControlling = false;
    public boolean outtakeControlling = false;

    public double rampRate = .4;

    public Drivetrain() {
        
        navx = new AHRS(SPI.Port.kMXP);

        motors[LEFT_BACK] = new CANSparkMax(RobotMap.CAN.LEFT_BACK_MOTOR, MotorType.kBrushless);
        motors[LEFT_FRONT] = new CANSparkMax(RobotMap.CAN.LEFT_FRONT_MOTOR, MotorType.kBrushless);
        motors[RIGHT_BACK] = new CANSparkMax(RobotMap.CAN.RIGHT_BACK_MOTOR, MotorType.kBrushless);
        motors[RIGHT_FRONT] = new CANSparkMax(RobotMap.CAN.RIGHT_FRONT_MOTOR, MotorType.kBrushless);

        encoders[LEFT_BACK] = motors[LEFT_BACK].getEncoder();
        encoders[LEFT_FRONT] = motors[LEFT_FRONT].getEncoder();
        encoders[RIGHT_BACK] = motors[RIGHT_BACK].getEncoder();
        encoders[RIGHT_FRONT] = motors[RIGHT_FRONT].getEncoder();


        robotDrive = new DifferentialDrive(motors[LEFT_FRONT], motors[RIGHT_FRONT]);

        motors[LEFT_BACK].follow(motors[LEFT_FRONT]);
        motors[RIGHT_BACK].follow(motors[RIGHT_FRONT]);

        motors[LEFT_FRONT].setOpenLoopRampRate(rampRate);
        motors[RIGHT_FRONT].setOpenLoopRampRate(rampRate);

        motors[LEFT_FRONT].setSmartCurrentLimit(40);
        motors[RIGHT_FRONT].setSmartCurrentLimit(40);
        motors[LEFT_BACK].setSmartCurrentLimit(40);
        motors[RIGHT_BACK].setSmartCurrentLimit(40);

        straightModeStart = false;
        straightModeRun = false;

        runDelay = System.currentTimeMillis();
        
        /*
        pidForDriveStraight = new PIDController(0.025, 0, 0, new PIDSource(){ //@jonathan
            PIDSourceType m_sourceType = PIDSourceType.kDisplacement;

            @Override
            public void setPIDSourceType(PIDSourceType pidSource) {
                m_sourceType = pidSource;
            }
        
            @Override
            public double pidGet() {
                return navx.getYaw();
            }
        
            @Override
            public PIDSourceType getPIDSourceType() {
                return m_sourceType;
            }
        }, new PIDOutput(){

            @Override
            public void pidWrite(double output) {       
                pidOutputForDriveStraight = output;
            } 

        });

        pidForDriveStraight.setAbsoluteTolerance(3);
		pidForDriveStraight.setInputRange(-180f, 180f);
		pidForDriveStraight.setOutputRange(-.4, .4);
		pidForDriveStraight.setContinuous(true);
        pidForDriveStraight.setSetpoint(navx.getYaw());
        
        
        limelightPID = new PIDController(0.007 , 0, 0.004, new PIDSource(){
            PIDSourceType m_sourceType = PIDSourceType.kDisplacement;

            @Override
            public void setPIDSourceType(PIDSourceType pidSource) {
                m_sourceType = pidSource;
            }

            @Override
            public double pidGet() {
                return navx.getYaw();
            }

            @Override
            public PIDSourceType getPIDSourceType() {
                return m_sourceType;
            }
        }, new PIDOutput() {

            @Override
            public void pidWrite(double output) {
                limelightPIDOutput = output;
            }

        });

        limelightPID.setAbsoluteTolerance(3);
		limelightPID.setInputRange(-180f, 180f);
		limelightPID.setOutputRange(-.4, .4);
		limelightPID.setContinuous(true);

        */
    }

    public void updateDrivetrain() {
        //SmartDashboard.putNumber("WheelRPM Left", wheelRPM(LEFT_FRONT));
        //SmartDashboard.putNumber("WheelRPM Right", wheelRPM(RIGHT_FRONT));
        //SmartDashboard.putBoolean("Is High Gear", isClimbUp());
        //SmartDashboard.putBoolean("Is Low Gear", isClimbDown());
        SmartDashboard.putNumber("Encoder Left", getEncoder(LEFT_FRONT));
        SmartDashboard.putNumber("Encoder Right", getEncoder(RIGHT_FRONT));
        SmartDashboard.putNumber("Drivetrain Angle", navx.getYaw());
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

    public void tankDrive(double leftSpeed, double rightSpeed) {
        robotDrive.tankDrive(leftSpeed, rightSpeed);
    }


    /*
    public void limeDrive(double speed) {
        
        //navx.reset();
        limelightPID.setSetpoint(limelight.getTx());
        limelightPID.enable();
        tankDrive(speed - limelightPIDOutput, speed + limelightPIDOutput);
    }
    */

    /*

    public void driveStraight(Double speed, double rotation){
        if(Math.abs(speed) > 0.15 && Math.abs(rotation) < 0.15){
            if (!straightModeStart) {
                straightModeStart = true;
                runDelay = System.currentTimeMillis();
            }
            // Wait a bit before setting our desired angle
            if (System.currentTimeMillis() - runDelay > 250 && !straightModeRun) {
		        pidForDriveStraight.reset();
                pidForDriveStraight.enable();             
                straightModeRun = true;
            }
            if (straightModeRun) {
                tankDrive(speed - pidOutputForDriveStraight / 2, speed + pidOutputForDriveStraight / 2);
            } else {
                drive(speed, rotation);
            }
        }else{
            if(straightModeStart){
                straightModeStart = false;
                straightModeRun = false;               
                pidForDriveStraight.disable();
            }
            drive(speed, rotation);
        }
    }
    */
    public void stop(){
        drive(0, 0);
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new DriveControl());
    }

    public double getAngle(){
        return navx.getAngle();
    }
}