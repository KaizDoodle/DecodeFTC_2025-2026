
package org.firstinspires.ftc.teamcode.Config.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Config.Core.Util.Alliance;

public class LimeLightSubsystem extends SubsystemBase {
    public Limelight3A limelight;
    int allianceTagID = 0;
    double SCALE = 6.8;
    double YAW_OFFSET = 2;
    Alliance alliance;
//150 0.046
//100 0.06
//50 0.1395



    public LimeLightSubsystem(HardwareMap hardwareMap, Alliance alliance){
        limelight = hardwareMap.get(Limelight3A.class, "limeLight");
        this.alliance = alliance;
//        limelight.setPollRateHz(250);
        if (alliance == Alliance.BLUE)
            allianceTagID = 20;
        else if (alliance == Alliance.RED)
            allianceTagID = 24;
    }
    public double getDistance() {
        LLResultTypes.FiducialResult tag = getAllianceAprilTag();
        if (tag != null)
            return (SCALE/ Math.sqrt(tag.getTargetArea()));
        else return -1;

    }
    public double getYawOffset(){
        LLResultTypes.FiducialResult tag = getAllianceAprilTag();
        double degrees = 4.75;

        if (getDistance() > 100)
            degrees = 0.5;

        if (tag != null) {
            return tag.getTargetXDegrees() -degrees;
            // root of a number - distance
        }
        return -1;
    }

    public Object[] getPattern(){
        Object[] colors = new Object[3];

        if (getAllianceAprilTag().getFiducialId() == 21 ) {
            colors[0] = "g";
            colors[1] = "p";
            colors[2] = "p";
        }
        if (getAllianceAprilTag().getFiducialId() == 22 ) {
            colors[0] = "p";
            colors[1] = "g";
            colors[2] = "p";
        }
        if (getAllianceAprilTag().getFiducialId() == 23 ) {
            colors[0] = "p";
            colors[1] = "p";
            colors[2] = "g";
        }
        return colors;
    }
    public void limeLightStart(){
        limelight.start();
        limelight.pipelineSwitch(0);
    }
    public void inputDistance(double heading){
        limelight.updateRobotOrientation(heading);
    }
    public boolean isLocked(){
        if (getYawOffset() != -1)
            return Math.abs(getYawOffset()) < 2;
        else return false;
    }
    public LLResultTypes.FiducialResult getAllianceAprilTag(){

        for (LLResultTypes.FiducialResult result : limelight.getLatestResult().getFiducialResults()) {
            if (result != null&& result.getFiducialId() == allianceTagID)
                return result;
        }
        return null;
    }
}
