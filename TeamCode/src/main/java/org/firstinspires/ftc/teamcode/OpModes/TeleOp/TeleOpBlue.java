package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Config.Core.RobotContainer;
import org.firstinspires.ftc.teamcode.Config.Core.Util.Alliance;
import org.firstinspires.ftc.teamcode.Config.Core.Util.OpModeCommand;

/**
 * Driver-controlled period, Blue alliance. Field-centric driving with vision-assisted aiming.
 * All behaviour lives in {@link RobotContainer}; button bindings are set in
 * {@link RobotContainer#teleOpControl()}.
 */
@TeleOp(name = "TeleOp Blue", group = "Match")
public class TeleOpBlue extends OpModeCommand {

    public static double testF = 13;
    public static double testP = 20 ;
    public static double testI = 0.05 ;
    public static double testD = 4 ;
    RobotContainer robot;

    @Override
    public void initialize() {
        reset();
        robot = new RobotContainer(hardwareMap, gamepad1, gamepad2, Alliance.BLUE, telemetry);
        robot.teleOpControl();
    }

    @Override
    public void start(){
        robot.startTeleOp();
    }

    @Override
    public void loop() {
        robot.periodic();
    }
}
