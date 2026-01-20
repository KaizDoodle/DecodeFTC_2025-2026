package org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.DynamicWaitCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ManualCageControlCommand;
import org.firstinspires.ftc.teamcode.Config.Core.Util.ShooterPosition;
import org.firstinspires.ftc.teamcode.Config.Subsystems.ShooterSubsystem;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public class StaggeredShotCommand extends SequentialCommandGroup {

    // Main Constructor
    public StaggeredShotCommand(ShooterSubsystem shooter, DoubleSupplier delay, Supplier<ShooterPosition[]> seqSupplier, boolean isAuto) {
        addRequirements(shooter);
        // 1. Add the shots linearly (Fire-Reset -> Wait -> Fire-Reset -> Wait...)
        addCommands(
                new ParallelCommandGroup(
                        // Shot 1: Starts immediately
                        new MasterLaunchCommand(shooter, () -> seqSupplier.get()[0]),

                        // Shot 2: Starts after 'delayMillis'
                        new SequentialCommandGroup(
                                new DynamicWaitCommand(delay),
                                new MasterLaunchCommand(shooter, () -> seqSupplier.get()[1])
                        ),

                        // Shot 3: Starts after 'delayMillis * 2'
                        new SequentialCommandGroup(
                                new DynamicWaitCommand(() -> delay.getAsDouble() * 2),
                                new MasterLaunchCommand(shooter, () -> seqSupplier.get()[2])
                        )
                )
        );
        // 2. ONLY if in Auto, add the final return to Intake
        if (isAuto) {
            addCommands(
                    new WaitCommand(200), // Small buffer after last shot
                    new ManualCageControlCommand(shooter, ShooterPosition.INTAKE)
            );
        }
    }

    // TeleOp Constructor (Defaults isAuto to false)
    public StaggeredShotCommand(ShooterSubsystem shooter, DoubleSupplier time) {
        this(shooter, time, () -> new ShooterPosition[]{ShooterPosition.LEFT, ShooterPosition.MIDDLE, ShooterPosition.RIGHT}, true);
    }

    // Helper for supplier array
    private Supplier<ShooterPosition> indexClosure(Supplier<ShooterPosition[]> supplier, int index){
        return () -> supplier.get()[index];
    }
}