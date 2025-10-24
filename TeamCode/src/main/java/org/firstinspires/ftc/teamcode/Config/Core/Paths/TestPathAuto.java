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

    public Pose start = new Pose(67, 12, Math.toRadians(-10));
    public Pose linePickUp1 = new Pose(96, 36, Math.toRadians(-90));
    public Pose pickUp1 = new Pose(120, 36, Math.toRadians(-90));
    public Pose score1 = new Pose(76, 12, Math.toRadians(-30));



    public TestPathAuto(RobotContainer robotContainer) {
        this.follower = robotContainer.follower;

        if (robotContainer.alliance.equals(Alliance.RED)) {
            start = start.mirror();
            linePickUp1 = linePickUp1.mirror();
            pickUp1 = pickUp1.mirror();
        }
    }

    public PathChain linePickUp1() {
        return follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                start,
                                linePickUp1
                        )
                )
                .setLinearHeadingInterpolation(start.getHeading(), linePickUp1.getHeading())
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
                                score1
                        )
                )
                .setLinearHeadingInterpolation(pickUp1.getHeading(), score1.getHeading())
                .build();
    }


}