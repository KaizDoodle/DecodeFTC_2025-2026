package org.firstinspires.ftc.teamcode.Config.Core.Paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.Config.Core.Util.Alliance;


public class AutoFarPath {
    private final Follower follower;

    // robot lined up to the edge of the mat left side (for blue) including mat corners
    public Pose start = new Pose(57, 9, Math.toRadians(90));

    public Pose lineUpPickUpPreload = new Pose(14, 20, Math.toRadians(20));
    public Pose pickUpPreloadAngled = new Pose(14, 12, Math.toRadians(20));
    public Pose lineUpToRam = new Pose(11, 20, Math.toRadians(90));
    public Pose ram = new Pose(11, 16, Math.toRadians(90));
    public Pose pivot = new Pose(15, 16, Math.toRadians(90)); // too lazy to change variable name
    public Pose ramAgain = new Pose(11, 13, Math.toRadians(90));

    public Pose lineUpPickUp = new Pose(39, 15, Math.toRadians(0));
    public Pose pickUp = new Pose(16, 15, Math.toRadians(0));

    public Pose lineUpPickUp2 = new Pose(39, 22, Math.toRadians(0));
    public Pose pickUp2 = new Pose(16, 22, Math.toRadians(0));

    public Pose driveOutOfBox = new Pose(45,15 , Math.toRadians(165));

    public Pose farScore = new Pose(57, 19, Math.toRadians(104));

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
            pivot = pivot.mirror();
            ramAgain = ramAgain.mirror();

            lineUpPickUp = lineUpPickUp.mirror();
            pickUp = pickUp.mirror();

            lineUpPickUp2 = lineUpPickUp2.mirror();
            pickUp2 = pickUp2.mirror();
        }
    }

    public PathChain shootPreload() {
        return follower.pathBuilder()
                .addPath(new BezierLine(start, farScore))
                .setLinearHeadingInterpolation(start.getHeading(), farScore.getHeading())
                .build();
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

//    public PathChain ramAgain() {
//        return follower.pathBuilder()
//                .addPath(new BezierLine(pivot, ram))
//                .setLinearHeadingInterpolation(pivot.getHeading(), ram.getHeading())
//
//                .addPath(new BezierLine(ram, ramAgain))
//                .setLinearHeadingInterpolation(ram.getHeading(), ramAgain.getHeading())
//                .build();
//    }


    public PathChain scorePickedUpPreload() {
        return follower.pathBuilder()
                .addPath(new BezierLine(pickUpPreloadAngled, farScore))
                .setLinearHeadingInterpolation(pickUpPreloadAngled.getHeading(), farScore.getHeading())
                .build();
    }

    public PathChain pickUp1() {
        return follower.pathBuilder()
                .addPath(new BezierLine(farScore, lineUpPickUp))
                .setLinearHeadingInterpolation(farScore.getHeading(), lineUpPickUp.getHeading())

                .addPath(new BezierLine(lineUpPickUp, pickUp))
                .setLinearHeadingInterpolation(lineUpPickUp.getHeading(), pickUp.getHeading())
                .build();
    }


    public PathChain scorePickUp1() {
        return follower.pathBuilder()
                .addPath(new BezierLine(pickUp, farScore))
                .setLinearHeadingInterpolation(pickUp.getHeading(), farScore.getHeading())
                .build();
    }


    public PathChain pickUp2() {
        return follower.pathBuilder()
                .addPath(new BezierLine(farScore, lineUpPickUp2))
                .setLinearHeadingInterpolation(farScore.getHeading(), lineUpPickUp2.getHeading())

                .addPath(new BezierLine(lineUpPickUp2, pickUp2))
                .setLinearHeadingInterpolation(lineUpPickUp2.getHeading(), pickUp2.getHeading())
                .build();
    }

    public PathChain scorePickUp2() {
        return follower.pathBuilder()
                .addPath(new BezierLine(pickUp2, farScore))
                .setLinearHeadingInterpolation(pickUp2.getHeading(), farScore.getHeading())
                .build();
    }

    public PathChain outOfBox() {
        return follower.pathBuilder()
                .addPath(new BezierLine(farScore, driveOutOfBox))
                .setLinearHeadingInterpolation(farScore.getHeading(), driveOutOfBox.getHeading())
                .build();
    }

    public PathChain next() {
        switch (index++) {
            case 0: return shootPreload();
            case 1: return lineUpPickUpPreload();
            case 2: return pickUpPreload();
            case 3: return scorePickedUpPreload();
            case 4: return pickUp2();
            case 5: return scorePickUp2();
            case 6: return pickUp1();
            case 7: return scorePickUp1();
            case 8: return pickUp2();
            case 9: return scorePickUp2();
            case 10: return pickUp1();
            case 11: return scorePickUp1();
            case 12: return outOfBox();
            default: return null;
        }
    }
}