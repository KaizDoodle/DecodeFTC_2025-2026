package org.firstinspires.ftc.teamcode.Config.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

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
    private Object getColor(ColorSensor sensor) {
        int red = sensor.red();
        int green = sensor.green();
        int blue = sensor.blue();

        if ((red < 50 ) && (green > 48 && green < 55) && (blue > 50 && blue < 57 ))
            return 'p';
        else if ( (red < 50 ) && (green > 55 && green < 62 ) && (blue > 45 && blue < 52 ))
            return 'g';
        else return 'n';
        // or return G
    }



    //    return order left to right of colors
    public Object[] getBallColors(){
        Object[] colors = new Object[3];

        colors[0] = getColor(sensorLeft);
        colors[1] = getColor(sensorMiddle);
        colors[2] = getColor(sensorRight);

        return colors;
    }

    // ------------------------------------------- //
    // ----Get either green or purple location---- //
    // ------------------------------------------- //
    public int getSupplyPurple(){
        Object[] array = getBallColors();
        int count = 0;
        for (Object o : array) {
            if (o == "p") count++;
        }
        return count;
    }
    public int getSupplyGreen(){
        Object[] array = getBallColors();
        int count = 0;
        for (Object o : array) {
            if (o == "g") count++;
        }
        return count;
    }

    public ShooterPosition getGreenLocation(){
        int place = 0;
        Object[] color = getBallColors();
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
    public ShooterPosition getPurpleLocation(){
        int place = 0;
        Object[] color = getBallColors();
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