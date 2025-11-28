package org.firstinspires.ftc.teamcode.OpModes.Testing;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

@Configurable
@Autonomous(name = "RPMTest", group = "Examples")
public class RPMtest extends LinearOpMode {


    private DcMotorEx shooterOne, shooterTwo, shooterThree;
    public static double testF = 13;
    public static double testP = 20 ;
    public static double testI = 0.05 ;
    public static double testD = 4 ;


    @Override
    public void runOpMode() throws InterruptedException {
        shooterOne = hardwareMap.get(DcMotorEx.class, "shooterOne");
        shooterTwo = hardwareMap.get(DcMotorEx.class, "shooterTwo");
        shooterThree = hardwareMap.get(DcMotorEx.class, "shooterThree");


        shooterOne.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(testP,testI,testD, testF));
        shooterTwo.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(testP,testI, testD, testF));
        shooterThree.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(testP,testI,testD, testF));

        shooterOne.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        shooterTwo.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        shooterThree.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("rpm", shooterOne.getVelocity());
            telemetry.addData("rpm", shooterTwo.getVelocity());
            telemetry.addData("rpm", shooterThree.getVelocity());
            telemetry.update();


            shooterOne.setVelocity(1200);
            shooterTwo.setVelocity(1200);
            shooterThree.setVelocity(1200);


        }
    }

}