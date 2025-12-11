
package org.firstinspires.ftc.teamcode.Config.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class LMECSubsystem extends SubsystemBase {

    public enum LockState {
        LOCKED, UNLOCKED
    }
    public Servo LMECFront;
    public Servo LMECBack;

    public LockState state = LockState.LOCKED;
    public LMECSubsystem(HardwareMap hardwareMap) {
//        LMECFront = hardwareMap.get(Servo.class, "LMECFront");
        LMECBack = hardwareMap.get(Servo.class, "LMECBack");
    }

    public void lockMechanum() {
//        LMECFront.setPosition(0.5);
        LMECBack.setPosition(0.5);
        state = LockState.LOCKED;
    }

    public void unlockMechanum() {
//        LMECFront.setPosition(0);
        LMECBack.setPosition(0);
        state = LockState.UNLOCKED;
    }
    public LockState getState(){
        return state;
    }
}
