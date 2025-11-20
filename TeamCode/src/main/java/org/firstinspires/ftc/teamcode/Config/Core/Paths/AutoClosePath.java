package org.firstinspires.ftc.teamcode.Config.Core.Paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.Config.Core.RobotContainer;
import org.firstinspires.ftc.teamcode.Config.Core.Util.Alliance;


public class AutoClosePath {
    private final Follower follower;

    // robot lined up facing the goal, left side to the crevice of the goal and the ramp
    public static Pose start = new Pose(19, 121, Math.toRadians(144));

    public static Pose linePickUp1 = new Pose(50, 84, Math.toRadians(0));
    public static Pose pickUp1 = new Pose(18, 84, Math.toRadians(0));

    public static Pose linePickUp2 = new Pose(50, 60, Math.toRadians(0));
    public static Pose pickUp2 = new Pose(12, 60, Math.toRadians(0));

    public static Pose linePickUp3 = new Pose(50, 36, Math.toRadians(0));
    public static Pose pickUp3 = new Pose(12, 36, Math.toRadians(0));

    public static Pose driveOutOfBox = new Pose(24, 72, Math.toRadians(90));

    public static Pose shortScore = new Pose(47, 98, Math.toRadians(138));




    public AutoClosePath(Follower follower, Alliance alliance) {
        this.follower = follower;

        if (alliance == Alliance.RED) {
            start = start.mirror();
            linePickUp1 = linePickUp1.mirror();
            pickUp1 = pickUp1.mirror();
            linePickUp2 = linePickUp2.mirror();
            pickUp2 = pickUp2.mirror();
            linePickUp3 = linePickUp3.mirror();
            pickUp3 = pickUp3.mirror();
            shortScore = shortScore.mirror();
            driveOutOfBox = driveOutOfBox.mirror();
        }
    }

    public PathChain shootPreload() {
        return follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                start,
                                shortScore
                        )
                )
                .setLinearHeadingInterpolation(start.getHeading(), shortScore.getHeading())
                .build();
    }

    public PathChain pickUp1() {
        return follower.pathBuilder()
                .addPath(new BezierLine(shortScore, linePickUp1))
                .setLinearHeadingInterpolation(shortScore.getHeading(), linePickUp1.getHeading())

                .addPath(new BezierLine(linePickUp1, pickUp1))
                .setConstantHeadingInterpolation(linePickUp1.getHeading())
                .build();
    }

    public PathChain score1() {
        return follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                pickUp1,
                                shortScore
                        )
                )
                .setLinearHeadingInterpolation(pickUp1.getHeading(), shortScore.getHeading())
                .build();
    }

    public PathChain pickUp2() {
        return follower.pathBuilder()
                .addPath(new BezierLine(shortScore, linePickUp2))
                .setLinearHeadingInterpolation(shortScore.getHeading(), linePickUp2.getHeading())

                .addPath(new BezierLine(linePickUp2, pickUp2))
                .setConstantHeadingInterpolation(linePickUp2.getHeading())
                .build();
    }

    public PathChain score2() {
        return follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                pickUp2,
                                shortScore
                        )
                )
                .setLinearHeadingInterpolation(pickUp2.getHeading(), shortScore.getHeading())
                .build();
    }

    public PathChain pickUp3() {
        return follower.pathBuilder()
                .addPath(new BezierLine(shortScore, linePickUp3))
                .setLinearHeadingInterpolation(shortScore.getHeading(), linePickUp3.getHeading())

                .addPath(new BezierLine(linePickUp3, pickUp3))
                .setConstantHeadingInterpolation(linePickUp3.getHeading())
                .build();
    }

    public PathChain score3() {
        return follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                pickUp3,
                                shortScore
                        )
                )
                .setLinearHeadingInterpolation(pickUp3.getHeading(), shortScore.getHeading())
                .build();
    }

    public PathChain outOfBox() {
        return follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                shortScore,
                                driveOutOfBox
                        )
                )
                .setLinearHeadingInterpolation(shortScore.getHeading(), driveOutOfBox.getHeading())
                .build();
    }

}