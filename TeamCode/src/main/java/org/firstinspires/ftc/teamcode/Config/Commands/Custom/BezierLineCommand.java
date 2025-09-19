package org.firstinspires.ftc.teamcode.Config.Commands.Custom;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;

public class BezierLineCommand extends InstantCommand {




    public BezierLineCommand(Pose startPose, Pose interPose, Follower follower, PathChain lineFollower){
        lineFollower = follower.pathBuilder()
                .addPath(new BezierLine(new Point(startPose), new Point(interPose)))
                .setLinearHeadingInterpolation(startPose.getHeading(), interPose.getHeading())
                .build();

        follower.followPath(lineFollower);

    }


    @Override
    public void initialize() {



    }

}