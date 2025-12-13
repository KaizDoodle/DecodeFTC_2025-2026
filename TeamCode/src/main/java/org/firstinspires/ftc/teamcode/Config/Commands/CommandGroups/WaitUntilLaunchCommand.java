package org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ManualCageControlCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ManualResetCommand;
import org.firstinspires.ftc.teamcode.Config.Core.RobotContainer;
import org.firstinspires.ftc.teamcode.Config.Core.Util.ShooterPosition;
import org.firstinspires.ftc.teamcode.Config.Subsystems.ShooterSubsystem;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public class WaitUntilLaunchCommand extends SequentialCommandGroup {

    public WaitUntilLaunchCommand(RobotContainer robot, ShooterSubsystem shooter, Supplier<ShooterPosition[]> pos, double targetSpeed) {

        addCommands(
                new InstantCommand(robot::refreshShootingData),
                // Wait until shooter reaches target velocity
                new WaitUntilCommand(() -> shooter.atVelocity(targetSpeed)),
                // Fire the cage
                new StaggeredShotCommand(shooter, () -> 300 ,  pos)
        );
    }
    public WaitUntilLaunchCommand(ShooterSubsystem shooter,  double targetSpeed) {


        addCommands(
                // Wait until shooter reaches target velocity
                new WaitUntilCommand(() -> shooter.atVelocity(targetSpeed)),

                // Fire the cage
                new StaggeredShotCommand(shooter, () -> 300  )
        );
    }
}
