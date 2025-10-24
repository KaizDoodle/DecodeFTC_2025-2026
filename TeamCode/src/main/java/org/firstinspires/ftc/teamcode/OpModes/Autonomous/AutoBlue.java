package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups.MasterLaunchCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ManualResetCommand;
import org.firstinspires.ftc.teamcode.Config.Core.RobotContainer;
import org.firstinspires.ftc.teamcode.Config.Core.Util.Alliance;
import org.firstinspires.ftc.teamcode.Config.Core.Util.OpModeCommand;
import org.firstinspires.ftc.teamcode.Config.Core.Paths.TestPathAuto;
import org.firstinspires.ftc.teamcode.Config.Core.Util.ShooterPosition;

public class AutoBlue extends OpModeCommand {

    RobotContainer robotContainer;


    @Override
    public void initialize() {
        robotContainer = new RobotContainer(hardwareMap, Alliance.BLUE);

        TestPathAuto auto = new TestPathAuto(robotContainer);
        robotContainer.follower.setStartingPose(auto.start);

        new ManualResetCommand(robotContainer.shooterSubsystem, ShooterPosition.ALL);


        schedule(
                new RunCommand(robotContainer::periodic),
                new SequentialCommandGroup(
                    // shoot all
                    // drive to line up

                )

        );

    }

}
