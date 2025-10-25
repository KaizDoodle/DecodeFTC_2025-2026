package org.firstinspires.ftc.teamcode.Config.Core.Paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import org.firstinspires.ftc.teamcode.Config.Core.RobotContainer;
import org.firstinspires.ftc.teamcode.Config.Core.Util.Alliance;

public class TestPathAuto {
    private final Follower follower;

    public Pose start = new Pose(56, 8, Math.toRadians(90));
    public Pose linePickUp1 = new Pose(45, 36, Math.toRadians(0));
    public Pose pickUp1 = new Pose(12, 36, Math.toRadians(0));

    public Pose farScore = new Pose(60, 20, Math.toRadians(115));

    public Pose linePickUp2 = new Pose(45, 63, Math.toRadians(0));
    public Pose pickUp2 = new Pose(12, 56, Math.toRadians(0));

    public Pose shortScore = new Pose(56, 80, Math.toRadians(130));

    public Pose linePickUp3 = new Pose(45, 84, Math.toRadians(0));
    public Pose pickUp3 = new Pose(18, 84, Math.toRadians(0));



    public TestPathAuto(RobotContainer robotContainer) {
        this.follower = robotContainer.follower;

        if (robotContainer.alliance.equals(Alliance.RED)) {
            start = start.mirror();
            linePickUp1 = linePickUp1.mirror();
            pickUp1 = pickUp1.mirror();
            linePickUp2 = linePickUp2.mirror();
            pickUp2 = pickUp2.mirror();
            linePickUp3 = linePickUp3.mirror();
            pickUp3 = pickUp3.mirror();
            farScore = farScore.mirror();
            shortScore = shortScore.mirror();
        }
    }

    public PathChain shootPreload() {
        return follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                start,
                                farScore
                        )
                )
                .setLinearHeadingInterpolation(start.getHeading(), farScore.getHeading())
                .build();
    }

    public PathChain linePickUp1() {
        return follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                start,
                                linePickUp1
                        )
                )
                .setLinearHeadingInterpolation(farScore.getHeading(), linePickUp1.getHeading())
                .build();
    }

    public PathChain pickUp1() {
        return follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                linePickUp1,
                                pickUp1
                        )
                )
                .setLinearHeadingInterpolation(linePickUp1.getHeading(), pickUp1.getHeading())
                .build();
    }

    public PathChain score1() {
        return follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                pickUp1,
                                farScore
                        )
                )
                .setLinearHeadingInterpolation(pickUp1.getHeading(), farScore.getHeading())
                .build();
    }

    public PathChain linePickUp2() {
        return follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                farScore,
                                linePickUp2
                        )
                )
                .setLinearHeadingInterpolation(farScore.getHeading(), linePickUp2.getHeading())
                .build();
    }

    public PathChain pickUp2() {
        return follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                linePickUp2,
                                pickUp2
                        )
                )
                .setLinearHeadingInterpolation(linePickUp2.getHeading(), pickUp2.getHeading())
                .build();
    }

    public PathChain shortScore() {
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

    public PathChain linePickUp3() {
        return follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                shortScore,
                                linePickUp3
                        )
                )
                .setLinearHeadingInterpolation(shortScore.getHeading(), linePickUp3.getHeading())
                .build();
    }

    public PathChain pickUp3() {
        return follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                linePickUp3,
                                pickUp3
                        )
                )
                .setLinearHeadingInterpolation(linePickUp3.getHeading(), pickUp3.getHeading())
                .build();
    }

    public PathChain shortScore2() {
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

}