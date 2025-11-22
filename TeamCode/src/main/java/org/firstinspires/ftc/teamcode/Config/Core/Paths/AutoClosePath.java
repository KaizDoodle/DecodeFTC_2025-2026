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
    private final Alliance alliance;

    // robot lined up facing the goal, left side to the crevice of the goal and the ramp
    public Pose start = new Pose(19, 121, Math.toRadians(144));

    public Pose linePickUp1 = new Pose(50, 84, Math.toRadians(0));
    public Pose transition1 = new Pose (40,90, Math.toRadians(120));
    public Pose pickUp1 = new Pose(18, 84, Math.toRadians(0));

    public Pose linePickUp2 = new Pose(50, 60.5, Math.toRadians(0));
    public Pose transition2 = new Pose (47,60.5, Math.toRadians(138));
    public Pose pickUp2 = new Pose(13, 60.5, Math.toRadians(0));

    public Pose linePickUp3 = new Pose(50, 39.5, Math.toRadians(0));
    public Pose transition3 = new Pose (40,90, Math.toRadians(120));
    public Pose pickUp3 = new Pose(15, 39.5, Math.toRadians(0));

    public Pose driveOutOfBox = new Pose(30, 72, Math.toRadians(180));

    public Pose shortScore = new Pose(47, 98, Math.toRadians(138));
    private int index = 0;



    public AutoClosePath(Follower follower, Alliance alliance) {
        this.follower = follower;
        this.alliance = alliance;

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
    public Pose returnStart(){
        return start;
    }

    public PathChain shootPreload() {
        return follower.pathBuilder()
                .addPath(new BezierLine(start, shortScore))
                .setLinearHeadingInterpolation(start.getHeading(), shortScore.getHeading())
                .build();
    }

    public PathChain pickUp1() {
        return follower.pathBuilder()
                .addPath(new BezierLine(shortScore, linePickUp1))
                .setLinearHeadingInterpolation(shortScore.getHeading(), linePickUp1.getHeading())

                .addPath(new BezierLine(linePickUp1, pickUp1))
                .setLinearHeadingInterpolation(linePickUp1.getHeading(), pickUp1.getHeading())
                .build();
    }

    public PathChain score1() {
        return follower.pathBuilder()
                .addPath(new BezierLine(pickUp1, transition1))
                .setLinearHeadingInterpolation(pickUp1.getHeading(), transition1.getHeading())

                .addPath(new BezierLine(transition1, shortScore))
                .setLinearHeadingInterpolation(transition1.getHeading(), shortScore.getHeading())
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
                .addPath(new BezierLine(pickUp2, transition2))
                .setLinearHeadingInterpolation(pickUp2.getHeading(), transition2.getHeading())

                .addPath(new BezierLine(transition2, shortScore))
                .setLinearHeadingInterpolation(transition2.getHeading(), shortScore.getHeading())
                .build();
    }

    public PathChain pickUp3() {
        return follower.pathBuilder()
                .addPath(new BezierLine(shortScore, linePickUp3))
                .setLinearHeadingInterpolation(shortScore.getHeading(), linePickUp3.getHeading())

                .addPath(new BezierLine(linePickUp3, pickUp3))
                .setLinearHeadingInterpolation(linePickUp3.getHeading(), pickUp3.getHeading())
                .build();
    }

    public PathChain score3() {
        return follower.pathBuilder()
                .addPath(new BezierLine(pickUp3, transition3))
                .setLinearHeadingInterpolation(pickUp3.getHeading(), transition3.getHeading())

                .addPath(new BezierLine(transition3, shortScore))
                .setLinearHeadingInterpolation(transition3.getHeading(), shortScore.getHeading())
                .build();
    }

    public PathChain outOfBox() {
        return follower.pathBuilder()
                .addPath(new BezierLine(shortScore, driveOutOfBox))
                .setLinearHeadingInterpolation(shortScore.getHeading(), driveOutOfBox.getHeading())
                .build();
    }
    public PathChain next() {
        switch (index++) {
            case 0: return shootPreload();
            case 1: return pickUp1();
            case 2: return score1();
            case 3: return pickUp2();
            case 4: return score2();
            case 5: return pickUp3();
            case 6: return score3();
            case 7: return outOfBox();
            default: return null;
        }
    }

}