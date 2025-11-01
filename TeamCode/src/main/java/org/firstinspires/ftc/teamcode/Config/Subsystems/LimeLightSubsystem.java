
package org.firstinspires.ftc.teamcode.Config.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class LimeLightSubsystem extends SubsystemBase {
    public Limelight3A limelight;
    public LLResultTypes.FiducialResult apriltag;
    int id;
    final double MAX_DISTANCE = 0.03;
    final double MIN_DISTANCE = 0.0015;



    public LimeLightSubsystem(HardwareMap hardwareMap){
        limelight = hardwareMap.get(Limelight3A.class, "limeLight");
    }

    //returns list of april tags seen at a given time

//    public double getPowerModifier(){
//        return 1 - ((getDistance() /100) * 0.3);
//    }

    public double getDistance() {
        LLResultTypes.FiducialResult tag = getAprilTag();
        if (tag != null) {
            return ((MAX_DISTANCE - tag.getTargetArea()) / (MAX_DISTANCE - MIN_DISTANCE));
        } else{
            return 0; // Return -1 if no tag was detected
    }
    }

    public double getYawOffset(int tagID){
        LLResultTypes.FiducialResult tag = getAprilTag(tagID);
        if (tag != null) {
            return tag.getTargetXDegrees();
        }
        return -1;
    }
    public String getPattern(){
        String colors = "";

        if (getAprilTag().getFiducialId() == 20 )
            colors = "gpp";
        if (getAprilTag().getFiducialId() == 21 )
            colors = "pgp";
        if (getAprilTag().getFiducialId() == 22 )
            colors = "ppg";

        return colors;
    }
    public void limeLightStart(){
        limelight.start();
        limelight.pipelineSwitch(0);
    }
    public void inputDistance(double heading){
        limelight.updateRobotOrientation(heading);
    }

    public LLResultTypes.FiducialResult getAprilTag(int tagID){

        for (LLResultTypes.FiducialResult dr : limelight.getLatestResult().getFiducialResults()) {
            if (dr != null && dr.getFiducialId() == tagID)
                apriltag = dr;
        }
        return null;
    }//modifier / 100.0) * 0.3;
    public LLResultTypes.FiducialResult getAprilTag(){

        for (LLResultTypes.FiducialResult dr : limelight.getLatestResult().getFiducialResults()) {
            if (dr != null)
                return apriltag;
        }
        return null;
    }


}
