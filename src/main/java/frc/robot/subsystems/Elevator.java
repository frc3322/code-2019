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

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import static frc.robot.Robot.drivetrain;
import static frc.robot.Robot.oi;

//import frc.robot.PIDController;
import frc.robot.RobotMap;
import frc.robot.commands.ElevatorControl;

public class Elevator extends PIDSubsystem {

    // create constants

    private SpeedControllerGroup elevator;
    
    private int currentLevel = 0;
    private int desiredLevel;
    private double upSpeed = 0.2;
    private double downSpeed = -0.2;
    public double cargoLevel = 0;
    public double secondLevel = 0;
    public double thirdLevel = 0;
    public static double P = 0.3;
    public static double I = 0;
    public static double D = 0;
    public double speedModifier = .75;
    public boolean canMoveUp = true;
    public boolean canMoveDown = true;
    public double moveInput;
    public double pidSpeed;
    CANEncoder elevatorEncoder;

    CANSparkMax elevatorMotor1;
    CANSparkMax elevatorMotor2;
    
    DigitalInput elevatorLimitSwitch;

    public Elevator() {
        super("Elevator PID", P, I, D);
        cargoLevel = SmartDashboard.getNumber("Cargo Level Encoder Value", 2000);
        secondLevel = SmartDashboard.getNumber("Second Level Encoder Value", 3250);
        thirdLevel = SmartDashboard.getNumber("Third Level Encoder Value", 8100);
        P = SmartDashboard.getNumber("Elevator P Value", P);
        I = SmartDashboard.getNumber("Elevator I Value", I);
        D = SmartDashboard.getNumber("Elevator D Value", D);

        setAbsoluteTolerance(20);
        getPIDController().setContinuous(false);
        // create elevator motors and assign to speed group for easy control
        elevatorLimitSwitch = new DigitalInput(RobotMap.DIO.ELEVATOR_LIMIT_SWITCH);
        
        elevatorMotor1 = new CANSparkMax(RobotMap.CAN.ELEVATOR_MOTOR_1,MotorType.kBrushless);
        elevatorMotor2 = new CANSparkMax(RobotMap.CAN.ELEVATOR_MOTOR_2,MotorType.kBrushless);

        elevatorEncoder = elevatorMotor1.getEncoder();

        elevatorMotor1.setInverted(true);
        elevatorMotor2.setInverted(true);

        elevator = new SpeedControllerGroup(elevatorMotor1, elevatorMotor2);
    }

    public Elevator(double upSpeed, double downSpeed) { // set up and down speeds
        this();
        this.upSpeed = upSpeed;
        this.downSpeed = downSpeed;
    }

    public void update() {
        SmartDashboard.putNumber("Elevator Encoder", elevatorEncoder.getPosition());
        SmartDashboard.putBoolean("Elevator Limit Switch", elevatorLimitSwitch.get());
        SmartDashboard.putBoolean("Elevator Can Move Up", canMoveUp);
        SmartDashboard.putBoolean("Elevator Can Move Down", canMoveDown);
        SmartDashboard.putNumber("Elevator Motor Speed", elevatorMotor1.getBusVoltage());

        onLimitSwitch();
        moveInput = (oi.upperChassis.getRawAxis(RobotMap.XBOX.TRIGGER_R_AXIS) - oi.upperChassis.getRawAxis(RobotMap.XBOX.TRIGGER_L_AXIS)) * speedModifier;
    }

    public void reset() {
        elevatorEncoder.setPosition(0);
    }

    public double currentHeight() {
        return elevatorEncoder.getPosition();
    }
    
    public void onLimitSwitch(){
        if(elevatorLimitSwitch.get()) {
            //reset();
        }
    }
    
    public boolean getLimitSwitch() {
        return elevatorLimitSwitch.get();
    }

    public void adjustRampRate() {
        drivetrain.rampRate = .4 + elevatorEncoder.getPosition() / 10000;
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new ElevatorControl()); // run elevator command in Commands
    }

    public void moveUp() { // move at current upSpeed
        move(upSpeed);
    }

    public void moveDown() { // move at current downSpeed
        move(downSpeed);
    }

    public void move(double speed) { // make it move at specified speed
        // test if at top or bottom first, implement later
        elevator.set(speed);
    }

    public void stop() { // stop the elevator
        elevator.set(0);
    } // add more methods as needed

    public void goToLevel(int level) {
        switch (level) {
        case 0:
            desiredLevel = 0;
            disable();
            move(downSpeed);
            currentLevel = 0;
            break;
        case 1:
            desiredLevel = 1;
            setSetpoint(cargoLevel);
            currentLevel = 1;
            break;
        case 2:
            desiredLevel = 2;
            setSetpoint(secondLevel);
            currentLevel = 2;
            break;
        case 3:
            desiredLevel = 3;
            setSetpoint(thirdLevel);
            currentLevel = 3;
            break;
        default:
            return;
        }

    }

    @Override
    protected double returnPIDInput() {
        return elevatorEncoder.getPosition();
    }

    @Override
    protected void usePIDOutput(double output) {
        SmartDashboard.putNumber("PID Output", output);
        pidSpeed = output;
        elevator.pidWrite(output);
    }

}
