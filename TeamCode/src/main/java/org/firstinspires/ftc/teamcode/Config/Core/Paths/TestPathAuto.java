package org.firstinspires.ftc.teamcode.Config.Core.Paths;

import com.pedropathing.geometry.Pose;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.paths.PathBuilder;
import com.pedropathing.paths.PathChain;
import com.pedropathing.paths.PathPoint;

public class TestPathAuto {

    public static Pose pose1 = new Pose(5, 0 ,Math.toRadians(0));
    public static Pose pose2 = new Pose(5, 5 ,Math.toRadians(90));

//    public static PathChain score1() {
//        return new PathBuilder()
//                .addPath(
//                        new BezierLine(
//                                new Pose(pose1),
//                                new Pose(pose2)
//                        )
//                )
//                .setLinearHeadingInterpolation(pose1.getHeading(), pose2.getHeading())
//                .setZeroPowerAccelerationMultiplier(5)
//                .build();
//    }
}