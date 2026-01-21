package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Config.AutoBases.AutoCloseBase;
import org.firstinspires.ftc.teamcode.Config.AutoBases.AutoCloseMMABase;
import org.firstinspires.ftc.teamcode.Config.Core.Util.Alliance;

@Autonomous(name="Red Auto Close MMA", group="Red")
public class AutoCloseMMARed extends AutoCloseMMABase {
    @Override
    public Alliance getAlliance() {
        return Alliance.RED;
    }
}