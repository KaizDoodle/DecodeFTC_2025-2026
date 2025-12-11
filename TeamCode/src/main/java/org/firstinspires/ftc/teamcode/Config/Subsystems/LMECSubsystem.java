
package org.firstinspires.ftc.teamcode.Config.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class LMECSubsystem extends SubsystemBase {

    public enum LockState {
        LOCKED, UNLOCKED
    }
    public Servo LMECFront;

    public LockState state = LockState.UNLOCKED;
    public LMECSubsystem(HardwareMap hardwareMap) {
        LMECFront = hardwareMap.get(Servo.class, "LMECBack");
    }

    public void lockMechanum() {
//        LMECFront.setPosition(0.5);
        LMECFront.setPosition(0.5);
        state = LockState.LOCKED;
    }

    public void unlockMechanum() {
//        LMECFront.setPosition(0);
        LMECFront.setPosition(0);
        state = LockState.UNLOCKED;
    }
    public LockState getState(){
        return state;
    }
}
