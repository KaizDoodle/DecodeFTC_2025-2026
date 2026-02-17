package org.firstinspires.ftc.teamcode.Config.AutoBases;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups.MasterLaunchCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups.ResetAllCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups.WaitUntilLaunchCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.FollowPathCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.IntakeControlCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ManualCageControlCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ManualResetCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ShooterControllerCommand;
import org.firstinspires.ftc.teamcode.Config.Core.Paths.AutoClosePath18;
import org.firstinspires.ftc.teamcode.Config.Core.RobotContainer;
import org.firstinspires.ftc.teamcode.Config.Core.Util.Alliance;
import org.firstinspires.ftc.teamcode.Config.Core.Util.OpModeCommand;
import org.firstinspires.ftc.teamcode.Config.Core.Util.ShooterPosition;


public abstract class AutoCloseBase18 extends OpModeCommand {

    RobotContainer robotContainer;
    AutoClosePath18 auto;
    public abstract Alliance getAlliance();
    double shotVelocity = 0.6;
    double preloadShotVelocity = 1;
    @Override
    public void initialize() {
        reset();
        Alliance alliance = getAlliance();

        robotContainer = new RobotContainer(hardwareMap, alliance, telemetry);

        auto = new AutoClosePath18(robotContainer.driveSubsystem.getFollower(), alliance);
        robotContainer.startAuto(auto.start);


        schedule(
                new RunCommand(robotContainer :: aPeriodic),
                new SequentialCommandGroup(
                        // --- SHOOT PRELOAD ---
                        new ShooterControllerCommand(robotContainer.shooterSubsystem, preloadShotVelocity),
                        new ParallelCommandGroup(
                                new FollowPathCommand(robotContainer.driveSubsystem.getFollower(), auto.next()),
                                new SequentialCommandGroup(
                                        new WaitCommand(750),
                                        new MasterLaunchCommand(robotContainer.shooterSubsystem, ShooterPosition.ALL),
                                        new ShooterControllerCommand(robotContainer.shooterSubsystem, 0),
                                        new ManualCageControlCommand(robotContainer.shooterSubsystem, ShooterPosition.INTAKE),
                                        new IntakeControlCommand(robotContainer.intakeSubsystem, 1)
                                        )
                        ),

                        // --- SCORE AGAIN ---
                        new ShooterControllerCommand(robotContainer.shooterSubsystem, shotVelocity),
                        new ParallelCommandGroup(
                                new FollowPathCommand(robotContainer.driveSubsystem.getFollower(), auto.next()),
                                new SequentialCommandGroup(
                                        new WaitCommand(600),
                                        new ManualResetCommand(robotContainer.shooterSubsystem, ShooterPosition.INTAKE),
                                        new WaitCommand(300),
                                        new IntakeControlCommand(robotContainer.intakeSubsystem, -1)
                                )
                        ),
                        new WaitUntilLaunchCommand(robotContainer.shooterSubsystem, shotVelocity),
                        new ShooterControllerCommand(robotContainer.shooterSubsystem, 0),

                        // --- FIRST PICKUP GATE
                        new IntakeControlCommand(robotContainer.intakeSubsystem, 1),
                        new FollowPathCommand(robotContainer.driveSubsystem.getFollower(), auto.next()),
                        new WaitCommand(1000),

                        // --- SCORE AGAIN X2 ---
                        new ShooterControllerCommand(robotContainer.shooterSubsystem, shotVelocity),
                        new IntakeControlCommand(robotContainer.intakeSubsystem, -1),
                        new ManualResetCommand(robotContainer.shooterSubsystem, ShooterPosition.INTAKE),
                        new FollowPathCommand(robotContainer.driveSubsystem.getFollower(), auto.next()),
                        new WaitUntilLaunchCommand(robotContainer.shooterSubsystem, shotVelocity),
                        new ShooterControllerCommand(robotContainer.shooterSubsystem, 0),

                        // --- SECOND PICKUP GATE
                        new IntakeControlCommand(robotContainer.intakeSubsystem, 1),
                        new FollowPathCommand(robotContainer.driveSubsystem.getFollower(), auto.next()),
                        new WaitCommand(1000),

                        // --- SCORE AGAIN X3 ---
                        new ShooterControllerCommand(robotContainer.shooterSubsystem, shotVelocity),
                        new IntakeControlCommand(robotContainer.intakeSubsystem, -1),
                        new ManualResetCommand(robotContainer.shooterSubsystem, ShooterPosition.INTAKE),
                        new FollowPathCommand(robotContainer.driveSubsystem.getFollower(), auto.next()),
                        new WaitUntilLaunchCommand(robotContainer.shooterSubsystem, shotVelocity),
                        new ShooterControllerCommand(robotContainer.shooterSubsystem, 0),

                        // --- THRID PICKUP
                        new IntakeControlCommand(robotContainer.intakeSubsystem, 1),
                        new FollowPathCommand(robotContainer.driveSubsystem.getFollower(), auto.next()),

                        // --- SCORE AGAIN X4 ---
                        new ShooterControllerCommand(robotContainer.shooterSubsystem, shotVelocity),
                        new ParallelCommandGroup(
                                new FollowPathCommand(robotContainer.driveSubsystem.getFollower(), auto.next()),
                                new SequentialCommandGroup(
                                        new WaitCommand(600),
                                        new ManualResetCommand(robotContainer.shooterSubsystem, ShooterPosition.INTAKE),
                                        new WaitCommand(300),
                                        new IntakeControlCommand(robotContainer.intakeSubsystem, -1)
                                )
                        ),
                        new WaitUntilLaunchCommand(robotContainer.shooterSubsystem, shotVelocity),
                        new ShooterControllerCommand(robotContainer.shooterSubsystem, 0),

                        // --- FOURTH PICKUP
                        new IntakeControlCommand(robotContainer.intakeSubsystem, 1),
                        new FollowPathCommand(robotContainer.driveSubsystem.getFollower(), auto.next()),

                        // --- SCORE AGAIN X5 ---
                        new ShooterControllerCommand(robotContainer.shooterSubsystem, shotVelocity),
                        new ParallelCommandGroup(
                                new FollowPathCommand(robotContainer.driveSubsystem.getFollower(), auto.next()),
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
                        new FollowPathCommand(robotContainer.driveSubsystem.getFollower(), auto.next())

                )
        );
    }

}


