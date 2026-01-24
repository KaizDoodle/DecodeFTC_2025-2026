package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Config.AutoBases.AutoCloseBase;
import org.firstinspires.ftc.teamcode.Config.AutoBases.AutoCloseMMABase;
import org.firstinspires.ftc.teamcode.Config.Core.Util.Alliance;

@Autonomous(name="Blue Auto Close MMA", group="Blue")
public class AutoCloseMMABlue extends AutoCloseMMABase {
    @Override
    public Alliance getAlliance() {
        return Alliance.BLUE;
    }
}