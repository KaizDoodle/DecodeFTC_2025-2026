package org.firstinspires.ftc.teamcode.Config.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ColorSubsystem extends SubsystemBase {
    ColorSensor sensorLeft;
    ColorSensor sensorMiddle;
    ColorSensor sensorRight;


    public ColorSubsystem(HardwareMap hardwareMap) {
        sensorLeft = hardwareMap.get(ColorSensor.class, "sensorLeft");
        sensorMiddle = hardwareMap.get(ColorSensor.class, "sensorLeft");
        sensorRight = hardwareMap.get(ColorSensor.class, "sensorLeft");
    }
    public String getColor(ColorSensor sensor) {
        int red = sensor.red();
        int green = sensor.green();
        int blue = sensor.blue();

        if ((red > 10 && red < 30 ) && (green > 10 && green < 30) && (blue > 30 && blue < 50 ))
            return "P";
        else if ( (red > 30 && red < 50 ) && (green > 30 && green < 50 ) && (blue > 30 && blue < 50 ))
            return "G";
        else return null;
        // or return G
    }

//    return order left to right of colors
    public String sortColor(){
        String colors;

        colors = getColor(sensorLeft) + getColor(sensorMiddle) + getColor(sensorRight);

        return colors;
    }
    public int getBallCount(){

    }


}
