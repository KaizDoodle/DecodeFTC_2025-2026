package org.firstinspires.ftc.teamcode.Config.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Config.Core.Util.RobotStates;
import org.firstinspires.ftc.teamcode.Config.Core.Util.ShooterPosition;

public class ShooterSubsystem extends SubsystemBase {

    // 36 .5
    // 48 .52
    // 60 .54
    // 72 .57
    // 84 .61
    // 96 .63
    // 10 .7
    // 11 .72

    private final double MIN_POWER = 0.5;
    private final double MAX_POWER = 0.7;
    private final double MAX_VELOCITY = 1500;

    private final double LAUNCH_POSE = 0.2;
    private final double LOAD_POS = 0.15;

    private Servo cageLeft, cageMiddle, cageRight;
    private DcMotorEx shooterOne, shooterTwo, shooterThree;

    // Busy flags for concurrency control
    private boolean[] launcherBusy = new boolean[3]; // LEFT, MIDDLE, RIGHT

    public ShooterSubsystem(HardwareMap hardwareMap) {
        cageLeft = hardwareMap.get(Servo.class, "cageLeft");
        cageMiddle = hardwareMap.get(Servo.class, "cageMiddle");
        cageRight = hardwareMap.get(Servo.class, "cageRight");


        shooterOne = hardwareMap.get(DcMotorEx.class, "shooterOne");
        shooterTwo = hardwareMap.get(DcMotorEx.class, "shooterTwo");
        shooterThree = hardwareMap.get(DcMotorEx.class, "shooterThree");

        resetManual(ShooterPosition.ALL);
    }



    public boolean isLauncherBusy(ShooterPosition pos) {
        if (pos == ShooterPosition.ALL || pos == ShooterPosition.LOAD || pos == ShooterPosition.INTAKE) {
            return launcherBusy[0] || launcherBusy[1] || launcherBusy[2];
        }
        return launcherBusy[pos.ordinal()];
    }

    public void setLauncherBusy(ShooterPosition pos, boolean busy) {
        if (pos == ShooterPosition.ALL || pos == ShooterPosition.LOAD || pos == ShooterPosition.INTAKE) {
            for (int i = 0; i < 3; i++) launcherBusy[i] = busy;
        } else {
            launcherBusy[pos.ordinal()] = busy;
        }
    }

    public void loadManual(ShooterPosition pos) {
        if (!isLauncherBusy(pos)) {
            setLauncherBusy(pos, true);
            switch (pos) {
                case LEFT: cageLeft.setPosition(LAUNCH_POSE); break;
                case MIDDLE: cageMiddle.setPosition(LAUNCH_POSE); break;
                case RIGHT: cageRight.setPosition(LAUNCH_POSE); break;
                case ALL:
                    cageLeft.setPosition(LAUNCH_POSE);
                    cageMiddle.setPosition(LAUNCH_POSE);
                    cageRight.setPosition(LAUNCH_POSE);
                    break;
                case LOAD:
                    cageLeft.setPosition(LOAD_POS);
                    cageMiddle.setPosition(LOAD_POS);
                    cageRight.setPosition(LOAD_POS);
                    break;
                case INTAKE:
                    cageLeft.setPosition(0);
                    cageMiddle.setPosition(0);
                    cageRight.setPosition(0);
            }
        }
    }

    public void resetManual(ShooterPosition pos) {
        switch (pos) {
            case LEFT: cageLeft.setPosition(0.075); break;
            case MIDDLE: cageMiddle.setPosition(0.075); break;
            case RIGHT: cageRight.setPosition(0.075); break;
            case INTAKE:
            case LOAD:
            case ALL:
                cageLeft.setPosition(0.075);
                cageMiddle.setPosition(0.075);
                cageRight.setPosition(0.075);
                break;

        }
        setLauncherBusy(pos, false);
    }

    public void setShooterSpeed(double speed) {
        shooterOne.setPower(speed);
        shooterTwo.setPower(speed);
        shooterThree.setPower(speed);
    }
    public double getTargetVelocity(double power){
        return power * 1500;

    }

    public double getLaunchVelocity1() {
        return shooterOne.getVelocity();
    }
    public double getLaunchVelocity2() {
        return shooterTwo.getVelocity();
    }
    public double getLaunchVelocity3() {
        return shooterThree.getVelocity();
    }

    public double calculatePowerPercentage(double distancePercent) {

        double i = 0.00229 * distancePercent +0.4176;
        return Range.clip(i, MIN_POWER, MAX_POWER);
    }
}
