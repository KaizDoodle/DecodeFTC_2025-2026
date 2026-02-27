package org.firstinspires.ftc.teamcode.Config.AutoBases;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.pedropathing.follower.Follower;

import org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups.ResetAllCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups.WaitUntilLaunchCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.FollowPathCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.IntakeControlCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ManualCageControlCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ManualResetCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ShooterControllerCommand;
import org.firstinspires.ftc.teamcode.Config.Core.Paths.AKIFIRSTAUTOPATH;
import org.firstinspires.ftc.teamcode.Config.Core.Paths.AutoClosePath;
import org.firstinspires.ftc.teamcode.Config.Core.RobotContainer;
import org.firstinspires.ftc.teamcode.Config.Core.Util.Alliance;
import org.firstinspires.ftc.teamcode.Config.Core.Util.OpModeCommand;
import org.firstinspires.ftc.teamcode.Config.Core.Util.ShooterPosition;


public abstract class otameshi extends OpModeCommand {

    RobotContainer robotContainer;
    AKIFIRSTAUTOPATH auto;
    public abstract Alliance getAlliance();


    @Override
    public void initialize() {
        reset();
        Alliance alliance = getAlliance();

        robotContainer = new RobotContainer(hardwareMap, alliance, telemetry);

        auto = new AKIFIRSTAUTOPATH(robotContainer.driveSubsystem.getFollower(), alliance);
        robotContainer.startAuto(auto.start);

        schedule(
                new SequentialCommandGroup(
                        new FollowPathCommand(robotContainer.driveSubsystem.getFollower(), auto.next())

                )
        );
    }

}


