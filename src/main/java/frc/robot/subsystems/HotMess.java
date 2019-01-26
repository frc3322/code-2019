/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.HotMessIdle;

/**
 * Add your docs here.
 */
public class HotMess extends Subsystem {
    
    private WPI_TalonSRX motor1,
                        motor2;
    
    private SpeedControllerGroup motorGroup;

    public HotMess(){

        motor1 = new WPI_TalonSRX(RobotMap.CAN.HOTMESS_MOTOR1);
        motor2 = new WPI_TalonSRX(RobotMap.CAN.HOTMESS_MOTOR2);

        motorGroup = new SpeedControllerGroup(motor1, motor2);

    }

    public void climb(){

        motorGroup.set(1);

    }

    public void stop(){

        motorGroup.set(0);

    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new HotMessIdle());
    }
}
