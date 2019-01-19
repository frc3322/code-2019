/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

//import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
//import frc.robot.PIDController;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class CargoManip extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

   
	WPI_TalonSRX cargoIntakeMotor = new WPI_TalonSRX(RobotMap.CAN.cargoMotor1);
    

 	public CargoManip(){


  	}
    public void setIntakeMotorSpeed(double speed){
        cargoIntakeMotor.set(speed);
    } 
   
	@Override
  	public void initDefaultCommand() {
    	// Set the default command for a subsystem here.
    	// setDefaultCommand(new MySpecialCommand());
  	}
}
