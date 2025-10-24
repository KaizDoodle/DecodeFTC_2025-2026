package org.firstinspires.ftc.teamcode.Config.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorControllerEx;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Config.Core.Util.ShooterPosition;

public class ShooterSubsystem extends SubsystemBase {
    final double MIN_POWER = 0.45;
    final double LAUNCH_POSE = 0.15;
    final double MAX_POWER = 1;

    private Servo cageLeft;
    private Servo cageMiddle;
    private Servo cageRight;

    private DcMotor shooterOne;
    private DcMotorEx shooterTwo;
    private DcMotor shooterThree;


    public ShooterSubsystem(HardwareMap hardwareMap){
        cageLeft = hardwareMap.get(Servo.class, "cageLeft");
        cageMiddle = hardwareMap.get(Servo.class, "cageMiddle");
        cageRight = hardwareMap.get(Servo.class, "cageRight");

        shooterOne = hardwareMap.get(DcMotor.class, "shooterOne");
        shooterTwo = hardwareMap.get(DcMotorEx.class, "shooterTwo");
        shooterThree = hardwareMap.get(DcMotor.class, "shooterThree");

    }

    private void loadAll() {
        cageLeft.setPosition(LAUNCH_POSE);
        cageMiddle.setPosition(LAUNCH_POSE);
        cageRight.setPosition(LAUNCH_POSE);
    }
    private void resetAll() {
        cageLeft.setPosition(0);
        cageMiddle.setPosition(0);
        cageRight.setPosition(0);
    }

    private void loadLeft() {
        cageLeft.setPosition(LAUNCH_POSE);
    }

    private void loadMiddle() {
        cageMiddle.setPosition(LAUNCH_POSE);
    }

    private void loadRight() {
        cageRight.setPosition(LAUNCH_POSE);
    }
    private void resetLeft() {
        cageLeft.setPosition(0);
    }

    private void resetMiddle() {
        cageMiddle.setPosition(0);
    }

    private void resetRight() {
        cageRight.setPosition(0);
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
    public void resetManual(ShooterPosition pos){
        switch (pos){
            case LEFT:
                resetLeft();
                break;
            case MIDDLE:
                resetMiddle();
                break;
            case RIGHT:
                resetRight();
                break;
            case ALL:
                resetAll();
                break;
        }
    }



    public void setShooterSpeed(double speed){
        shooterOne.setPower(speed);
        shooterTwo.setPower(speed);
        shooterThree.setPower(speed);
    }
    public double getLaunchVelocity(){
        return shooterTwo.getVelocity();
    }

    public double calculatePowerPercentage(double distancePercent){
        return MIN_POWER + (Math.pow(distancePercent, 1.2)) * (MAX_POWER - MIN_POWER);
    }

}
