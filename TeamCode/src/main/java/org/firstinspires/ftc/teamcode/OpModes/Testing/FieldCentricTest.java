package org.firstinspires.ftc.teamcode.OpModes.Testing;

import org.firstinspires.ftc.teamcode.Config.Core.Util.Alliance;
import org.firstinspires.ftc.teamcode.Config.Core.RobotContainer;

import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp
public class FieldCentricTest extends OpMode {

    RobotContainer robot;


    @Override
    public void init() {
        robot = new RobotContainer(hardwareMap, gamepad1, gamepad2, Alliance.BLUE, telemetry);
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