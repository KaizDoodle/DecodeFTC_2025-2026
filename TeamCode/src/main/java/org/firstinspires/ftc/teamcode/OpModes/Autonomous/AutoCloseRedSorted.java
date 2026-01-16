package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups.ResetAllCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups.WaitUntilLaunchCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.FollowPathCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.IntakeControlCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ManualCageControlCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ManualResetCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ShooterControllerCommand;
import org.firstinspires.ftc.teamcode.Config.Core.Paths.AutoClosePath;
import org.firstinspires.ftc.teamcode.Config.Core.Paths.AutoClosePathSorted;
import org.firstinspires.ftc.teamcode.Config.Core.RobotContainer;
import org.firstinspires.ftc.teamcode.Config.Core.Util.Alliance;
import org.firstinspires.ftc.teamcode.Config.Core.Util.OpModeCommand;
import org.firstinspires.ftc.teamcode.Config.Core.Util.ShooterPosition;
@Disabled
@Autonomous
public class AutoCloseRedSorted extends OpModeCommand {

    RobotContainer robotContainer;
    AutoClosePathSorted auto;

    double shotVelocity = 0.54;

    @Override
    public void initialize() {
        robotContainer = new RobotContainer(hardwareMap, Alliance.RED, telemetry);
        robotContainer.limeLightSubsystem.limeLightStart();

        auto = new AutoClosePathSorted(robotContainer.follower, Alliance.RED);
        robotContainer.aStart(auto.start);
        new ManualResetCommand(robotContainer.shooterSubsystem, ShooterPosition.ALL);

        schedule(
                new RunCommand(robotContainer :: aPeriodic),

                new SequentialCommandGroup(
                        // --- SHOOT PRELOAD ---
                        new ShooterControllerCommand(robotContainer.shooterSubsystem, shotVelocity),
                        new FollowPathCommand(robotContainer.follower, auto.next()),
                        new WaitUntilLaunchCommand(robotContainer, robotContainer.shooterSubsystem, shotVelocity, robotContainer.getTelemetry()),

//                        // --- READ TAG ---
//                        new FollowPathCommand(robotContainer.follower, auto.next()),
//                        new WaitCommand(400),

                        // --- DRIVE TO FIRST PICKUP (continuous line→pickup) ---
                        new ManualCageControlCommand(robotContainer.shooterSubsystem, ShooterPosition.INTAKE),
                        new IntakeControlCommand(robotContainer.intakeSubsystem, 1),
                        new FollowPathCommand(robotContainer.follower, auto.next()),
                        new FollowPathCommand(robotContainer.follower, auto.next()), // dump gate

                        // --- SCORE AGAIN ---
                        new FollowPathCommand(robotContainer.follower, auto.next()),
                        new ManualResetCommand(robotContainer.shooterSubsystem, ShooterPosition.INTAKE),
                        new IntakeControlCommand(robotContainer.intakeSubsystem, -0.5),
                        new WaitUntilLaunchCommand(robotContainer, robotContainer.shooterSubsystem, shotVelocity, robotContainer.getTelemetry()),

                        // --- SECOND PICKUP (continuous line→pickup) ---
                        new ManualCageControlCommand(robotContainer.shooterSubsystem, ShooterPosition.INTAKE),
                        new IntakeControlCommand(robotContainer.intakeSubsystem, 1),
                        new FollowPathCommand(robotContainer.follower, auto.next()),

                        // --- SCORE AGAIN X2 ---
                        new FollowPathCommand(robotContainer.follower, auto.next()),
                        new ManualResetCommand(robotContainer.shooterSubsystem, ShooterPosition.INTAKE),
                        new IntakeControlCommand(robotContainer.intakeSubsystem, -0.5),
                        new WaitUntilLaunchCommand(robotContainer, robotContainer.shooterSubsystem, shotVelocity, robotContainer.getTelemetry()),

                        // --- THRID PICKUP (continuous line→pickup) ---
                        new ManualCageControlCommand(robotContainer.shooterSubsystem, ShooterPosition.INTAKE),
                        new IntakeControlCommand(robotContainer.intakeSubsystem, 1),
                        new FollowPathCommand(robotContainer.follower, auto.next()),

                        // --- SCORE AGAIN X3 ---
                        new FollowPathCommand(robotContainer.follower, auto.next()),
                        new ManualResetCommand(robotContainer.shooterSubsystem, ShooterPosition.INTAKE),
                        new IntakeControlCommand(robotContainer.intakeSubsystem, -0.5),
                        new WaitUntilLaunchCommand(robotContainer, robotContainer.shooterSubsystem, shotVelocity, robotContainer.getTelemetry()),

                        // 0 Everything + Drive out box
                        new ResetAllCommand(robotContainer.shooterSubsystem, robotContainer.intakeSubsystem),
                        new FollowPathCommand(robotContainer.follower, auto.next())

                )
        );
    }

}


