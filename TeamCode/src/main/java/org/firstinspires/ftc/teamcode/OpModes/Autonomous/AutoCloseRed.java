package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups.MasterLaunchCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups.ResetAllCommand;
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
import org.firstinspires.ftc.teamcode.Config.Subsystems.ShooterSubsystem;

@Autonomous
public class AutoCloseRed extends OpModeCommand {

    RobotContainer robotContainer;


    @Override
    public void initialize() {
        robotContainer = new RobotContainer(hardwareMap, Alliance.RED, AutoClosePath.start, telemetry);
        robotContainer.limeLightSubsystem.limeLightStart();

        AutoClosePath auto = new AutoClosePath(robotContainer.follower, Alliance.RED);


        new ManualResetCommand(robotContainer.shooterSubsystem, ShooterPosition.ALL);

        // Main autonomous sequence
        schedule(
                new SequentialCommandGroup(
                        // --- SHOOT PRELOAD ---
                        new ShooterControllerCommand(robotContainer.shooterSubsystem, 0.53),
                        new FollowPathCommand(robotContainer.follower, auto.shootPreload()),
                        new WaitCommand(3000),
                        new MasterLaunchCommand(robotContainer.shooterSubsystem, ShooterPosition.ALL),
                        new WaitCommand(500),

                        // --- DRIVE TO FIRST PICKUP (continuous line→pickup) ---
                        new ManualCageControlCommand(robotContainer.shooterSubsystem, ShooterPosition.INTAKE),
                        new IntakeControlCommand(robotContainer.intakeSubsystem,  1),
                        new FollowPathCommand(robotContainer.follower, auto.pickUp1()),

                        // --- SCORE AGAIN ---
                        new FollowPathCommand(robotContainer.follower, auto.score1()),
                        new ManualResetCommand(robotContainer.shooterSubsystem, ShooterPosition.INTAKE),
                        new IntakeControlCommand(robotContainer.intakeSubsystem, 0),
                        new WaitCommand(500),
                        new MasterLaunchCommand(robotContainer.shooterSubsystem, ShooterPosition.ALL),
                        new WaitCommand(500),

                        // --- SECOND PICKUP (continuous line→pickup) ---
                        new ManualCageControlCommand(robotContainer.shooterSubsystem, ShooterPosition.INTAKE),
                        new IntakeControlCommand(robotContainer.intakeSubsystem,  1),
                        new FollowPathCommand(robotContainer.follower, auto.pickUp2()),

                        // --- SCORE AGAIN X2 ---
                        new FollowPathCommand(robotContainer.follower, auto.score2()),
                        new ManualResetCommand(robotContainer.shooterSubsystem, ShooterPosition.INTAKE),
                        new IntakeControlCommand(robotContainer.intakeSubsystem, 0),
                        new WaitCommand(500),
                        new MasterLaunchCommand(robotContainer.shooterSubsystem, ShooterPosition.ALL),
                        new WaitCommand(500),

                        // --- THRID PICKUP (continuous line→pickup) ---
                        new ManualCageControlCommand(robotContainer.shooterSubsystem, ShooterPosition.INTAKE),
                        new IntakeControlCommand(robotContainer.intakeSubsystem,  1),
                        new FollowPathCommand(robotContainer.follower, auto.pickUp3()),

                        // --- SCORE AGAIN X3 ---
                        new FollowPathCommand(robotContainer.follower, auto.score3()),
                        new ManualResetCommand(robotContainer.shooterSubsystem, ShooterPosition.INTAKE),
                        new IntakeControlCommand(robotContainer.intakeSubsystem, 0),
                        new WaitCommand(500),
                        new MasterLaunchCommand(robotContainer.shooterSubsystem, ShooterPosition.ALL),
                        new WaitCommand(500),

                        // 0 Everything + Drive out box
                        new ResetAllCommand(robotContainer.shooterSubsystem, robotContainer.intakeSubsystem),
                        new FollowPathCommand(robotContainer.follower, auto.outOfBox())

                )
        );

    }

}
