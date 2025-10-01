package org.firstinspires.ftc.teamcode.Config.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ShooterSubsystem extends SubsystemBase {

    DcMotor shooter;

    public ShooterSubsystem(HardwareMap hardwareMap){
        shooter = hardwareMap.get(DcMotor.class, "shooter");

    }

    public void setShooterSpeed(double speed){
        shooter.setPower(speed);
    }

}
