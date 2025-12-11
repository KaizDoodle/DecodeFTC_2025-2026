package org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.Config.Commands.Custom.DynamicWaitCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ManualCageControlCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ManualResetCommand;
import org.firstinspires.ftc.teamcode.Config.Core.Util.ShooterPosition;
import org.firstinspires.ftc.teamcode.Config.Subsystems.LimeLightSubsystem;
import org.firstinspires.ftc.teamcode.Config.Subsystems.ShooterSubsystem;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public class StaggeredShotCommand extends SequentialCommandGroup {
    public StaggeredShotCommand(ShooterSubsystem shooter, DoubleSupplier time) {
        addCommands(
                new ParallelCommandGroup(
                        new SequentialCommandGroup(
                                new MasterLaunchCommand(shooter, ShooterPosition.LEFT)
                        ),
                        new SequentialCommandGroup(
                                new DynamicWaitCommand(time),
                                new MasterLaunchCommand(shooter, ShooterPosition.MIDDLE)
                        ),
                        new SequentialCommandGroup(
                                new DynamicWaitCommand(time),
                                new DynamicWaitCommand(time),
                                new MasterLaunchCommand(shooter, ShooterPosition.RIGHT
                                )
                        )
                )
        );
    }

    public StaggeredShotCommand(ShooterSubsystem shooter, DoubleSupplier time, Supplier<ShooterPosition[]> sequenceSupplier) {
        addCommands(
                new ParallelCommandGroup(
                        new SequentialCommandGroup(
                                new MasterLaunchCommand(shooter, sequenceSupplier.get()[0])
                        ),
                        new SequentialCommandGroup(
                                new DynamicWaitCommand(time),
                                new MasterLaunchCommand(shooter, sequenceSupplier.get()[1])
                        ),
                        new SequentialCommandGroup(
                                new DynamicWaitCommand(time),
                                new DynamicWaitCommand(time),
                                new MasterLaunchCommand(shooter, sequenceSupplier.get()[2])
                        )
                )
        );
    }
}