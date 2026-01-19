package org.firstinspires.ftc.teamcode.Config.AutoBases;

import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups.ResetAllCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups.WaitUntilLaunchCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.FollowPathCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.IntakeControlCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ManualCageControlCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ManualResetCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ShooterControllerCommand;
import org.firstinspires.ftc.teamcode.Config.Core.Paths.AutoClosePathSorted;
import org.firstinspires.ftc.teamcode.Config.Core.RobotContainer;
import org.firstinspires.ftc.teamcode.Config.Core.Util.Alliance;
import org.firstinspires.ftc.teamcode.Config.Core.Util.OpModeCommand;
import org.firstinspires.ftc.teamcode.Config.Core.Util.ShooterPosition;


public abstract class AutoCloseSorted12Base extends OpModeCommand {

    RobotContainer robotContainer;
    AutoClosePathSorted auto;
    public abstract Alliance getAlliance();

    double shotVelocity = 0.54;

    @Override
    public void initialize() {
        reset();
        Alliance alliance = getAlliance();

        robotContainer = new RobotContainer(hardwareMap, alliance, telemetry);
        robotContainer.limeLightSubsystem.limeLightStart();

        auto = new AutoClosePathSorted(robotContainer.follower, alliance);
        robotContainer.aStart(auto.start);
        new ManualResetCommand(robotContainer.shooterSubsystem, ShooterPosition.ALL);

        schedule(
                new RunCommand(robotContainer :: aPeriodic),

                new SequentialCommandGroup(
                        // --- SHOOT PRELOAD ---
                        new ShooterControllerCommand(robotContainer.shooterSubsystem, shotVelocity),
                        new FollowPathCommand(robotContainer.follower, auto.next()),
                        new WaitUntilLaunchCommand( robotContainer.shooterSubsystem, shotVelocity),

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
                        new WaitUntilLaunchCommand(robotContainer, robotContainer.shooterSubsystem, shotVelocity),

                        // --- SECOND PICKUP (continuous line→pickup) ---
                        new ManualCageControlCommand(robotContainer.shooterSubsystem, ShooterPosition.INTAKE),
                        new IntakeControlCommand(robotContainer.intakeSubsystem, 1),
                        new FollowPathCommand(robotContainer.follower, auto.next()),

                        // --- SCORE AGAIN X2 ---
                        new FollowPathCommand(robotContainer.follower, auto.next()),
                        new ManualResetCommand(robotContainer.shooterSubsystem, ShooterPosition.INTAKE),
                        new IntakeControlCommand(robotContainer.intakeSubsystem, -0.5),
                        new WaitUntilLaunchCommand(robotContainer, robotContainer.shooterSubsystem, shotVelocity),


                        // --- THRID PICKUP (continuous line→pickup) ---
                        new ManualCageControlCommand(robotContainer.shooterSubsystem, ShooterPosition.INTAKE),
                        new IntakeControlCommand(robotContainer.intakeSubsystem, 1),
                        new FollowPathCommand(robotContainer.follower, auto.next()),

                        // --- SCORE AGAIN X3 ---
                        new FollowPathCommand(robotContainer.follower, auto.next()),
                        new ManualResetCommand(robotContainer.shooterSubsystem, ShooterPosition.INTAKE),
                        new IntakeControlCommand(robotContainer.intakeSubsystem, -0.5),
                        new WaitUntilLaunchCommand(robotContainer, robotContainer.shooterSubsystem, shotVelocity),


                        // 0 Everything + Drive out box
                        new ResetAllCommand(robotContainer.shooterSubsystem, robotContainer.intakeSubsystem),
                        new FollowPathCommand(robotContainer.follower, auto.next())

                )
        );
    }

}


