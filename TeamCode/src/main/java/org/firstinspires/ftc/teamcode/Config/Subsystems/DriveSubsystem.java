package org.firstinspires.ftc.teamcode.Config.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Config.pedroPathing.Constants;

public class DriveSubsystem extends SubsystemBase {
    private final Follower follower;

    public DriveSubsystem(HardwareMap hardwareMap) {
        follower = Constants.createFollower(hardwareMap);
    }
    public void holdPosition(){
//        follower.holdPoint(follower.getPose());
    }
    public void breakPoint(){
//        follower.breakFollowing();
    }
    public void setTeleOpDrive(double forward, double strafe, double rot, boolean fieldCentric) {
        follower.setTeleOpDrive(forward, strafe, rot, fieldCentric);
    }
    public void setStartingPose(Pose pos){
        follower.setStartingPose(pos);
    }
    public void setPose(Pose pos){
        follower.setPose(pos);
    }
    public Pose getPose(){
        return follower.getPose();
    }

    public Follower getFollower() {
        return follower;
    }
    public void startTeleopDrive(){
        follower.startTeleOpDrive();
    }

    public void update() {
        follower.update();
    }
}