/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

public class RobotMap {

    public static class CAN {

        //drivetrain
        public static final int LEFT_FRONT_MOTOR = 36 ;
        public static final int LEFT_BACK_MOTOR = 37;
        public static final int RIGHT_FRONT_MOTOR = 35;
        public static final int RIGHT_BACK_MOTOR = 34;

        //hot mess
        public static final int HOTMESS_MOTOR1 = 4;
        public static final int HOTMESS_MOTOR2 = 5;

        //cargo
        public static final int L_SIDE_OUTTAKE = 0;
        public static final int R_SIDE_OUTTAKE = 1;
        public static final int INTAKE_MOTOR = 2;

        //elevator
        public static final int ELEVATOR_MOTOR_1 = 4;
        public static final int ELEVATOR_MOTOR_2 = 5;

    }

    public static class DIO {
        public static final int HATCH_DETECTOR = 2;

        public static final int LEFT_INFRARED = 0;
        public static final int RIGHT_INFRARED = 1;

      //elevator
        public static final int HALL_EFFECT_LEVEL_0 = 10;
        public static final int HALL_EFFECT_LEVEL_1 = 11;
        public static final int HALL_EFFECT_LEVEL_2 = 12;
        public static final int HALL_EFFECT_LEVEL_3 = 13;
        public static final int ELEVATOR_M_ENCODER_1_A = 14;
        public static final int ELEVATOR_M_ENCODER_1_B = 15;
        public static final int ELEVATOR_M_ENCODER_2_A = 16;
        public static final int ELEVATOR_M_ENCODER_2_B = 17;
        public static final int ELEVATOR_TRACK_ENCODER_A = 18;
        public static final int ELEVATOR_TRACK_ENCODER_B = 19;

    }

    public static class PCM {

        public static final int PCM_ID = 3;

        public static final int SHIFT_GEAR_1 = 0;
        public static final int SHIFT_GEAR_2 = 1;
        public static final int LOWER_MECHANISM = 2;
        public static final int RAISE_MECHANSIM = 3;
        public static final int GRAB_HATCH = 4;
        public static final int RELEASE_HATCH = 5;
        

        public static final int INTAKE_EXTEND = 4;
        public static final int INTAKE_RETRACT = 5;
 
    }
  
    public static class XBOX {
      // Buttons
        public static final int BUTTON_A = 1;
        public static final int BUTTON_B = 2;
        public static final int BUTTON_X = 3;
        public static final int BUTTON_Y = 4;
        public static final int BUMPER_LEFT = 5;
        public static final int BUMPER_RIGHT = 6;
        public static final int BUTTON_BACK = 7;
        public static final int BUTTON_START = 8;
        public static final int STICK_LEFT = 9;
        public static final int STICK_RIGHT = 10;

       // Axes
        public static final int STICK_L_X_AXIS = 0;
        public static final int STICK_L_Y_AXIS = 1;
        public static final int STICK_R_X_AXIS = 4;
        public static final int STICK_R_Y_AXIS = 5;
        public static final int TRIGGER_L_AXIS = 2;
        public static final int TRIGGER_R_AXIS = 3;
  }
  
}
