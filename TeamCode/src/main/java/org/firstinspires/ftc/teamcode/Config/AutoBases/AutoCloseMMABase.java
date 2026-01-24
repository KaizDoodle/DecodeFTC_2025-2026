package org.firstinspires.ftc.teamcode.Config.AutoBases;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups.ResetAllCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups.WaitUntilLaunchCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.FollowPathCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.IntakeControlCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ManualCageControlCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ManualResetCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ShooterControllerCommand;
import org.firstinspires.ftc.teamcode.Config.Core.Paths.AutoClosePath;
import org.firstinspires.ftc.teamcode.Config.Core.Paths.AutoClosePathMMA;
import org.firstinspires.ftc.teamcode.Config.Core.RobotContainer;
import org.firstinspires.ftc.teamcode.Config.Core.Util.Alliance;
import org.firstinspires.ftc.teamcode.Config.Core.Util.OpModeCommand;
import org.firstinspires.ftc.teamcode.Config.Core.Util.ShooterPosition;


public abstract class AutoCloseMMABase extends OpModeCommand {

    RobotContainer robotContainer;
    AutoClosePathMMA auto;
    public abstract Alliance getAlliance();
    double shotVelocity = 0.6;
    @Override
    public void initialize() {
        reset();
        Alliance alliance = getAlliance();

        robotContainer = new RobotContainer(hardwareMap, alliance, telemetry);

        auto = new AutoClosePathMMA(robotContainer.follower, alliance);
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
                        new IntakeControlCommand(robotContainer.intakeSubsystem, 1),
                        new FollowPathCommand(robotContainer.follower, auto.next()),

                        // --- SCORE AGAIN ---
                        new ShooterControllerCommand(robotContainer.shooterSubsystem, shotVelocity),
                        new ParallelCommandGroup(
                                new FollowPathCommand(robotContainer.follower, auto.next()),
                                new SequentialCommandGroup(
                                        new WaitCommand(600),
                                        new ManualResetCommand(robotContainer.shooterSubsystem, ShooterPosition.INTAKE),
                                        new WaitCommand(300),
                                        new IntakeControlCommand(robotContainer.intakeSubsystem, -1)
                                )
                        ),
                        new WaitUntilLaunchCommand(robotContainer.shooterSubsystem, shotVelocity),
                        new ShooterControllerCommand(robotContainer.shooterSubsystem, 0),

                        // ---  PICKUP GATE
                        new IntakeControlCommand(robotContainer.intakeSubsystem, 1),
                        new FollowPathCommand(robotContainer.follower, auto.next()),
                        new WaitCommand(1250),

                        // --- SCORE AGAIN X2 ---
                        new ShooterControllerCommand(robotContainer.shooterSubsystem, shotVelocity),
                        new IntakeControlCommand(robotContainer.intakeSubsystem, -1),
                        new ManualResetCommand(robotContainer.shooterSubsystem, ShooterPosition.INTAKE),
                        new FollowPathCommand(robotContainer.follower, auto.next()),
                        new WaitUntilLaunchCommand(robotContainer.shooterSubsystem, shotVelocity),
                        new ShooterControllerCommand(robotContainer.shooterSubsystem, 0),

                        // ---  SECOND PICKUP GATE
                        new IntakeControlCommand(robotContainer.intakeSubsystem, 1),
                        new FollowPathCommand(robotContainer.follower, auto.next()),
                        new WaitCommand(1250),

                        // --- SCORE AGAIN X3 ---
                        new ShooterControllerCommand(robotContainer.shooterSubsystem, shotVelocity),
                        new IntakeControlCommand(robotContainer.intakeSubsystem, -1),
                        new ManualResetCommand(robotContainer.shooterSubsystem, ShooterPosition.INTAKE),
                        new FollowPathCommand(robotContainer.follower, auto.next()),
                        new WaitUntilLaunchCommand(robotContainer.shooterSubsystem, shotVelocity),
                        new ShooterControllerCommand(robotContainer.shooterSubsystem, 0),

                        // --- FOURTH PICKUP
                        new IntakeControlCommand(robotContainer.intakeSubsystem, 1),
                        new FollowPathCommand(robotContainer.follower, auto.next()),

                        // --- SCORE AGAIN X4 ---
                        new ShooterControllerCommand(robotContainer.shooterSubsystem, shotVelocity),
                        new ParallelCommandGroup(
                                new FollowPathCommand(robotContainer.follower, auto.next()),
                                new SequentialCommandGroup(
                                        new WaitCommand(600),
                                        new ManualResetCommand(robotContainer.shooterSubsystem, ShooterPosition.INTAKE),
                                        new WaitCommand(300),
                                        new IntakeControlCommand(robotContainer.intakeSubsystem, -1)
                                )
                        ),
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


