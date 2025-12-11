package org.firstinspires.ftc.teamcode.Config.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Config.Core.Util.ShooterPosition;

public class ColorSubsystem extends SubsystemBase {
    RevColorSensorV3 sensorLeft;
    RevColorSensorV3 sensorMiddle;
    RevColorSensorV3 sensorRight;


    public ColorSubsystem(HardwareMap hardwareMap) {
        sensorLeft = hardwareMap.get(RevColorSensorV3.class, "colorLeft");
        sensorMiddle = hardwareMap.get(RevColorSensorV3.class, "colorMiddle");
        sensorRight = hardwareMap.get(RevColorSensorV3.class, "colorRight");
    }
    public double getDistance(){
        return sensorLeft.getDistance(DistanceUnit.CM);
    }

    //    public double getRed(){
//        NormalizedRGBA val = sensorLeft.getNormalizedColors();
//
//        return  val.red / Math.max(val.alpha, 1.0f);
//
//    }
//    public double getGreen(){
//        NormalizedRGBA val = sensorLeft.getNormalizedColors();
//
//        return  (val.green / Math.max(val.alpha, 1.0f))/getRed();
//
//    }
    public boolean isMiddleFull(){
        return sensorMiddle.getDistance(DistanceUnit.CM) < 6.1;
    }
    public boolean isRightFull(){
        return sensorRight.getDistance(DistanceUnit.CM) < 9.2;

    }
    public boolean isLeftFull(){
        return sensorLeft.getDistance(DistanceUnit.CM) < 4.45;
    }


    public boolean isFull(){
        return isLeftFull() && isMiddleFull() && isRightFull();
    }

    private Object getColor(RevColorSensorV3 sensor, boolean ballDetected) {
        NormalizedRGBA val = sensor.getNormalizedColors();

        float r = val.red / Math.max(val.alpha, 1.0f);
        float g = val.green / Math.max(val.alpha, 1.0f);
        float b = val.blue / Math.max(val.alpha, 1.0f);



        if (ballDetected)
            if (g/r - b/r > 0.2)
                return 'g';
            else
                return 'p';
        else return 'n';
        // or return G
    }



    //    return order left to right of colors
    public Object[] getBallColors(){
        Object[] colors = new Object[3];

//        colors[0] = getColor(sensorLeft, isLeftFull());
//        colors[1] = getColor(sensorMiddle, isMiddleFull());
//        colors[2] = getColor(sensorRight, isRightFull());

        colors[0] = 'p';
        colors[1] = 'p';
        colors[2] = 'g';

        return colors;
    }

    // ------------------------------------------- //
    // ----Get either green or purple location---- //
    // ------------------------------------------- //


    public ShooterPosition getGreenLocation(Object[] colors){
        int place = 0;
        Object[] color = colors;
        for (int i =0; i < color.length; i++){
            if (color[i] == "g")
                place = i;
        }
        switch (place) {
            case 0: return ShooterPosition.LEFT;
            case 1: return ShooterPosition.MIDDLE;
            case 2: return ShooterPosition.RIGHT;
        }
        return null;
    }
    public ShooterPosition getPurpleLocation(Object[] colors){
        int place = 0;
        Object[] color = colors;
        for (int i =0; i < color.length; i++){
            if (color[i] == "p")
                place = i;
        }
        switch (place) {
            case 0: return ShooterPosition.LEFT;
            case 1: return ShooterPosition.MIDDLE;
            case 2: return ShooterPosition.RIGHT;
        }
        return null;
    }



}