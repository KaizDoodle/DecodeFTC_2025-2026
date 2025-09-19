package org.firstinspires.ftc.teamcode.OpModes.Testing;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Config.Core.Util.Alliance;
import org.firstinspires.ftc.teamcode.Config.Core.Util.OpModeCommand;
import org.firstinspires.ftc.teamcode.Config.Core.RobotContainer;

@Autonomous(name = "AutoTest", group = "Examples")
public class AutoTest extends OpModeCommand {

    PathChain lineFollower;

    RobotContainer robot;
    @Override
    public void init() {

        robot = new RobotContainer(hardwareMap, new Pose(0, 0, 0), Alliance.BLUE);
        schedule(
                new RunCommand(robot::periodic),
                new SequentialCommandGroup(

                )
        );
    }

    @Override
    public void loop() {


    }

    @Override
    public void initialize() {

    }
}