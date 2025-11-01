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
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {
    public static FollowerConstants followerConstants = new FollowerConstants()
            .mass( 13)
            .forwardZeroPowerAcceleration(-40.69671127146222)
            .lateralZeroPowerAcceleration(-70.74379213578918)
            .translationalPIDFCoefficients(new PIDFCoefficients(0.075,0,0.01,0))
            .secondaryTranslationalPIDFCoefficients(new PIDFCoefficients(0.1,0,0.01,0))
            .headingPIDFCoefficients(new PIDFCoefficients(1,0,0.75,0))
            .secondaryHeadingPIDFCoefficients(new PIDFCoefficients(2,0,0.1,0))
            .drivePIDFCoefficients(new FilteredPIDFCoefficients(0.009,0,0.0001,0.6,0))
            .secondaryDrivePIDFCoefficients(new FilteredPIDFCoefficients(0.1,0,0,0.6,0))
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
            .forwardPodY(-0.5)
            .strafePodX(0.6875)
            .distanceUnit(DistanceUnit.INCH)
            .hardwareMapName("odo")
            .customEncoderResolution(74.54)
            .forwardEncoderDirection(GoBildaPinpointDriver.EncoderDirection.REVERSED)
            .strafeEncoderDirection(GoBildaPinpointDriver.EncoderDirection.REVERSED);


    public static MecanumConstants driveConstants = new MecanumConstants()
            .maxPower(1)
            .rightFrontMotorName("frontRight")
            .rightRearMotorName("backRight")
            .leftRearMotorName("backLeft")
            .leftFrontMotorName("frontLeft")
            .leftFrontMotorDirection(DcMotor.Direction.REVERSE)
            .leftRearMotorDirection(DcMotor.Direction.REVERSE)
            .rightFrontMotorDirection(DcMotor.Direction.FORWARD)
            .rightRearMotorDirection(DcMotor.Direction.FORWARD)
            .xVelocity(0)
            .yVelocity(0);
}
