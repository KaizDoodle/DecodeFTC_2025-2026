package org.firstinspires.ftc.teamcode.Config.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import org.firstinspires.ftc.teamcode.Config.Drivers.GoBildaPinpointDriver;

public class DriveSubsystem extends SubsystemBase {
    GoBildaPinpointDriver odo;
    Follower follower;


    public DriveSubsystem(final HardwareMap hardwareMap, Follower follower) {
        this.follower = follower;
    }

    public void driveFieldCentric(double left_stick_x, double left_stick_y , double gamepadRX) {
        follower.setTeleOpDrive(-left_stick_y, -left_stick_x, -gamepadRX, false);
        follower.update();
    }

    public double getHeadingRads() {
        return odo.getHeading(AngleUnit.RADIANS);
    }


    public double getHeadingDeg() {
        return odo.getHeading(AngleUnit.DEGREES);
    }

    public void resetHeading(){
        odo.recalibrateIMU();
    }

    public void odoUpdate() {
        odo.update();

    }


}