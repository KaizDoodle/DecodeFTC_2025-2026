package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Config.Core.RobotContainer;
import org.firstinspires.ftc.teamcode.Config.Core.Util.Alliance;

/**
 * Driver-controlled period, Red alliance. Field-centric driving with vision-assisted aiming.
 * All behaviour lives in {@link RobotContainer}; button bindings are set in
 * {@link RobotContainer#teleOpControl()}.
 */
@TeleOp(name = "TeleOp Red", group = "Match")
public class TeleOpRed extends OpMode {

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
        robot.startTeleOp();
    }
}
