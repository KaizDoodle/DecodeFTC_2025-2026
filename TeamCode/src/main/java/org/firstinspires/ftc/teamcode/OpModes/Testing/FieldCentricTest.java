package org.firstinspires.ftc.teamcode.OpModes.Testing;

import org.firstinspires.ftc.teamcode.Config.Core.Util.Alliance;
import org.firstinspires.ftc.teamcode.Config.Core.RobotContainer;
import org.firstinspires.ftc.teamcode.Config.Core.Util.OpModeCommand;

import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp
public class FieldCentricTest extends OpModeCommand {

    RobotContainer robot;

    @Override
    public void initialize() {
        reset();
        robot = new RobotContainer(hardwareMap, gamepad1, gamepad2, Alliance.BLUE, telemetry);
        robot.teleOpControl();
    }

    @Override
    public void start(){
        robot.tStart();
    }

    @Override
    public void loop() {
        robot.periodic();
    }




}