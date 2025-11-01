package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

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

@Autonomous

public class Preload extends OpModeCommand {

    RobotContainer robotContainer;


    @Override
    public void initialize() {
        robotContainer = new RobotContainer(hardwareMap, Alliance.BLUE);
        robotContainer.limeLightSubsystem.limeLightStart();


        TestPathAuto auto = new TestPathAuto(robotContainer);
        robotContainer.follower.setStartingPose(auto.start);

        new ManualResetCommand(robotContainer.shooterSubsystem, ShooterPosition.ALL);





        // Main autonomous sequence
        schedule(
                new RunCommand(robotContainer::aPeriodic),
                new SequentialCommandGroup(
                        // --- SHOOT PRELOAD ---
                        new ShooterControllerCommand(robotContainer.shooterSubsystem, 1),
                        new WaitCommand(4000),
                        new MasterLaunchCommand(robotContainer.shooterSubsystem, ShooterPosition.ALL),
                        new WaitCommand(1500),
                        new FollowPathCommand(robotContainer.follower, auto.shootPreload()),

                        // 0 Everything
                        new ResetAllCommand(robotContainer.shooterSubsystem, robotContainer.intakeSubsystem)
                )
        );

    }
    @Override
    public void run(){
        super.run();
    }

}
