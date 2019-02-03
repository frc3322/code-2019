/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;


import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.*;

/**
 * Add your docs here.
 */
public class SideOuttake extends Subsystem{

    public static final long outtakeTime = 2000;

    WPI_TalonSRX leftOuttake = new WPI_TalonSRX(RobotMap.CAN.L_SIDE_OUTTAKE);
    WPI_TalonSRX rightOuttake = new WPI_TalonSRX(RobotMap.CAN.R_SIDE_OUTTAKE);

    DigitalInput leftInfrared = new DigitalInput(RobotMap.DIO.LEFT_INFRARED);
    DigitalInput rightInfrared = new DigitalInput(RobotMap.DIO.RIGHT_INFRARED);
    DigitalInput cargoDetector = new DigitalInput(RobotMap.DIO.CARGO_DETECTOR_OUTTAKE);

    public SideOuttake() {
        SmartDashboard.putBoolean("Right Infrared", getRightInfrared());
        SmartDashboard.putBoolean("Left Infrared", getLeftInfrared());
    }

    public void outtakeRight() {
        leftOuttake.set(0);
        rightOuttake.set(1);
    }

    public void outtakeLeft() {
        rightOuttake.set(0);
        leftOuttake.set(1);
    }

    public void outtakeStop() {
        leftOuttake.set(0);
        rightOuttake.set(0);
    }

    public void intakeCarriage() {
        leftOuttake.set(1);
        rightOuttake.set(-1);
    }

    public boolean hasCargo() {
        return cargoDetector.get();
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
