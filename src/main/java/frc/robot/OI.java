/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    public Joystick lowerChassis = new Joystick(0);
    public Joystick upperChassis = new Joystick(1);
    
    //new buttons goes here
    Button button_a = new JoystickButton(upperChassis, RobotMap.XBOX.BUTTON_A);
    Button button_b = new JoystickButton(upperChassis, RobotMap.XBOX.BUTTON_B);
    Button button_x = new JoystickButton(upperChassis, RobotMap.XBOX.BUTTON_X);
    Button button_y = new JoystickButton(upperChassis, RobotMap.XBOX.BUTTON_Y);
    Button bumper_left_upper = new JoystickButton(upperChassis, RobotMap.XBOX.BUMPER_LEFT);
    Button bumper_right_upper = new JoystickButton(upperChassis, RobotMap.XBOX.BUMPER_RIGHT);
    Button button_back_upper = new JoystickButton(upperChassis, RobotMap.XBOX.BUTTON_BACK);
    Button button_start_upper = new JoystickButton(upperChassis, RobotMap.XBOX.BUTTON_START);
    Button left_trigger_upper = new JoystickButton(upperChassis, RobotMap.XBOX.TRIGGER_L_AXIS);
    Button right_trigger_upper = new JoystickButton(upperChassis, RobotMap.XBOX.TRIGGER_R_AXIS);

    Button bumper_left_lower = new JoystickButton(lowerChassis, RobotMap.XBOX.BUMPER_LEFT);
    Button bumper_right_lower = new JoystickButton(lowerChassis, RobotMap.XBOX.BUMPER_RIGHT);
    Button button_back_lower = new JoystickButton(lowerChassis, RobotMap.XBOX.BUTTON_BACK);
    Button button_start_lower = new JoystickButton(lowerChassis, RobotMap.XBOX.BUTTON_START);
    Button left_stick = new JoystickButton(lowerChassis, RobotMap.XBOX.STICK_LEFT);
    Button right_stick = new JoystickButton(lowerChassis, RobotMap.XBOX.STICK_RIGHT);
    Button left_trigger_lower = new JoystickButton(lowerChassis, RobotMap.XBOX.TRIGGER_L_AXIS);
    Button right_trigger_lower = new JoystickButton(lowerChassis, RobotMap.XBOX.TRIGGER_R_AXIS);


    public OI(){

        //lowerChassis controller
        right_stick.whileHeld(new LimelightAlign());
        button_start_lower.whileHeld(new HotMessClimb());
        button_back_lower.whileHeld(new HotMessReverse());
        left_trigger_lower.whileHeld(new LeftOuttake());
        right_trigger_lower.whileHeld(new RightOuttake());
        bumper_right_lower.whenPressed(new GrabHatch());
        left_stick.whenPressed(new ToggleShift());

        //upperChassis controller
        button_y.whenPressed(new GoToLevel(3));
        button_b.whenPressed(new GoToLevel(2));
        button_a.whenPressed(new GoToLevel(1));
        button_x.whenPressed(new GoToLevel(0));
        button_back_upper.whenPressed(new IntakeCargo());
        button_start_upper.whenPressed(new ExtendHatchManip());
        bumper_left_upper.whenPressed(new ToggleIntake());
        bumper_right_upper.whenPressed(new GrabHatch());
        //left_trigger_upper.whileHeld(new ElevatorUp());
        //right_trigger_upper.whileHeld(new ElevatorDown());

        /*
        bumper_right.whileHeld(new LimelightAlign());
        
        button_a.whileHeld(new HotMessClimb());
        button_a.whenReleased(new HotMessIdle());
        //button_b.whenPressed(new HotMessForceClimb());
        //button_b.whenReleased(new HotMessIdle());
        button_x.whileHeld(new HotMessReverse());
        button_x.whenReleased(new HotMessIdle());

        bumper_left.whenPressed(new ToggleIntake());
        button_y.whileHeld(new TestIntake());
        button_y.whenReleased(new IntakeIdle());

        left_stick.whenPressed(new ToggleShift());
        */


    }

}
