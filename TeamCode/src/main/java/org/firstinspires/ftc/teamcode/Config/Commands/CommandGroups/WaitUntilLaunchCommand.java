package org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.Config.Core.RobotContainer;
import org.firstinspires.ftc.teamcode.Config.Subsystems.ShooterSubsystem;

public class WaitUntilLaunchCommand extends SequentialCommandGroup {

    // SORTED CONSTRUCTOR: Refreshes ball color data and then fires
    public WaitUntilLaunchCommand(RobotContainer robot, ShooterSubsystem shooter, double targetSpeed) {
        addCommands(
                // 1. Get the latest color/sequence data right before shooting
                new InstantCommand(robot::refreshShootingData),

                // 2. Wait until wheels are up to speed
                new WaitUntilCommand(() -> shooter.atVelocity(targetSpeed)),

                // 3. Fire in sequence with a larger delay
                new StaggeredShotCommand(shooter, () -> 500, robot.getSequence(), true)
        );
    }

    // NON SORTED CONSTRUCTOR: Just waits for speed and fires
    public WaitUntilLaunchCommand(ShooterSubsystem shooter, double targetSpeed) {
        addCommands(
                new WaitUntilCommand(() -> shooter.atVelocity(targetSpeed)),

                // Fire with a tighter delay and stay in firing position (false)
                new StaggeredShotCommand(shooter, () -> 250)
        );
    }
}