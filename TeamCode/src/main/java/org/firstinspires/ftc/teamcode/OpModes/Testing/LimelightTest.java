package org.firstinspires.ftc.teamcode.OpModes.Testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Config.Core.RobotContainer;
import org.firstinspires.ftc.teamcode.Config.Core.Util.Alliance;
import org.firstinspires.ftc.teamcode.Config.Core.Util.OpModeCommand;

@Autonomous(name = "LimeLightTest", group = "Examples")

public class LimelightTest extends OpModeCommand {
    RobotContainer robot;

    @Override
    public void init() {
        robot = new RobotContainer(hardwareMap, gamepad1, gamepad2, Alliance.BLUE, telemetry);

    }

    @Override
    public void loop() {
        telemetry.addData("asd", robot.limeLightSubsystem.getDistance() );
        telemetry.update();
    }

    @Override
    public void initialize() {
        robot.tStart();

    }
}
