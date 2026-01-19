package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Config.AutoBases.AutoCloseBase;
import org.firstinspires.ftc.teamcode.Config.Core.Util.Alliance;

@Autonomous(name="Blue Auto Close", group="Blue")
public class AutoCloseBlue extends AutoCloseBase {
    @Override
    public Alliance getAlliance() {
        return Alliance.BLUE;
    }
}