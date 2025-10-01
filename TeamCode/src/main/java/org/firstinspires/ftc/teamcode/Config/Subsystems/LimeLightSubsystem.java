
package org.firstinspires.ftc.teamcode.Config.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;


import org.firstinspires.ftc.teamcode.OpModes.Testing.LimelightTest;

import java.util.List;

public class LimeLightSubsystem extends SubsystemBase {
    public Limelight3A limelight;
    public LLResultTypes.FiducialResult apriltag;
    int id;

    public LimeLightSubsystem(HardwareMap hardwareMap){
        limelight = hardwareMap.get(Limelight3A.class, "limeLight");
    }

    //returns list of april tags seen at a given time
    public LLResultTypes.FiducialResult getAprilTag(){
        for (LLResultTypes.FiducialResult dr : limelight.getLatestResult().getFiducialResults()) {
//            if (dr.getFiducialId() == 20)
                apriltag = dr;
        }
        return apriltag;
    }//modifier / 100.0) * 0.3;

    public double getPowerModifier(){
        return 1 - ((getDistance() /100) * 0.3);
    }
    public double getDistance(){
        return getAprilTag().getTargetArea();
    }
    public void limeLightStart(){
        limelight.start();
        limelight.pipelineSwitch(1);
    }

    private LLResult getDetections(){
        //Get detection info
        return limelight.getLatestResult();

        //array list of result time
    }

}
