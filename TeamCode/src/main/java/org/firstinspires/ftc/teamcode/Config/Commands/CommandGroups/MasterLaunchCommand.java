package org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ManualLaunchCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ManualResetCommand;
import org.firstinspires.ftc.teamcode.Config.Core.Util.ShooterPosition;
import org.firstinspires.ftc.teamcode.Config.Subsystems.ShooterSubsystem;

public class MasterLaunchCommand extends SequentialCommandGroup {
    public MasterLaunchCommand(ShooterSubsystem shooter, ShooterPosition pos) {
            addCommands(
                    new ManualLaunchCommand(shooter, pos),
                    new WaitCommand(500),
                    new ManualResetCommand(shooter, pos)
            );
    }
}
