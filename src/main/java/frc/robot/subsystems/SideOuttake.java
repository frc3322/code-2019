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

/**
 * Add your docs here.
 */
public class SideOuttake extends Subsystem{
    private double slowSpeedModifier = 0.3;
    private double fastSpeedModifier = 0.75;
    private double leftOuttakeModifier = 0.8;

    public static final long outtakeTime = 2000;

    WPI_TalonSRX leftOuttake = new WPI_TalonSRX(RobotMap.CAN.L_SIDE_OUTTAKE);
    WPI_TalonSRX rightOuttake = new WPI_TalonSRX(RobotMap.CAN.R_SIDE_OUTTAKE);

    DigitalInput leftInfrared = new DigitalInput(RobotMap.DIO.LEFT_INFRARED);
    DigitalInput rightInfrared = new DigitalInput(RobotMap.DIO.RIGHT_INFRARED);

    public SideOuttake() {
        SmartDashboard.putBoolean("Right Infrared", getRightInfrared());
        SmartDashboard.putBoolean("Left Infrared", getLeftInfrared());

        leftOuttake.setInverted(true);
    }

    public void outtakeRight(double baseSpeed) {
        leftOuttake.set(baseSpeed*slowSpeedModifier*leftOuttakeModifier);
        rightOuttake.set(baseSpeed*fastSpeedModifier);
    }

    public void outtakeLeft(double baseSpeed) {
        rightOuttake.set(baseSpeed*slowSpeedModifier);
        leftOuttake.set(baseSpeed*fastSpeedModifier*leftOuttakeModifier);
    }

    public void outtakeStop() {
        leftOuttake.set(0);
        rightOuttake.set(0);
    }

    public void intakeCarriage() {
        leftOuttake.set(.5);
        rightOuttake.set(-.5);
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
