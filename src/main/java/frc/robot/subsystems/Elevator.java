/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/


package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
//import frc.robot.PIDController;
import frc.robot.RobotMap;
import frc.robot.commands.ElevatorControl;


public class Elevator extends Subsystem {

    //create constants

    private SpeedControllerGroup elevator;
    private CANEncoder encoder1;
    private CANEncoder encoder2;
    //private PIDController pid;
    private double upSpeed=1; //temp
    private double downSpeed=-1; //temp
    private double firstLevel=10; //temp
    private double secondLevel=20; //temp
    private double thirdLevel=30; //temp
    private double convertToInches=15; //temp
    private double errorMargin=0.25; //temp


    public Elevator() {
        CANSparkMax elevatorMotor1 = new CANSparkMax(RobotMap.CAN.ELEVATOR_MOTOR_1, MotorType.kBrushless); //create elevator motors and assign to speed group for easy control
        CANSparkMax elevatorMotor2 = new CANSparkMax(RobotMap.CAN.ELEVATOR_MOTOR_2, MotorType.kBrushless);
        elevator = new SpeedControllerGroup(elevatorMotor1, elevatorMotor2);
        elevator.setInverted(true);

        encoder1=elevatorMotor1.getEncoder();
        encoder2=elevatorMotor2.getEncoder();

        //pid = new PIDController("Elevator", ELEVATOR_KP, ELEVATOR_DECAY, ELEVATOR_KI, ELEVATOR_KD);
    }

    public Elevator(double upSpeed, double downSpeed) { //set up and down speeds
        this();
        this.upSpeed = upSpeed;
        this.downSpeed = downSpeed;
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new ElevatorControl()); //run elevator command in Commands
    }

    public void moveUp() { //move at current upSpeed
        move(upSpeed);
    }

    public void moveDown() { //move at current downSpeed
        move(-downSpeed);
    }

    public void move(double speed) { //make it move at specified speed
        //test if at top or bottom first, implement later
        elevator.set(speed);
    }

    public void stop() { //stop the elevator
        elevator.set(0);
    } //add more methods as needed

    public void goToLevel(int level) {
        switch(level) {
            case 1:
                goToHeight(firstLevel);
                break;
            case 2:
                goToHeight(secondLevel);
                break;
            case 3:
                goToHeight(thirdLevel);
                break;
            default:
                return;
        }

    }

    public void goToHeight(double height){
        if(height > getHeight() + errorMargin) {
            moveDown();
        } else if(height < getHeight() - errorMargin) {
            moveUp();
        } else {
            stop();
        }
    }

    public double getHeight(){
        return ((encoder1.getPosition() + encoder2.getPosition())/2)*convertToInches;
    }




}

