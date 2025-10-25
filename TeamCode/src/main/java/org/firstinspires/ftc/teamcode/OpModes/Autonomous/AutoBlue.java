package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups.MasterLaunchCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ManualLaunchCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ManualResetCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ShooterControllerCommand;
import org.firstinspires.ftc.teamcode.Config.Core.RobotContainer;
import org.firstinspires.ftc.teamcode.Config.Core.Util.Alliance;
import org.firstinspires.ftc.teamcode.Config.Core.Util.OpModeCommand;
import org.firstinspires.ftc.teamcode.Config.Core.Paths.TestPathAuto;
import org.firstinspires.ftc.teamcode.Config.Core.Util.ShooterPosition;
import org.firstinspires.ftc.teamcode.Config.Subsystems.LimeLightSubsystem;

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
                        new ShooterControllerCommand(robotContainer.shooterSubsystem, new LimeLightSubsystem(hardwareMap)),
                        new WaitCommand(5000),
                        new ManualLaunchCommand(robotContainer.shooterSubsystem, ShooterPosition.ALL),
                        new WaitCommand(3000),
                        new ShooterControllerCommand(robotContainer.shooterSubsystem, 0)
                    // this is just shoots and stops shooter
                        // @TODO figure out how to make commands for the paths, check baron's code

                )

        );

    }

}
