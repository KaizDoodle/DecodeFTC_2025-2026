package org.firstinspires.ftc.teamcode.Config.Commands.Custom;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;

public class ResetIMUCommand extends InstantCommand {

    public ResetIMUCommand(Follower follower) {
        super(() -> {
            Pose current = follower.getPose();
            follower.setPose(new Pose(
                    current.getX(),
                    current.getY(),
                    0.0
            ));
        });
    }
}
