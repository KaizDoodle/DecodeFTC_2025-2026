package org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.Config.Commands.Custom.IntakeControlCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ManualResetCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ShooterControllerCommand;
import org.firstinspires.ftc.teamcode.Config.Core.Util.ShooterPosition;
import org.firstinspires.ftc.teamcode.Config.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Config.Subsystems.ShooterSubsystem;

public class ResetAllCommand extends SequentialCommandGroup {

    public ResetAllCommand(ShooterSubsystem shooter, IntakeSubsystem intake) {
        addCommands(
                new SequentialCommandGroup(
                        new ManualResetCommand(shooter, ShooterPosition.ALL),
                        new ShooterControllerCommand(shooter, 0),
                        new IntakeControlCommand(intake, 0)
                )
        );

        addRequirements(shooter, intake);
    }
}
