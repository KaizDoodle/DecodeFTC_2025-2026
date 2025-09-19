package org.firstinspires.ftc.teamcode.Config.Core.Paths;

import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathBuilder;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;

public class TestPathAuto {

    public static Pose pose1 = new Pose(5, 0 ,Math.toRadians(0));
    public static Pose pose2 = new Pose(5, 5 ,Math.toRadians(90));

    public static PathChain score1() {
        return new PathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(pose1),
                                new Point(pose2)
                        )
                )
                .setLinearHeadingInterpolation(pose1.getHeading(), pose2.getHeading())
                .setZeroPowerAccelerationMultiplier(5)
                .build();
    }
}