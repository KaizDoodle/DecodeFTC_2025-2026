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

    private final ShooterSubsystem shooter;
    private ShooterPosition pos;
    private Supplier<ShooterPosition> posSupplier;
    private final boolean doEndReset;

    // NORMAL LAUNCH (fixed position)
    public MasterLaunchCommand(ShooterSubsystem shooter, ShooterPosition pos) {
        this.shooter = shooter;
        this.pos = pos;
        this.doEndReset = true;

        addCommands(
                new ManualCageControlCommand(shooter, pos)
        );
        addRequirements(shooter);
    }

    // HELD MODE (unchanged behavior)
    public MasterLaunchCommand(ShooterSubsystem shooter, ShooterPosition pos, boolean held) {
        this.shooter = shooter;
        this.pos = pos;
        this.doEndReset = false; // IMPORTANT: no auto reset

        if (held)
            addCommands(new ManualCageControlCommand(shooter, pos));
        else
            addCommands(new ManualResetCommand(shooter, pos));
        addRequirements(shooter);

    }

    // NORMAL LAUNCH (supplier)
    public MasterLaunchCommand(ShooterSubsystem shooter, Supplier<ShooterPosition> posSupplier) {
        this.shooter = shooter;
        this.posSupplier = posSupplier;
        this.doEndReset = true;

        addCommands(
                new ManualCageControlCommand(shooter, posSupplier)
        );
        addRequirements(shooter);

    }

    @Override
    public void end(boolean interrupted) {
        if (!doEndReset || interrupted) return;

        if (posSupplier != null) {
            new SequentialCommandGroup(
                    new WaitCommand(400),
                    new ManualResetCommand(shooter, posSupplier)
            ).schedule();
        } else if (pos != null) {
            new SequentialCommandGroup(
                    new WaitCommand(400),
                    new ManualResetCommand(shooter, pos)
            ).schedule();

        }
        return;
    }
}