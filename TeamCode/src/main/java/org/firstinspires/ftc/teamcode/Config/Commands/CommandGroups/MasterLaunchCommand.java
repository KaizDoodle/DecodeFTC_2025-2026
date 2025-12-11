package org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ManualCageControlCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ManualResetCommand;
import org.firstinspires.ftc.teamcode.Config.Core.Util.ShooterPosition;
import org.firstinspires.ftc.teamcode.Config.Subsystems.LimeLightSubsystem;
import org.firstinspires.ftc.teamcode.Config.Subsystems.ShooterSubsystem;

import java.util.function.Supplier;

public class MasterLaunchCommand extends SequentialCommandGroup {
    public MasterLaunchCommand(ShooterSubsystem shooter,  ShooterPosition pos) {

        addCommands(
                new ManualCageControlCommand(shooter, pos),
                new WaitCommand(500),
                new ManualResetCommand(shooter, pos)
        );
    }
    public MasterLaunchCommand(ShooterSubsystem shooter,  ShooterPosition pos, boolean held) {
        if (held)
            addCommands(new ManualCageControlCommand(shooter, pos));
        else
            addCommands(new ManualResetCommand(shooter, pos));
    }
    public MasterLaunchCommand(ShooterSubsystem shooter, Supplier<ShooterPosition> posSupplier) {

        addCommands(
                new ManualCageControlCommand(shooter, posSupplier),
                new WaitCommand(500),
                new ManualResetCommand(shooter, posSupplier)
        );
    }
}