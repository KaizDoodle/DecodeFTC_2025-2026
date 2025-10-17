package org.firstinspires.ftc.teamcode.Config.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ColorSubsystem extends SubsystemBase {
    ColorSensor sensorLeft;
    ColorSensor sensorMiddle;
    ColorSensor sensorRight;

    String colorLeft;
    String colorMiddle;
    String colorRight;




    public ColorSubsystem(HardwareMap hardwareMap) {
        sensorLeft = hardwareMap.get(ColorSensor.class, "sensorLeft");
        sensorMiddle = hardwareMap.get(ColorSensor.class, "sensorLeft");
        sensorRight = hardwareMap.get(ColorSensor.class, "sensorLeft");
    }
    public String getColor(ColorSensor sensor) {
        int red = sensor.red();
        int green = sensor.green();
        int blue = sensor.blue();
        return "purple";
    }

//    return order left to right of colors
    public String[] sortColor(){
        String[] colors = new String[3];

        colors[0] = getColor(sensorLeft);
        colors[1] = getColor(sensorMiddle);
        colors[2] = getColor(sensorRight);

        return colors;
    }


}
