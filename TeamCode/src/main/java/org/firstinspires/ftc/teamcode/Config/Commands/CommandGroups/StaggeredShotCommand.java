package org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ManualCageControlCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ManualResetCommand;
import org.firstinspires.ftc.teamcode.Config.Core.Util.ShooterPosition;
import org.firstinspires.ftc.teamcode.Config.Subsystems.LimeLightSubsystem;
import org.firstinspires.ftc.teamcode.Config.Subsystems.ShooterSubsystem;

import java.util.function.DoubleSupplier;

public class StaggeredShotCommand extends SequentialCommandGroup {
    public StaggeredShotCommand(ShooterSubsystem shooter, DoubleSupplier time){




        addCommands(
                new ManualCageControlCommand(shooter, ShooterPosition.LEFT),
                new WaitCommand((long) time.getAsDouble()),
                new ManualCageControlCommand(shooter, ShooterPosition.MIDDLE),
                new WaitCommand((long) time.getAsDouble()),
                new ManualCageControlCommand(shooter, ShooterPosition.RIGHT),
                new WaitCommand((long) time.getAsDouble())
        );

    }
    public StaggeredShotCommand(ShooterSubsystem shooter, LimeLightSubsystem limeLightSubsystem, ShooterPosition[] pattern){
        long  wait = (long) (5 * limeLightSubsystem.getDistance());

        addCommands(
                new MasterLaunchCommand(shooter, pattern[0]),
                new WaitCommand(wait),
                new MasterLaunchCommand(shooter, pattern[1]),
                new WaitCommand(wait),
                new MasterLaunchCommand(shooter, pattern[2])
        );
    }
}