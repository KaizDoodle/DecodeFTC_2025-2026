package org.firstinspires.ftc.teamcode.OpModes.Testing;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Config.Core.RobotContainer;
import org.firstinspires.ftc.teamcode.Config.Core.Util.Alliance;


@TeleOp
public class FieldCentricRed extends OpMode {

    RobotContainer robot;


    @Override
    public void init() {
        robot = new RobotContainer(hardwareMap, gamepad1, gamepad2, Alliance.RED, telemetry);
        robot.teleOpControl();
    }

    @Override
    public void loop() {

        robot.periodic();
    }
    @Override
    public void start(){
        robot.tStart();
    }
}