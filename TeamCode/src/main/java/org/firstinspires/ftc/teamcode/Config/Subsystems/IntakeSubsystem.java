
package org.firstinspires.ftc.teamcode.Config.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.List;

public class IntakeSubsystem extends SubsystemBase {
    public static class LMECSubsystem extends SubsystemBase {

        public enum LockState {
            LOCKED, UNLOCKED
        }
        public Servo LMECFront;
        public Servo LMECBack;

        public LockState state = LockState.LOCKED;
        public LMECSubsystem(HardwareMap hardwareMap) {
            LMECFront = hardwareMap.get(Servo.class, "LMECFront");
            LMECBack = hardwareMap.get(Servo.class, "LMECBack");
        }

        public void lockMechanum() {
            LMECFront.setPosition(0.5);
            LMECBack.setPosition(0.5);
            state = LockState.LOCKED;
        }

        public void unlockMechanum() {
            LMECFront.setPosition(0);
            LMECBack.setPosition(0);
            state = LockState.UNLOCKED;
        }
        public LockState getState(){
            return state;
        }
    }

    public static class LimelightSubsystem extends SubsystemBase {
        public Limelight3A limelight;

        public LimelightSubsystem(HardwareMap hardwareMap){
            limelight = hardwareMap.get(Limelight3A.class, "limelight");
        }

        //returns list of april tags seen at a given time
        public List<LLResultTypes.FiducialResult> getAprilTag(){
            //Get detection info
            return limelight.getLatestResult().getFiducialResults();
        }

        public List<LLResultTypes.DetectorResult>  getDetections(){
            return limelight.getLatestResult().getDetectorResults();
        }

        public void getDetections(double numDetections){
            //Get detection info
            LLResult result = limelight.getLatestResult();

            //array list of result time


        }
    }
}
