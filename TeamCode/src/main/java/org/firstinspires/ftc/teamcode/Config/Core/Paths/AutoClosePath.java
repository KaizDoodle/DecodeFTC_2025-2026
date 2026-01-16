package org.firstinspires.ftc.teamcode.Config.Core.Paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.Config.Core.Util.Alliance;

// USED FOR ALL 15 BALL AUTOS
public class AutoClosePath {
    private final Follower follower;
    private final Alliance alliance;

    // robot lined up facing the goal, side to the crevice of the goal and the ramp
    public Pose start = new Pose(19.5, 122, Math.toRadians(144));

    public Pose linePickUp1 = new Pose(39, 85, Math .toRadians(0)); // 50?? for x???
    public Pose pickUp1 = new Pose(22, 85, Math.toRadians(0));

    public Pose linePickUp2 = new Pose(39, 60.5, Math.toRadians(0));
    public Pose pickUp2 = new Pose(18, 60.5, Math.toRadians(0));

    public Pose linePickUp3 = new Pose(39, 38, Math.toRadians(0));
    public Pose pickUp3 = new Pose(21, 38, Math.toRadians(0));

    public Pose transition = new Pose (40,90, Math.toRadians(120));

    public Pose pickUpGate = new Pose(13, 62, Math.toRadians(-30));

    public Pose driveOutOfBox = new Pose(39, 83, Math.toRadians(175));

    public Pose shortScore = new Pose(47, 98, Math.toRadians(138));
    public Pose shortScore2 = new Pose(47, 98, Math.toRadians(123));
    public Pose shortScore3 = new Pose(47, 98, Math.toRadians(123));
    public Pose shortScore4 = new Pose(47, 98, Math.toRadians(135));


    public Pose ctrlPickUp1 = new Pose(60, 72);
    public Pose ctrlScore1  = new Pose(54, 63); // used for a lot of paths
    public Pose ctrlPickUp2 = new Pose(51, 92);
    public Pose ctrlScore2  = new Pose(45, 85);
    public Pose ctrlPickUp3 = new Pose(67, 58);

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
            shortScore2 = shortScore2.mirror();
            shortScore3 = shortScore3.mirror();
            shortScore4 = shortScore4.mirror();
            driveOutOfBox = driveOutOfBox.mirror();
            transition = transition.mirror();
            pickUpGate = pickUpGate.mirror();
            ctrlPickUp1 = ctrlPickUp1.mirror();
            ctrlScore1  = ctrlScore1.mirror();
            ctrlPickUp2 = ctrlPickUp2.mirror();
            ctrlScore2  = ctrlScore2.mirror();
            ctrlPickUp3 = ctrlPickUp3.mirror();

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


    public PathChain pickUp1() { // ik the numbers for pickup are weird, the method number is the sequence, the pose number is the spike mark
        return follower.pathBuilder()
                .addPath(new BezierCurve(shortScore, ctrlPickUp1, linePickUp2))
                .setLinearHeadingInterpolation(shortScore.getHeading(), linePickUp2.getHeading())

                .addPath(new BezierLine(linePickUp2, pickUp2))
                .setLinearHeadingInterpolation(linePickUp2.getHeading(), pickUp2.getHeading())
                .build();
    }


    public PathChain score1() {
        return follower.pathBuilder()
                .addPath(new BezierCurve(pickUp2, ctrlScore1, shortScore2))
                .setLinearHeadingInterpolation(pickUp2.getHeading(), shortScore2.getHeading())
                .build();
    }

    public PathChain pickUpGate() {
        return follower.pathBuilder()
                .addPath(new BezierCurve(shortScore2, ctrlScore1, pickUpGate))
                .setLinearHeadingInterpolation(shortScore2.getHeading(), pickUpGate.getHeading())
                .build();
    }

    public PathChain scoreGate() {
        return follower.pathBuilder()
                .addPath(new BezierCurve(pickUpGate, ctrlScore1, shortScore2))
                .setLinearHeadingInterpolation(pickUpGate.getHeading(), shortScore2.getHeading())
                .build();
    }

    public PathChain pickUp2() {
        return follower.pathBuilder()
                .addPath(new BezierCurve(shortScore2, ctrlPickUp2, linePickUp1))
                .setLinearHeadingInterpolation(shortScore2.getHeading(), linePickUp1.getHeading())

                .addPath(new BezierLine(linePickUp1, pickUp1))
                .setLinearHeadingInterpolation(linePickUp1.getHeading(), pickUp1.getHeading())
                .build();
    }

    public PathChain score2() {
        return follower.pathBuilder()
                .addPath(new BezierCurve(pickUp1, ctrlScore2, shortScore3))
                .setLinearHeadingInterpolation(pickUp1.getHeading(), shortScore3.getHeading())
                .build();
    }

    public PathChain pickUp3() {
        return follower.pathBuilder()
                .addPath(new BezierCurve(shortScore3, ctrlPickUp3, linePickUp3))
                .setLinearHeadingInterpolation(shortScore3.getHeading(), linePickUp3.getHeading())

                .addPath(new BezierLine(linePickUp3, pickUp3))
                .setLinearHeadingInterpolation(linePickUp3.getHeading(), pickUp3.getHeading())
                .build();
    }

    public PathChain score3() {
        return follower.pathBuilder()
                .addPath(new BezierLine(pickUp3, shortScore4))
                .setLinearHeadingInterpolation(pickUp3.getHeading(), shortScore4.getHeading())
                .build();
    }


    public PathChain outOfBox() {
        return follower.pathBuilder()
                .addPath(new BezierLine(shortScore4, driveOutOfBox))
                .setLinearHeadingInterpolation(shortScore4.getHeading(), driveOutOfBox.getHeading())
                .build();
    }
    public PathChain next() {
        switch (index++) {
            case 0: return shootPreload();
            case 1: return pickUp1();
            case 2: return score1();
            case 3: return pickUpGate();
            case 4: return scoreGate();
            case 5: return pickUp2();
            case 6: return score2();
            case 7: return pickUp3();
            case 8: return score3();
            case 9: return outOfBox();
            default: return null;
        }
    }

}