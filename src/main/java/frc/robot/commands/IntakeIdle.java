package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import static frc.robot.Robot.wideintake;

public class IntakeIdle extends Command {
    
    public IntakeIdle() {
        requires(wideintake);
    }

    @Override
    protected void execute() {
  
        wideintake.intakeStop();

    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}
