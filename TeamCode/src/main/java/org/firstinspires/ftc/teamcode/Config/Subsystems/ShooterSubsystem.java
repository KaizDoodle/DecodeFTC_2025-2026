package org.firstinspires.ftc.teamcode.Config.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ShooterSubsystem extends SubsystemBase {
    final double MIN_POWER = 0.45;
    final double MAX_POWER = 1;

    public Servo cageLeft;
    public Servo cageMiddle;
    public Servo cageRight;

    public DcMotor shooterLeft;
    public DcMotor shooterMiddle;
    public DcMotor shooterRight;


    public ShooterSubsystem(HardwareMap hardwareMap){
        cageLeft = hardwareMap.get(Servo.class, "cageLeft");
        cageMiddle = hardwareMap.get(Servo.class, "cageMiddle");
        cageRight = hardwareMap.get(Servo.class, "cageRight");

        shooterLeft = hardwareMap.get(DcMotor.class, "shooterLeft");
        shooterMiddle = hardwareMap.get(DcMotor.class, "shooterMiddle");
        shooterRight = hardwareMap.get(DcMotor.class, "shooterRight");

    }

    public void shootAll(double speed) {
        cageLeft.setPosition(0.5);
        cageMiddle.setPosition(0.5);
        cageRight.setPosition(0.5);

        shooterLeft.setPower(speed);
        shooterMiddle.setPower(speed);
        shooterRight.setPower(speed);
    }

    public void shootLeft() {
        cageLeft.setPosition(0.5);
    }

    public void shootMiddle() {
        cageMiddle.setPosition(0.5);
    }

    public void shootRight() {
        cageRight.setPosition(0.5);
    }

    public void resetCages() {
        cageLeft.setPosition(0);
        cageMiddle.setPosition(0);
        cageRight.setPosition(0);
    }

    public void setShooterSpeed(double speed){
        shooterLeft.setPower(speed);
        shooterMiddle.setPower(speed);
        shooterRight.setPower(speed);

    }

    public double calculatePowerPercentage(double distancePercent){
        return MIN_POWER + (Math.pow(distancePercent, 1.2)) * (MAX_POWER - MIN_POWER);
    }

}
