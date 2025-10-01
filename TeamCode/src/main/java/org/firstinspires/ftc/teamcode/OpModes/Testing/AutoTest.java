package org.firstinspires.ftc.teamcode.OpModes.Testing;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Config.Core.Util.Alliance;
import org.firstinspires.ftc.teamcode.Config.Core.Util.OpModeCommand;
import org.firstinspires.ftc.teamcode.Config.Core.RobotContainer;

@Autonomous(name = "AutoTest", group = "Examples")
public class AutoTest extends OpModeCommand {


    RobotContainer robot;
    @Override
    public void init() {

        robot = new RobotContainer(hardwareMap, Alliance.BLUE);
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