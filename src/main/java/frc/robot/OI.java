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
    Button bumper_left = new JoystickButton(upperChassis, RobotMap.XBOX.BUMPER_LEFT);
    Button bumper_right = new JoystickButton(upperChassis, RobotMap.XBOX.BUMPER_RIGHT);
    Button button_back = new JoystickButton(upperChassis, RobotMap.XBOX.BUTTON_BACK);
    Button button_start = new JoystickButton(upperChassis, RobotMap.XBOX.BUTTON_START);

    public OI(){

        bumper_right.whileHeld(new LimelightAlign());

        bumper_left.whenPressed(new ElevatorControl(true));
        bumper_right.whenPressed(new ElevatorControl(false));
        
        button_a.whenPressed(new HotMessClimb());
        button_a.whenReleased(new HotMessIdle());
        button_b.whenPressed(new HotMessForceClimb());
        button_b.whenReleased(new HotMessIdle());

    }

}
