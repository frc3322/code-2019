/**
 *  _____    _____     _____     _____   
 * |___  \  |___  \   /  _  \   /  _  \
 *  ___|  |  ___|  | |__| |  | |__| |  |
 * |___   | |___   |     /  /      /  /
 *  ___|  |  ___|  |   /  /__    /  /__
 * |_____/  |_____/   |______|  |______|
 *
 */

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.commands.HotMessClimb;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class HotMess extends Subsystem {
    
    public CANSparkMax motor1;
    
    private CANEncoder encoder1;

    private DoubleSolenoid hotMessSolenoid;

    public HotMess(){
        hotMessSolenoid = new DoubleSolenoid(RobotMap.PCM.PCM_ID, RobotMap.PCM.HOT_MESS_1, RobotMap.PCM.HOT_MESS_2);
        motor1 = new CANSparkMax(RobotMap.CAN.HOTMESS_MOTOR, MotorType.kBrushless);
        encoder1 = motor1.getEncoder();
    }

    public void update() {
        SmartDashboard.putNumber("Motor Current 1", motor1.getOutputCurrent());
    }

    public void climb(double speed){
        if(speed >= 1) {
            speed = 1;
            motor1.set(speed);
        } else {
            motor1.set(speed);
        }
    }

    public void stop(){

        motor1.set(0);

    }

    public void reverse() {
        motor1.set(-.1);
    }

    public void toggleHotMess() {
        if(isClimbUp()) {
            hotMessDown();
        } else {
            hotMessUp();
        }
    }

    public void hotMessUp() {
        hotMessSolenoid.set(Value.kReverse);
    }

    public void hotMessDown() {
        hotMessSolenoid.set(Value.kForward);
    }

    public boolean isClimbUp() {
        return hotMessSolenoid.get() == Value.kReverse;
    }

    public boolean isClimbDown() {
        return hotMessSolenoid.get() == Value.kForward;
    }

    public double getEncoderVal(){

        return (encoder1.getPosition());

    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new HotMessClimb());
    }
}
