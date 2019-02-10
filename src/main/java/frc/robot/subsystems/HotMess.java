package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.HotMessIdle;

/**
 * Add your docs here.
 */
public class HotMess extends Subsystem {
    
    private CANSparkMax motor1,
                        motor2;
    
    private CANEncoder encoder1,
                       encoder2;
    
    private SpeedControllerGroup motorGroup;

    public HotMess(){

        motor1 = new CANSparkMax(RobotMap.CAN.HOTMESS_MOTOR1, MotorType.kBrushless);
        motor2 = new CANSparkMax(RobotMap.CAN.HOTMESS_MOTOR2, MotorType.kBrushless);

        encoder1 = motor1.getEncoder();
        encoder2 = motor2.getEncoder();

        motorGroup = new SpeedControllerGroup(motor1, motor2);

        motor1.setInverted(true);

        //motor1.setRampRate(0.5);
        //motor2.setRampRate(0.5);

    }

    public void climb(double speed){
        if(speed >= .5) {
            speed = .5;
            motorGroup.set(speed);
        } else {
            motorGroup.set(speed);
        }
    }

    public void stop(){

        motorGroup.set(0);

    }

    public void reverse() {
        motorGroup.set(-.2);
    }

    public double getEncoderVal(){

        return (encoder1.getPosition() + encoder2.getPosition()) / 2;

    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new HotMessIdle());
    }
}
