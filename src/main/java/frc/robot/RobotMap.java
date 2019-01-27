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
        public static final int LEFT_FRONT_MOTOR = 0;
        public static final int LEFT_BACK_MOTOR = 1;
        public static final int RIGHT_FRONT_MOTOR = 2;
        public static final int RIGHT_BACK_MOTOR = 3;

        //elevator
        public static final int ELEVATOR_MOTOR_1 = 4;
        public static final int ELEVATOR_MOTOR_2 = 5;

    }

    public static class DIO {

      //elevator
    //   public static final int HALL_EFFECT_LEVEL_0 = 0;
    //   public static final int HALL_EFFECT_LEVEL_1 = 1;
    //   public static final int HALL_EFFECT_LEVEL_2 = 2;
    //   public static final int HALL_EFFECT_LEVEL_3 = 3;
        public static final int ELEVATOR_M_ENCODER_1_A = 4;
        public static final int ELEVATOR_M_ENCODER_1_B = 5;
        public static final int ELEVATOR_M_ENCODER_2_A = 6;
        public static final int ELEVATOR_M_ENCODER_2_B = 7;
        public static final int ELEVATOR_TRACK_ENCODER_A = 8;
        public static final int ELEVATOR_TRACK_ENCODER_B = 9;

    }

    public static class PCM {

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
