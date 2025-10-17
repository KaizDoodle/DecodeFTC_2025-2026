package org.firstinspires.ftc.teamcode.Config.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Config.Core.Util.ShooterPosition;

public class ShooterSubsystem extends SubsystemBase {
    final double MIN_POWER = 0.45;
    final double MAX_POWER = 1;

    private Servo cageLeft;
    private Servo cageMiddle;
    private Servo cageRight;

    private DcMotor shooterLeft;
    private DcMotor shooterMiddle;
    private DcMotor shooterRight;


    public ShooterSubsystem(HardwareMap hardwareMap){
        cageLeft = hardwareMap.get(Servo.class, "cageLeft");
        cageMiddle = hardwareMap.get(Servo.class, "cageMiddle");
        cageRight = hardwareMap.get(Servo.class, "cageRight");

        shooterLeft = hardwareMap.get(DcMotor.class, "shooterLeft");
        shooterMiddle = hardwareMap.get(DcMotor.class, "shooterMiddle");
        shooterRight = hardwareMap.get(DcMotor.class, "shooterRight");

    }

    public void loadAll() {
        cageLeft.setPosition(0.5);
        cageMiddle.setPosition(0.5);
        cageRight.setPosition(0.5);
    }

    public void loadLeft() {
        cageLeft.setPosition(0.5);
    }

    public void loadMiddle() {
        cageMiddle.setPosition(0.5);
    }

    public void loadRight() {
        cageRight.setPosition(0.5);
    }
    public void loadManual(ShooterPosition pos){
        switch (pos){
            case LEFT:
                loadLeft();
                break;
            case MIDDLE:
                loadMiddle();
                break;
            case RIGHT:
                loadRight();
                break;
            case ALL:
                loadAll();
                break;
        }
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
