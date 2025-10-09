package org.firstinspires.ftc.teamcode.Config.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ShooterSubsystem extends SubsystemBase {
    final double MIN_POWER = 0.45;
    final double MAX_POWER = 1;
    DcMotor shooter;

    public ShooterSubsystem(HardwareMap hardwareMap){
        shooter = hardwareMap.get(DcMotor.class, "shooter");

    }

    public void setShooterSpeed(double speed){
        shooter.setPower(speed);
    }

    public double calculatePowerPercentage(double distancePercent){
        return MIN_POWER + (Math.pow(distancePercent, 1.2)) * (MAX_POWER - MIN_POWER);
    }

}
