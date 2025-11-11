package org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups.StateCommands;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ManualLaunchCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ManualResetCommand;
import org.firstinspires.ftc.teamcode.Config.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Config.Subsystems.LMECSubsystem;
import org.firstinspires.ftc.teamcode.Config.Subsystems.ShooterSubsystem;

public class IntakingStateCommand extends ParallelCommandGroup {

    public IntakingStateCommand(IntakeSubsystem intake, ShooterSubsystem shooter, LMECSubsystem lmec) {
        super(() -> {
            shooter.setShooterSpeed(0);
            lmec.open();
            intake.intakeOn();
        });
    }
}