package org.firstinspires.ftc.teamcode.Config.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Config.Core.Util.ShooterPosition;

public class ShooterSubsystem extends SubsystemBase {

    private final double MIN_POWER = 0.44;
    private final double MAX_POWER = 0.85;
    private final double LAUNCH_POSE = 0.2;
    private final double LOAD_POS = 0.15;

    private Servo cageLeft, cageMiddle, cageRight;
    private DcMotor shooterOne;
    private DcMotorEx shooterTwo;
    private DcMotor shooterThree;

    // Busy flags for concurrency control
    private boolean[] launcherBusy = new boolean[3]; // LEFT, MIDDLE, RIGHT

    public ShooterSubsystem(HardwareMap hardwareMap) {
        cageLeft = hardwareMap.get(Servo.class, "cageLeft");
        cageMiddle = hardwareMap.get(Servo.class, "cageMiddle");
        cageRight = hardwareMap.get(Servo.class, "cageRight");

        shooterOne = hardwareMap.get(DcMotor.class, "shooterOne");
        shooterTwo = hardwareMap.get(DcMotorEx.class, "shooterTwo");
        shooterThree = hardwareMap.get(DcMotor.class, "shooterThree");

        resetManual(ShooterPosition.ALL);
    }


    public boolean isLauncherBusy(ShooterPosition pos) {
        if (pos == ShooterPosition.ALL || pos == ShooterPosition.LOAD) {
            return launcherBusy[0] || launcherBusy[1] || launcherBusy[2];
        }
        return launcherBusy[pos.ordinal()];
    }

    public void setLauncherBusy(ShooterPosition pos, boolean busy) {
        if (pos == ShooterPosition.ALL || pos == ShooterPosition.LOAD) {
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
            }
        }
    }

    public void resetManual(ShooterPosition pos) {
        switch (pos) {
            case LEFT: cageLeft.setPosition(0); break;
            case MIDDLE: cageMiddle.setPosition(0); break;
            case RIGHT: cageRight.setPosition(0); break;
            case ALL:
                cageLeft.setPosition(0);
                cageMiddle.setPosition(0);
                cageRight.setPosition(0);
                break;
        }
        setLauncherBusy(pos, false);
    }

    public void setShooterSpeed(double speed) {
        shooterOne.setPower(speed);
        shooterTwo.setPower(speed);
        shooterThree.setPower(speed);
    }

    public double getLaunchVelocity() {
        return shooterTwo.getVelocity();
    }

    public double calculatePowerPercentage(double distancePercent) {
        return MIN_POWER + Math.pow(distancePercent, 5) * (MAX_POWER - MIN_POWER);
    }
}
