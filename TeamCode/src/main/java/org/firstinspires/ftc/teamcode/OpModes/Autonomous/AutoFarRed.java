package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Config.AutoBases.AutoCloseBase;
import org.firstinspires.ftc.teamcode.Config.AutoBases.AutoFarBase;
import org.firstinspires.ftc.teamcode.Config.Core.Util.Alliance;

@Autonomous(name="Red Auto Far", group="Red")
public class AutoFarRed extends AutoFarBase {
    @Override
    public Alliance getAlliance() {
        return Alliance.RED;
    }
}