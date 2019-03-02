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
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

// import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

//import frc.robot.PIDController;
import frc.robot.RobotMap;
import frc.robot.commands.ElevatorControl;

public class Elevator extends PIDSubsystem {

    // create constants

    private SpeedControllerGroup elevator;
    
    private int currentLevel = 0;
    private int desiredLevel;
    private double upSpeed = 0.2; // temp
    private double downSpeed = -0.2; // temp
    private double bottom = 0;
    private double firstLevel = 1000; // temp
    private double secondLevel = 2000; // temp
    private double thirdLevel = 3000; // temp
    private double top = 7850;
    public double pidSpeed;
    public double speedModifier = .75;
    public boolean canMoveUp = true;
    public boolean canMoveDown = true;

    Encoder elevatorEncoder;

    WPI_TalonSRX elevatorMotor1;
    WPI_TalonSRX elevatorMotor2;
    
    DigitalInput elevatorLimitSwitch;

    public Elevator() {
        super("Elevator PID", .03, 0, 0);
        setAbsoluteTolerance(20);
        getPIDController().setContinuous(false);
        // create elevator motors and assign to speed group for easy control

        elevatorEncoder = new Encoder(RobotMap.DIO.ELEVATOR_ENCODER_A, RobotMap.DIO.ELEVATOR_ENCODER_B);
        elevatorLimitSwitch = new DigitalInput(RobotMap.DIO.ELEVATOR_LIMIT_SWITCH);
        
        elevatorMotor1 = new WPI_TalonSRX(RobotMap.CAN.ELEVATOR_MOTOR_1);
        elevatorMotor2 = new WPI_TalonSRX(RobotMap.CAN.ELEVATOR_MOTOR_2);

        elevatorMotor1.setInverted(true);
        elevatorMotor2.setInverted(true);

        elevatorMotor2.follow(elevatorMotor1);
        elevator = new SpeedControllerGroup(elevatorMotor1, elevatorMotor2);
        
    }

    public Elevator(double upSpeed, double downSpeed) { // set up and down speeds
        this();
        this.upSpeed = upSpeed;
        this.downSpeed = downSpeed;
    }

    public void update() {
        SmartDashboard.putNumber("Elevator Encoder", elevatorEncoder.getDistance());
        SmartDashboard.putBoolean("Elevator Limit Switch", elevatorLimitSwitch.get());
        SmartDashboard.putBoolean("Elevator Can Move Up", canMoveUp);
        SmartDashboard.putBoolean("Elevator Can Move Down", canMoveDown);
        onLimitSwitch();
        atTop();
    }

    public void reset() {
        elevatorEncoder.reset();
    }

    public void onLimitSwitch(){
        if(elevatorLimitSwitch.get()) {
            canMoveDown = false;
            reset();
        }
    }

    public void atTop(){
        if(elevatorEncoder.getDistance() > top){
            canMoveUp = false;
        }
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new ElevatorControl()); // run elevator command in Commands
    }

    private double toInchRatio(double input) {
        // This ratio determines the lift translation based on experimental data
        double inchesTraveled = 58.3; //temp
        double encoderTicks = 8371; //temp
        return input * (inchesTraveled / encoderTicks);
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
            setSetpoint(bottom);
            currentLevel = 0;
            break;
        case 1:
            desiredLevel = 1;
            setSetpoint(firstLevel);
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
        //elevator.set(pidSpeed);

    }

    @Override
    protected double returnPIDInput() {
        return elevatorEncoder.getDistance();
    }

    @Override
    protected void usePIDOutput(double output) {
        //pidSpeed = output;
        elevatorMotor1.pidWrite(output);
    }

}
