
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

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.OuttakeControl;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.*;

public class SideOuttake extends Subsystem{
    private double slowSpeedModifier = 0.3;
    private double fastSpeedModifier = 0.75;  //https://i.imgur.com/BKIr3Kt.jpg

    public static final long outtakeTime = 2000;

    WPI_TalonSRX leftOuttake = new WPI_TalonSRX(RobotMap.CAN.L_SIDE_OUTTAKE);
    WPI_TalonSRX rightOuttake = new WPI_TalonSRX(RobotMap.CAN.R_SIDE_OUTTAKE);

    DigitalInput leftInfrared = new DigitalInput(RobotMap.DIO.LEFT_INFRARED);
    DigitalInput rightInfrared = new DigitalInput(RobotMap.DIO.RIGHT_INFRARED);

    public SideOuttake() {
        leftOuttake.setInverted(true);
        rightOuttake.setInverted(true);

    }

    public void outtakeRight(double baseSpeed) {
        leftOuttake.set((baseSpeed*slowSpeedModifier));
        rightOuttake.set(-(baseSpeed*fastSpeedModifier));
    }

    public void outtakeLeft(double baseSpeed) {
        rightOuttake.set((baseSpeed*slowSpeedModifier));
        leftOuttake.set(-(baseSpeed*fastSpeedModifier));
    }

    public void outtakeStop() {
        leftOuttake.set(0);
        rightOuttake.set(0);
    }

    public void intakeCarriage() {
        leftOuttake.set(-.2);
        rightOuttake.set(-.2);
    }

    public void update(){
        SmartDashboard.putBoolean("Right Infrared", rightInfrared.get());
        SmartDashboard.putBoolean("Left Infrared", leftInfrared.get());
    }

    public boolean getRightInfrared() {
        return rightInfrared.get();
    }

    public boolean getLeftInfrared() {
        return leftInfrared.get();
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new OuttakeControl());
    }
}
