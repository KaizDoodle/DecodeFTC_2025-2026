package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups.MasterLaunchCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups.ResetAllCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.FollowPathCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.IntakeControlCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ManualLaunchCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ManualResetCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ShooterControllerCommand;
import org.firstinspires.ftc.teamcode.Config.Core.RobotContainer;
import org.firstinspires.ftc.teamcode.Config.Core.Util.Alliance;
import org.firstinspires.ftc.teamcode.Config.Core.Util.OpModeCommand;
import org.firstinspires.ftc.teamcode.Config.Core.Paths.TestPathAuto;
import org.firstinspires.ftc.teamcode.Config.Core.Util.ShooterPosition;
import org.firstinspires.ftc.teamcode.Config.Subsystems.LimeLightSubsystem;
import org.firstinspires.ftc.teamcode.Config.Subsystems.ShooterSubsystem;

public class AutoBlue extends OpModeCommand {

    RobotContainer robotContainer;


    @Override
    public void initialize() {
        robotContainer = new RobotContainer(hardwareMap, Alliance.BLUE);

        TestPathAuto auto = new TestPathAuto(robotContainer);
        robotContainer.follower.setStartingPose(auto.start);

        new ManualResetCommand(robotContainer.shooterSubsystem, ShooterPosition.ALL);


        schedule(new RunCommand(robotContainer::periodic));

        // Main autonomous sequence
        schedule(
                new SequentialCommandGroup(
                        // --- SHOOT PRELOAD ---
                        new ShooterControllerCommand(robotContainer.shooterSubsystem, new LimeLightSubsystem(hardwareMap)),
                        new FollowPathCommand(robotContainer.follower, auto.shootPreload()),
                        new WaitCommand(4000),
                        new MasterLaunchCommand(robotContainer.shooterSubsystem, ShooterPosition.ALL),
                        new WaitCommand(1500),

                        // --- DRIVE TO FIRST PICKUP (continuous line→pickup) ---
                        new IntakeControlCommand(robotContainer.intakeSubsystem, 1),
                        new FollowPathCommand(robotContainer.follower, auto.pickUp1()),

                        // --- SCORE AGAIN ---
                        new FollowPathCommand(robotContainer.follower, auto.score1()),
                        new IntakeControlCommand(robotContainer.intakeSubsystem, 0),
                        new ShooterControllerCommand(robotContainer.shooterSubsystem, new LimeLightSubsystem(hardwareMap)),
                        new WaitCommand(3000),
                        new MasterLaunchCommand(robotContainer.shooterSubsystem, ShooterPosition.ALL),
                        new WaitCommand(1500),

                        // --- SECOND PICKUP (continuous line→pickup) ---
                        new IntakeControlCommand(robotContainer.intakeSubsystem, 1),
                        new FollowPathCommand(robotContainer.follower, auto.pickUp2()),

                        // --- SCORE AGAIN X2 ---
                        new FollowPathCommand(robotContainer.follower, auto.score2()),
                        new IntakeControlCommand(robotContainer.intakeSubsystem, 0),
                        new ShooterControllerCommand(robotContainer.shooterSubsystem, new LimeLightSubsystem(hardwareMap)),
                        new WaitCommand(3000),
                        new MasterLaunchCommand(robotContainer.shooterSubsystem, ShooterPosition.ALL),
                        new WaitCommand(1500),

                        // --- THRID PICKUP (continuous line→pickup) ---
                        new IntakeControlCommand(robotContainer.intakeSubsystem, 1),
                        new FollowPathCommand(robotContainer.follower, auto.pickUp3()),

                        // --- SCORE AGAIN X3 ---
                        new FollowPathCommand(robotContainer.follower, auto.score3()),
                        new IntakeControlCommand(robotContainer.intakeSubsystem, 0),
                        new ShooterControllerCommand(robotContainer.shooterSubsystem, new LimeLightSubsystem(hardwareMap)),
                        new WaitCommand(3000),
                        new MasterLaunchCommand(robotContainer.shooterSubsystem, ShooterPosition.ALL),
                        new WaitCommand(1500),

                        // 0 Everything
                        new ResetAllCommand(robotContainer.shooterSubsystem, robotContainer.intakeSubsystem)
                )
        );

    }

}
