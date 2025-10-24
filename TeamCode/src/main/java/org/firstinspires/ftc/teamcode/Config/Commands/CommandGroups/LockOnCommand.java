package org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.pedropathing.follower.Follower;

import org.firstinspires.ftc.teamcode.Config.Subsystems.LimeLightSubsystem;

public class LockOnCommand extends SequentialCommandGroup {
    LimeLightSubsystem limeLightSubsystem;
    Follower follower;

    public LockOnCommand(LimeLightSubsystem limeLightSubsystem,  Follower follower){
        this.limeLightSubsystem = limeLightSubsystem;
        this.follower = follower;
    }

}
