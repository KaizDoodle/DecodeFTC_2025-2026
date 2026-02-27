package org.firstinspires.ftc.teamcode.Config.Core.Paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.Config.Core.Util.Alliance;

// USED FOR ALL 15 BALL AUTOS
public class AKIFIRSTAUTOPATH {
    private final Follower follower;
    private final Alliance alliance;

    private int index = 0;

    public Pose start = new Pose(63, 80, Math.toRadians(90));
    public Pose niban = new Pose(63, 110, Math.toRadians(90));
    public Pose sanban = new Pose(50, 140, Math.toRadians(180));

    public Pose bla = new Pose(144, 132);



    public AKIFIRSTAUTOPATH(Follower follower, Alliance alliance) {
        this.follower = follower;
        this.alliance = alliance;

        if (alliance == Alliance.RED) {
            start = start.mirror();
            niban = niban.mirror();

        }
    }

    public PathChain metro() {
        return follower.pathBuilder()
                .addPath(new BezierLine(start, niban))
                .setLinearHeadingInterpolation(start.getHeading(),niban.getHeading())
                .addPath(new BezierCurve(niban, bla, sanban))
                .setLinearHeadingInterpolation(niban.getHeading(),sanban.getHeading())

                .build();
    }


    public PathChain next() {
        switch (index++) {
            case 0: return metro();
            default: return null;
        }
    }

}