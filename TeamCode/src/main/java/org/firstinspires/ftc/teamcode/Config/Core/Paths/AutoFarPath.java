package org.firstinspires.ftc.teamcode.Config.Core.Paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.Config.Core.Util.Alliance;


public class AutoFarPath {
    private final Follower follower;

    // robot lined up to the edge of the mat
    public Pose start = new Pose(57, 9, Math.toRadians(90));

    public Pose lineUpPickUpPreload = new Pose(13, 20, Math.toRadians(15));
    public Pose pickUpPreloadAngled = new Pose(12.5, 12.5, Math.toRadians(15));
    public Pose lineUpToRam = new Pose(9.5, 20, Math.toRadians(90));
    public Pose ram = new Pose(9.5, 12, Math.toRadians(90));

    public Pose transitionScoreFromPickUpPreload = new Pose(50,20,Math.toRadians(100));

    public Pose driveOutOfBox = new Pose(48,32 , Math.toRadians(180));

    public Pose farScore = new Pose(58, 20, Math.toRadians(111));
    private int index = 0;




    public AutoFarPath(Follower follower, Alliance alliance) {
        this.follower = follower;

        if (alliance == Alliance.RED) {
            start = start.mirror();
            farScore = farScore.mirror();
            driveOutOfBox = driveOutOfBox.mirror();

            lineUpPickUpPreload = lineUpPickUpPreload.mirror();
            pickUpPreloadAngled = pickUpPreloadAngled.mirror();
            lineUpToRam = lineUpToRam.mirror();
            ram = ram.mirror();
            transitionScoreFromPickUpPreload = transitionScoreFromPickUpPreload.mirror();
        }
    }

    public PathChain lineUpPickUpPreload() {
        return follower.pathBuilder()
                .addPath(new BezierLine(farScore, lineUpPickUpPreload))
                .setLinearHeadingInterpolation(farScore.getHeading(), lineUpPickUpPreload.getHeading())
                .build();
    }

    public PathChain pickUpPreload() {
        return follower.pathBuilder()
                .addPath(new BezierLine(lineUpPickUpPreload, pickUpPreloadAngled))
                .setLinearHeadingInterpolation(lineUpPickUpPreload.getHeading(), pickUpPreloadAngled.getHeading())
                .build();
    }

    public PathChain lineUpToRam() {
        return follower.pathBuilder()
                .addPath(new BezierLine(pickUpPreloadAngled, lineUpToRam))
                .setLinearHeadingInterpolation(pickUpPreloadAngled.getHeading(), lineUpToRam.getHeading())
                .build();
    }

    public PathChain ram() {
        return follower.pathBuilder()
                .addPath(new BezierLine(lineUpToRam, ram))
                .setLinearHeadingInterpolation(lineUpToRam.getHeading(), ram.getHeading())
                .build();
    }

    public PathChain goToTransPoses() {
        return follower.pathBuilder()
                .addPath(new BezierLine(ram, transitionScoreFromPickUpPreload))
                .setLinearHeadingInterpolation(ram.getHeading(), transitionScoreFromPickUpPreload.getHeading())
                .build();
    }

    public PathChain scorePickedUpPreload() {
        return follower.pathBuilder()
                .addPath(new BezierLine(transitionScoreFromPickUpPreload, farScore))
                .setLinearHeadingInterpolation(transitionScoreFromPickUpPreload.getHeading(), farScore.getHeading())
                .build();
    }

    public PathChain shootPreload() {
        return follower.pathBuilder()
                .addPath(new BezierLine(start, farScore))
                .setLinearHeadingInterpolation(start.getHeading(), farScore.getHeading())
                .build();
    }


    public PathChain outOfBox() {
        return follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                farScore,
                                driveOutOfBox
                        )
                )
                .setLinearHeadingInterpolation(farScore.getHeading(), driveOutOfBox.getHeading())
                .build();
    }

//    public PathChain next() {
//        switch (index++) {
//            case 0: return shootPreload();
//            case 1: return pickUp1();
//            case 2: return score1();
//            case 3: return pickUp2();
//            case 4: return score2();
//            case 5: return pickUp3();
//            case 6: return score3();
//            case 7: return outOfBox();
//            default: return null;
//        }
//    }

    public PathChain next() {
        switch (index++) {
            case 0: return shootPreload();
            case 1: return lineUpPickUpPreload();
            case 2: return pickUpPreload();
            case 3: return lineUpToRam();
            case 4: return ram();
            case 5: return goToTransPoses();
            case 6: return scorePickedUpPreload();
            case 7: return outOfBox();
            default: return null;
        }
    }
}