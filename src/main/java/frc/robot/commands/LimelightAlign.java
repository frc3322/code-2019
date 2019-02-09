/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

import static frc.robot.Robot.drivetrain;
import static frc.robot.Robot.limelight;


/**
 * Add your docs here.
 */
public class LimelightAlign extends Command {

    double angleModifier = .05;

    public LimelightAlign() {
        requires(drivetrain);
        requires(limelight);
    }

    @Override
    protected void initialize(){
        
    }
    
    @Override
    protected void execute() {
        drivetrain.drive(Robot.oi.lowerChassis.getRawAxis(1) * .75, drivetrain.PIDOutput);
        if(Robot.hatchManip.hasHatch()) {
            Robot.hatchManip.hatchGrab();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
