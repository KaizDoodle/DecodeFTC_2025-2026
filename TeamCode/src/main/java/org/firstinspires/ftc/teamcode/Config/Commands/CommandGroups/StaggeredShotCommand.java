package org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ManualCageControlCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ManualResetCommand;
import org.firstinspires.ftc.teamcode.Config.Core.Util.ShooterPosition;
import org.firstinspires.ftc.teamcode.Config.Subsystems.LimeLightSubsystem;
import org.firstinspires.ftc.teamcode.Config.Subsystems.ShooterSubsystem;

public class StaggeredShotCommand extends SequentialCommandGroup {
    public StaggeredShotCommand(ShooterSubsystem shooter, LimeLightSubsystem limeLightSubsystem){
        double wait = 5 * limeLightSubsystem.getDistance();

        addCommands(
                new MasterLaunchCommand(shooter, ShooterPosition.LEFT ),
                new WaitCommand((long)wait),
                new MasterLaunchCommand(shooter, ShooterPosition.MIDDLE),
                new WaitCommand((long)wait),
                new MasterLaunchCommand(shooter, ShooterPosition.RIGHT)
        );
    }
    public StaggeredShotCommand(ShooterSubsystem shooter, LimeLightSubsystem limeLightSubsystem, ShooterPosition[] pattern){
        double wait = 5 * limeLightSubsystem.getDistance();

        addCommands(
                new MasterLaunchCommand(shooter, pattern[0]),
                new WaitCommand((long)wait),
                new MasterLaunchCommand(shooter, pattern[1]),
                new WaitCommand((long)wait),
                new MasterLaunchCommand(shooter, pattern[2])
        );
    }
}
