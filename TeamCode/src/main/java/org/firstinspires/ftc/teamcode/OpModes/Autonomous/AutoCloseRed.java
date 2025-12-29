package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups.MasterLaunchCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups.ResetAllCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups.WaitUntilLaunchCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.FollowPathCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.IntakeControlCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ManualCageControlCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ManualResetCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ShooterControllerCommand;
import org.firstinspires.ftc.teamcode.Config.Core.Paths.AutoClosePath;
import org.firstinspires.ftc.teamcode.Config.Core.RobotContainer;
import org.firstinspires.ftc.teamcode.Config.Core.Util.Alliance;
import org.firstinspires.ftc.teamcode.Config.Core.Util.OpModeCommand;
import org.firstinspires.ftc.teamcode.Config.Core.Paths.TestPathAuto;
import org.firstinspires.ftc.teamcode.Config.Core.Util.ShooterPosition;
import org.firstinspires.ftc.teamcode.Config.Subsystems.LimeLightSubsystem;
import org.firstinspires.ftc.teamcode.Config.Subsystems.ShooterSubsystem;

@Autonomous
public class AutoCloseRed extends OpModeCommand {

    RobotContainer robotContainer;
    AutoClosePath auto;

    double shotVelocity = 0.55;

    @Override
    public void initialize() {
        robotContainer = new RobotContainer(hardwareMap, Alliance.RED, telemetry);
        robotContainer.limeLightSubsystem.limeLightStart();

        auto = new AutoClosePath(robotContainer.follower, Alliance.RED);
        robotContainer.aStart(auto.start);

        schedule(
                new RunCommand(robotContainer :: aPeriodic),

                new SequentialCommandGroup(
                        // --- SHOOT PRELOAD ---
                        new ShooterControllerCommand(robotContainer.shooterSubsystem, shotVelocity),
                        new FollowPathCommand(robotContainer.follower, auto.next()),
                        new WaitUntilLaunchCommand(robotContainer.shooterSubsystem, shotVelocity),
                        new ShooterControllerCommand(robotContainer.shooterSubsystem, 0),

                        // --- DRIVE TO FIRST PICKUP
                        new ManualCageControlCommand(robotContainer.shooterSubsystem, ShooterPosition.INTAKE),
                        new IntakeControlCommand(robotContainer.intakeSubsystem, 1),
                        new FollowPathCommand(robotContainer.follower, auto.next()),

                        // --- SCORE AGAIN ---
                        new ShooterControllerCommand(robotContainer.shooterSubsystem, shotVelocity),
                        new ParallelCommandGroup(
                                new FollowPathCommand(robotContainer.follower, auto.next()),
                                new SequentialCommandGroup(
                                        new WaitCommand(900),
                                        new IntakeControlCommand(robotContainer.intakeSubsystem, -1)
                                )
                        ),
                        new ManualResetCommand(robotContainer.shooterSubsystem, ShooterPosition.INTAKE),
                        new WaitUntilLaunchCommand(robotContainer.shooterSubsystem, shotVelocity),
                        new ShooterControllerCommand(robotContainer.shooterSubsystem, 0),

                        // --- SECOND PICKUP GATE
                        new ManualCageControlCommand(robotContainer.shooterSubsystem, ShooterPosition.INTAKE),
                        new IntakeControlCommand(robotContainer.intakeSubsystem, 1),
                        new FollowPathCommand(robotContainer.follower, auto.next()),
                        new WaitCommand(2000),

                        // --- SCORE AGAIN X2 ---
                        new ShooterControllerCommand(robotContainer.shooterSubsystem, shotVelocity),
                        new IntakeControlCommand(robotContainer.intakeSubsystem, -1),
                        new FollowPathCommand(robotContainer.follower, auto.next()),
                        new ManualResetCommand(robotContainer.shooterSubsystem, ShooterPosition.INTAKE),
                        new WaitUntilLaunchCommand(robotContainer.shooterSubsystem, shotVelocity),
                        new ShooterControllerCommand(robotContainer.shooterSubsystem, 0),

                        // --- THRID PICKUP
                        new ManualCageControlCommand(robotContainer.shooterSubsystem, ShooterPosition.INTAKE),
                        new IntakeControlCommand(robotContainer.intakeSubsystem, 1),
                        new FollowPathCommand(robotContainer.follower, auto.next()),

                        // --- SCORE AGAIN X3 ---
                        new ShooterControllerCommand(robotContainer.shooterSubsystem, shotVelocity),
                        new ParallelCommandGroup(
                                new FollowPathCommand(robotContainer.follower, auto.next()),
                                new SequentialCommandGroup(
                                        new WaitCommand(900),
                                        new IntakeControlCommand(robotContainer.intakeSubsystem, -1)
                                )
                        ),
                        new ManualResetCommand(robotContainer.shooterSubsystem, ShooterPosition.INTAKE),
                        new WaitUntilLaunchCommand(robotContainer.shooterSubsystem, shotVelocity),
                        new ShooterControllerCommand(robotContainer.shooterSubsystem, 0),

                        // --- FOURTH PICKUP
                        new ManualCageControlCommand(robotContainer.shooterSubsystem, ShooterPosition.INTAKE),
                        new IntakeControlCommand(robotContainer.intakeSubsystem, 1),
                        new FollowPathCommand(robotContainer.follower, auto.next()),

                        // --- SCORE AGAIN X4 ---
                        new ShooterControllerCommand(robotContainer.shooterSubsystem, shotVelocity),
                        new ParallelCommandGroup(
                                new FollowPathCommand(robotContainer.follower, auto.next()),
                                new SequentialCommandGroup(
                                        new WaitCommand(500),
                                        new IntakeControlCommand(robotContainer.intakeSubsystem, -1)
                                )
                        ),
                        new ManualResetCommand(robotContainer.shooterSubsystem, ShooterPosition.INTAKE),
                        new WaitUntilLaunchCommand(robotContainer.shooterSubsystem, shotVelocity),
                        new ShooterControllerCommand(robotContainer.shooterSubsystem, 0),

                        // 0 Everything + Drive out box
                        new ResetAllCommand(robotContainer.shooterSubsystem, robotContainer.intakeSubsystem),
                        new ShooterControllerCommand(robotContainer.shooterSubsystem, 0),
                        new FollowPathCommand(robotContainer.follower, auto.next())

                )
        );
    }

}


