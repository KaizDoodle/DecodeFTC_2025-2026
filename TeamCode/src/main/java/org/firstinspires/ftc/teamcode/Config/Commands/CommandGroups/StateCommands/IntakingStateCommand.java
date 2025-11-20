package org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups.StateCommands;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;

import org.firstinspires.ftc.teamcode.Config.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Config.Subsystems.LMECSubsystem;
import org.firstinspires.ftc.teamcode.Config.Subsystems.ShooterSubsystem;

public class IntakingStateCommand extends ParallelCommandGroup {

    public IntakingStateCommand(IntakeSubsystem intake, ShooterSubsystem shooter, LMECSubsystem lmec) {

            shooter.setShooterSpeed(0);
            lmec.unlockMechanum();
//            intake.intakeOn();
    }
}