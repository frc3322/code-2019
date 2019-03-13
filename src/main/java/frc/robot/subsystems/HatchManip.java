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
import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * code for hatch manipulation
 */
public class HatchManip extends Subsystem {

    DoubleSolenoid hatchGrabber = new DoubleSolenoid(RobotMap.PCM.PCM_ID, RobotMap.PCM.GRAB_HATCH, RobotMap.PCM.RELEASE_HATCH);
    DoubleSolenoid grabberExtender = new DoubleSolenoid(RobotMap.PCM.PCM_ID, RobotMap.PCM.LOWER_MECHANISM, RobotMap.PCM.RAISE_MECHANSIM);

    public void updateHatch() {
        //SmartDashboard.putBoolean("hatchGrabberActivated", !hatchGrabberActivated());
        //SmartDashboard.putBoolean("hatchGrabberExtended", !hatchGrabberExtended());
    }

    public void grabHatch() {
        if(hatchGrabberActivated()) {
            hatchRelease();
        }else{
            hatchGrab();
        }
    }

    public void extendGrabber() {
        if(hatchGrabberExtended()) {
            grabberRetract();
        }else{
            grabberExtend();
        }
    }

    public void hatchGrab() {
        hatchGrabber.set(DoubleSolenoid.Value.kForward);
    }

    public void hatchRelease() {
        hatchGrabber.set(DoubleSolenoid.Value.kReverse);
    }

    public void grabberExtend() {
        grabberExtender.set(DoubleSolenoid.Value.kForward);
    }

    public void grabberRetract() {
        grabberExtender.set(DoubleSolenoid.Value.kReverse);
    }

    public void toggleHatch() {
        if(hatchGrabberActivated()) {
            hatchRelease();
        } else {
            hatchGrab();
        }
    }
    
    public boolean hatchGrabberActivated() {
        return hatchGrabber.get() == DoubleSolenoid.Value.kForward;
    }

    public boolean hatchGrabberExtended() {
        return grabberExtender.get() == DoubleSolenoid.Value.kForward;
    }

    @Override
    public void initDefaultCommand() {}
}
