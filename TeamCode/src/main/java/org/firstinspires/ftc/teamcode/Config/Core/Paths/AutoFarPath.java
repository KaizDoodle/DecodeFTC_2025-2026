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

    public Pose lineUpPickUpPreload = new Pose(13, 20, Math.toRadians(7));
    public Pose pickUpPreloadAngled = new Pose(12.5, 12.5, Math.toRadians(7));
    public Pose lineUpToRam = new Pose(9.5, 20, Math.toRadians(90));
    public Pose ram = new Pose(9.5, 12, Math.toRadians(90));

    public Pose transitionScoreFromPickUpPreload = new Pose(50,20,Math.toRadians(100));

    public Pose linePickUp3 = new Pose(50, 84, Math.toRadians(0));
    public Pose transition3 = new Pose(58, 84, Math.toRadians(0));
    public Pose pickUp3 = new Pose(19, 84, Math.toRadians(0));

    public Pose linePickUp2 = new Pose(50, 60, Math.toRadians(0));
    public Pose pickUp2 = new Pose(13, 60, Math.toRadians(0));

    public Pose linePickUp1 = new Pose(50, 36, Math.toRadians(0));
    public Pose pickUp1 = new Pose(14, 36, Math.toRadians(0));

    public Pose driveOutOfBox = new Pose(48,32 , Math.toRadians(180));

    public Pose farScore = new Pose(58, 20, Math.toRadians(111));
    private int index = 0;




    public AutoFarPath(Follower follower, Alliance alliance) {
        this.follower = follower;

        if (alliance == Alliance.RED) {
            start = start.mirror();
            linePickUp1 = linePickUp1.mirror();
            pickUp1 = pickUp1.mirror();
            linePickUp2 = linePickUp2.mirror();
            pickUp2 = pickUp2.mirror();
            linePickUp3 = linePickUp3.mirror();
            pickUp3 = pickUp3.mirror();
            farScore = farScore.mirror();
            driveOutOfBox = driveOutOfBox.mirror();

            lineUpPickUpPreload = lineUpPickUpPreload.mirror();
            pickUpPreloadAngled = pickUpPreloadAngled.mirror();
            lineUpToRam = lineUpToRam.mirror();
            ram = ram.mirror();
        }
    }

    public PathChain pickUpPreload() {
        return follower.pathBuilder()
                .addPath(new BezierLine(farScore, lineUpPickUpPreload))
                .setLinearHeadingInterpolation(farScore.getHeading(), lineUpPickUpPreload.getHeading())

                .addPath(new BezierLine(lineUpPickUpPreload, pickUpPreloadAngled))
                .setLinearHeadingInterpolation(lineUpPickUpPreload.getHeading(), pickUpPreloadAngled.getHeading())

                .addPath(new BezierLine(pickUpPreloadAngled, lineUpToRam))
                .setLinearHeadingInterpolation(pickUpPreloadAngled.getHeading(), lineUpToRam.getHeading())

                .addPath(new BezierLine(lineUpToRam, ram))
                .setLinearHeadingInterpolation(lineUpToRam.getHeading(), ram.getHeading())
                .build();
    }

    public PathChain scorePickedUpPreload() {
        return follower.pathBuilder()
                .addPath(new BezierLine(ram, transitionScoreFromPickUpPreload))
                .setLinearHeadingInterpolation(ram.getHeading(), transitionScoreFromPickUpPreload.getHeading())
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

    public PathChain pickUp1() {
        return follower.pathBuilder()
                .addPath(new BezierLine(farScore, linePickUp1))
                .setLinearHeadingInterpolation(farScore.getHeading(), linePickUp1.getHeading())

                .addPath(new BezierLine(linePickUp1, pickUp1))
                .setConstantHeadingInterpolation(linePickUp1.getHeading())
                .build();
    }

    public PathChain score1() {
        return follower.pathBuilder()
                .addPath(new BezierLine(pickUp1, farScore))
                .setLinearHeadingInterpolation(pickUp1.getHeading(), farScore.getHeading())
                .build();
    }

    public PathChain pickUp2() {
        return follower.pathBuilder()
                .addPath(new BezierLine(farScore, linePickUp2))
                .setLinearHeadingInterpolation(farScore.getHeading(), linePickUp2.getHeading())

                .addPath(new BezierLine(linePickUp2, pickUp2))
                .setConstantHeadingInterpolation(linePickUp2.getHeading())
                .build();
    }

    public PathChain score2() {
        return follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                pickUp2,
                                farScore
                        )
                )
                .setLinearHeadingInterpolation(pickUp2.getHeading(), farScore.getHeading())
                .build();
    }

    public PathChain pickUp3() {
        return follower.pathBuilder()
                .addPath(new BezierLine(farScore, linePickUp3))
                .setLinearHeadingInterpolation(farScore.getHeading(), linePickUp3.getHeading())

                .addPath(new BezierLine(linePickUp3, pickUp3))
                .setConstantHeadingInterpolation(linePickUp3.getHeading())
                .build();
    }

    public PathChain score3() {
        return follower.pathBuilder()
                .addPath(new BezierLine(pickUp3, transition3))
                .setLinearHeadingInterpolation(pickUp3.getHeading(), transition3.getHeading())

                .addPath(new BezierLine(transition3, farScore))
                .setLinearHeadingInterpolation(transition3.getHeading(), farScore.getHeading())
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
            case 1: return pickUpPreload();
            case 2: return scorePickedUpPreload();
            case 3: return outOfBox();
            default: return null;
        }
    }
}