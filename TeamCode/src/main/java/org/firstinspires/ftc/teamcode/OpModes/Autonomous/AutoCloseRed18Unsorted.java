package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Config.AutoBases.AutoCloseBase18;
import org.firstinspires.ftc.teamcode.Config.Core.Util.Alliance;
@Autonomous(name="Red Auto Close Unsorted 18", group="RED")
public class AutoCloseRed18Unsorted extends AutoCloseBase18 {
    @Override
    public Alliance getAlliance() {
        return Alliance.RED;
    }
}