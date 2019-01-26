/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.*;

/**
 * Add your docs here.
 */
public class SideOuttake extends Subsystem{

    public static final long outtakeTime = 2000;

    CANSparkMax leftOuttake = new CANSparkMax(RobotMap.CAN.L_SIDE_OUTTAKE, MotorType.kBrushless);
    CANSparkMax rightOuttake = new CANSparkMax(RobotMap.CAN.R_SIDE_OUTTAKE, MotorType.kBrushless);

    DigitalInput leftInfrared = new DigitalInput(RobotMap.DIO.LEFT_INFRARED);
    DigitalInput rightInfrared = new DigitalInput(RobotMap.DIO.RIGHT_INFRARED);

    SpeedControllerGroup outtake = new SpeedControllerGroup(leftOuttake, rightOuttake);

    public SideOuttake() {
        SmartDashboard.putBoolean("Right Infrared", getRightInfrared());
        SmartDashboard.putBoolean("Left Infrared", getLeftInfrared());
    }

    public void outtakeRight() {
        outtake.set(1);
    }

    public void outtakeLeft() {
        outtake.set(-1);
    }

    public void outtakeStop() {
        outtake.set(0);
    }

    public boolean getRightInfrared() {
        return rightInfrared.get();
    }

    public boolean getLeftInfrared() {
        return leftInfrared.get();
    }

    @Override
    protected void initDefaultCommand() {

    }
}
