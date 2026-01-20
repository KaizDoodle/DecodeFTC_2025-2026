package org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ManualCageControlCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ManualResetCommand;
import org.firstinspires.ftc.teamcode.Config.Core.Util.ShooterPosition;
import org.firstinspires.ftc.teamcode.Config.Subsystems.ShooterSubsystem;
import java.util.function.Supplier;

public class MasterLaunchCommand extends SequentialCommandGroup {

    // 1. Sorted shot
    public MasterLaunchCommand(ShooterSubsystem shooter, Supplier<ShooterPosition> posSupplier) {
        addCommands(
                new ManualCageControlCommand(shooter, posSupplier), // Open
                new WaitCommand(250),                               // Delay for ring
                new ManualResetCommand(shooter, posSupplier)        // Close
        );
//        addRequirements(shooter);
    }

    // 2. HELD CONSTRUCTOR (Manual Override)
    public MasterLaunchCommand(ShooterSubsystem shooter, ShooterPosition pos, boolean held) {
        if (held) {
            addCommands(new ManualCageControlCommand(shooter, pos));
        } else {
            addCommands(new ManualResetCommand(shooter, pos));
        }
//        addRequirements(shooter);
    }

    // Simple wrapper for non-supplier positions
    public MasterLaunchCommand(ShooterSubsystem shooter, ShooterPosition pos) {
        this(shooter, () -> pos);
    }
}