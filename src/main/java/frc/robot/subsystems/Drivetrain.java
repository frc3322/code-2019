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

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.SPI;

import frc.robot.RobotMap;
import frc.robot.commands.DriveControl;
import frc.robot.Constants;

import static frc.robot.Robot.limelight;

/**
 * Code for drive train
 */
public class Drivetrain extends Subsystem {
  
    private DifferentialDrive robotDrive;

    private DoubleSolenoid shiftSolenoid;

    private final int LEFT_BACK = 0,
                      LEFT_FRONT = 1,
                      RIGHT_BACK = 2,
                      RIGHT_FRONT = 3;
                    
    double previousThrottle = 0,
            previousTurn = 0,
            maxTurnDelta = .05,
            maxThrottleDelta = .05;

    public int upShiftMidpoint = 510,
                downShiftMidpoint = 700; //TODO: Tweak the autoshift

    public AHRS navx;

    public boolean shiftPause;

    private CANSparkMax[] motors = new CANSparkMax[4];

    private CANEncoder[] encoders = new CANEncoder[4];

    private boolean straightModeStart, straightModeRun;
    private double runDelay, lastShift;

    private PIDController pidForDriveStraight;
    private double pidOutputForDriveStraight;

    public PIDController limelightPID;
    private double limelightPIDOutput;

    public boolean limeControlling = false;
    public boolean outtakeControlling = false;

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

        shiftSolenoid = new DoubleSolenoid(RobotMap.PCM.PCM_ID, RobotMap.PCM.SHIFT_GEAR_1, RobotMap.PCM.SHIFT_GEAR_2);

        robotDrive = new DifferentialDrive(motors[LEFT_FRONT], motors[RIGHT_FRONT]);

        motors[LEFT_BACK].follow(motors[LEFT_FRONT]);
        motors[RIGHT_BACK].follow(motors[RIGHT_FRONT]);

        //TODO tune these values
        motors[LEFT_FRONT].setOpenLoopRampRate(0.3);
        motors[RIGHT_FRONT].setOpenLoopRampRate(0.3);

        straightModeStart = false;
        straightModeRun = false;

        lastShift = System.currentTimeMillis() - 2000;
        runDelay = System.currentTimeMillis();
        
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
        pidForDriveStraight.setSetpoint(0);
        
        limelightPID = new PIDController(0.007 , 0, 0.004, new PIDSource(){ //@jonathan
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

    }

    public void updateDrivetrain() {
        SmartDashboard.putNumber("WheelRPM Left", wheelRPM(LEFT_FRONT));
        SmartDashboard.putNumber("WheelRPM Right", wheelRPM(RIGHT_FRONT));
        //SmartDashboard.putBoolean("Is High Gear", isHighGear());
        //SmartDashboard.putBoolean("Is Low Gear", isLowGear());
        SmartDashboard.putNumber("Encoder Left", getEncoder(LEFT_FRONT));
        SmartDashboard.putNumber("Encoder Right", getEncoder(RIGHT_FRONT));
        SmartDashboard.putBoolean("Straight Mode", straightModeRun);
        SmartDashboard.putNumber("PID Output", pidOutputForDriveStraight);
        SmartDashboard.putNumber("Setpoint", pidForDriveStraight.getSetpoint());
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

    public void limeDrive(double speed) {
        
        navx.reset();
        limelightPID.setSetpoint(limelight.getTx());
        limelightPID.enable();
        tankDrive(speed - limelightPIDOutput, speed + limelightPIDOutput);
    }

    public void driveStraight(Double speed, double rotation){
        if(Math.abs(speed) > 0.15 && Math.abs(rotation) < 0.15){
            if (!straightModeStart) {
                straightModeStart = true;
                runDelay = System.currentTimeMillis();
            }
            // Wait a bit before setting our desired angle
            if (System.currentTimeMillis() - runDelay > 250 && !straightModeRun) {
                navx.reset();
		        pidForDriveStraight.reset();
                pidForDriveStraight.enable();             
                straightModeRun = true;
            }
            if (straightModeRun) {
                tankDrive(speed - pidOutputForDriveStraight / 2, speed + pidOutputForDriveStraight / 2);
                //drive(speed, pidOutputForDriveStraight);
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
        return false; //shiftSolenoid.get() == Value.kReverse;
    }

    public boolean isLowGear() {
        return true; //shiftSolenoid.get() == Value.kForward;
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new DriveControl());
    }

    public void autoShift() {
        if(System.currentTimeMillis() - lastShift > 800 && straightModeRun) {
            if(Math.abs(wheelRPM(LEFT_FRONT)) > upShiftMidpoint && Math.abs(wheelRPM(RIGHT_FRONT)) > upShiftMidpoint){
                if (!isHighGear()) {
                    shiftHigh();
                    lastShift = System.currentTimeMillis();    
                }
            }
            if(Math.abs(wheelRPM(LEFT_FRONT)) < downShiftMidpoint && Math.abs(wheelRPM(RIGHT_FRONT)) < downShiftMidpoint) {
                if (isHighGear()) {
                    shiftLow();
                    lastShift = System.currentTimeMillis();
                }
            }
        }
       
    }

    public double degreeToRadian(double degree) {       
        return (degree * Math.PI) / 180;
    }

    public double radianToDegree(double radian) {
        return (radian * 180) / Math.PI;
    }
    public double getAngle(){
        return navx.getAngle();
    }
    public double getAngleToTarget(){
        //calculate distance from the limelight to the target using equation found on limelight website
        double limelightDistanceToTarget = (Constants.FieldDetails.targetHeight - Constants.LimelightMountingDetails.height) / Math.tan(degreeToRadian((Constants.LimelightMountingDetails.mountingAngle + Limelight.getTy())));
        //calculate the angle from the limelight to the target based on how the limelight is mounted and the angle that the limelight calculates between the center of its fov and the target
        double limelightAngleFromTarget = Constants.LimelightMountingDetails.angleOffset - Limelight.getTx();
        
        //calculate the distance between the robot and the target using the law of cosines
        double robotDistanceToTarget = Math.sqrt(Math.pow(limelightDistanceToTarget, 2) + Math.pow(Constants.LimelightMountingDetails.centerOffset, 2) - 2 * limelightDistanceToTarget * Constants.LimelightMountingDetails.centerOffset * Math.cos(degreeToRadian(limelightAngleFromTarget)));
        //calculate the angle between the robot and the target using the law of cosines
        double calculatedAngle = radianToDegree(Math.acos(Math.sqrt((Math.pow(limelightDistanceToTarget, 2) - Math.pow(Constants.LimelightMountingDetails.centerOffset, 2) - Math.pow(robotDistanceToTarget, 2)) / -2 * Constants.LimelightMountingDetails.centerOffset * robotDistanceToTarget)));
        //find the turn angle
        double returnAngle = 90 - calculatedAngle;

        return returnAngle;
    }
}