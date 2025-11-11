package org.firstinspires.ftc.teamcode.OpModes.Testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorControllerEx;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
@Autonomous(name = "RPMTest", group = "Examples")
public class RPMtest extends LinearOpMode {

    DcMotorEx motor;
    @Override
    public void runOpMode() throws InterruptedException {
        motor = hardwareMap.get(DcMotorImplEx.class, "intakeMotor");

    waitForStart();
    while (opModeIsActive()) {
        telemetry.addData("rpm", motor.getVelocity(AngleUnit.DEGREES));
        motor.setVelocity(300 * 384.5);
    }
    }

}
