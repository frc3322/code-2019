/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/*
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.CANSparkMax;
import edu.wpi.first.wpilibj.CANEncoder;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.PIDController;
import frc.robot.RobotMap;
import frc.robot.commands.ElevatorControl;


public class Elevator extends Subsystem {

    //create constants

    private SpeedControllerGroup elevator;
    private Encoder encoder;
    private PIDController pid;
    private Double upSpeed=1.0; //temp
    private Double downSpeed=-1.0; //temp

    public Elevator() {
        Talon elevatorMotor1 = new Talon(RobotMap.CAN.ELEVATOR_MOTOR_1); //create elevator motors and assign to speed group for easy control
        Talon elevatorMotor2 = new Talon(RobotMap.CAN.ELEVATOR_MOTOR_2); //need to map motors
        elevator = new SpeedControllerGroup(elevatorMotor1, elevatorMotor2);
        elevator.setInverted(true);

        encoder = new Encoder(RobotMap.DIO.ELEVATOR_ENCODER_A, RobotMap.DIO.ELEVATOR_ENCODER_B);

        pid = new PIDController("Elevator", ELEVATOR_KP, ELEVATOR_DECAY, ELEVATOR_KI, ELEVATOR_KD);
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

}

*/