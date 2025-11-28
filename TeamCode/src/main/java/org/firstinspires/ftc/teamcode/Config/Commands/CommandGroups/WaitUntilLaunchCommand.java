package org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ManualCageControlCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ManualResetCommand;
import org.firstinspires.ftc.teamcode.Config.Core.Util.ShooterPosition;
import org.firstinspires.ftc.teamcode.Config.Subsystems.ShooterSubsystem;

public class WaitUntilLaunchCommand extends SequentialCommandGroup {

    public WaitUntilLaunchCommand(ShooterSubsystem shooter, ShooterPosition pos, double targetVelocity) {

        addCommands(
                // Wait until shooter reaches target velocity
                new WaitUntilCommand(() -> shooter.atVelocity(targetVelocity)),

                // Fire the cage
                new ManualCageControlCommand(shooter, pos),
                new WaitCommand(250),
                new ManualResetCommand(shooter, pos)
        );
    }
}
