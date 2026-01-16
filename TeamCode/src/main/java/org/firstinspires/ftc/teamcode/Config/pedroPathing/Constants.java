package org.firstinspires.ftc.teamcode.Config.pedroPathing;

import com.pedropathing.control.FilteredPIDFCoefficients;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.constants.PinpointConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {
    public static FollowerConstants followerConstants = new FollowerConstants()
            .mass(13.7)
            .forwardZeroPowerAcceleration(-38)
            .lateralZeroPowerAcceleration(-64)

            .translationalPIDFCoefficients(new PIDFCoefficients(0.15,0,0.01,0.03))
            .headingPIDFCoefficients(new PIDFCoefficients(2,0,0.1,0.02))
            .drivePIDFCoefficients(new FilteredPIDFCoefficients(0.05,0,0.0001,0.6,0.03))

            .centripetalScaling(0.00025);


    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .pathConstraints(pathConstraints)
                .mecanumDrivetrain(driveConstants)
                .pinpointLocalizer(localizerConstants)
                .build();
    }

    public static PathConstraints pathConstraints = new PathConstraints(0.99, 100, 1, 1);


    public static PinpointConstants localizerConstants = new PinpointConstants()
            .forwardPodY(1.125)
            .strafePodX(5.4375)
            .distanceUnit(DistanceUnit.INCH)
            .hardwareMapName("odo")
            .customEncoderResolution(2.9)
            .forwardEncoderDirection(GoBildaPinpointDriver.EncoderDirection.REVERSED)
            .strafeEncoderDirection(GoBildaPinpointDriver.EncoderDirection.REVERSED);


    public static MecanumConstants driveConstants = new MecanumConstants()
            .maxPower(1)
            .rightFrontMotorName("frontRight")
            .rightRearMotorName("backRight")
            .leftRearMotorName("backLeft")
            .leftFrontMotorName("frontLeft")
            .leftFrontMotorDirection(DcMotor.Direction.FORWARD)
            .leftRearMotorDirection(DcMotor.Direction.FORWARD)
            .rightFrontMotorDirection(DcMotor.Direction.REVERSE)
            .rightRearMotorDirection(DcMotor.Direction.REVERSE)
            .useBrakeModeInTeleOp(true)
            .xVelocity(75)
            .yVelocity(49);
}
