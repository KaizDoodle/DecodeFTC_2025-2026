package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Config.AutoBases.AutoFarBase;
import org.firstinspires.ftc.teamcode.Config.Core.Util.Alliance;

@Autonomous(name="Blue Auto Far", group="Blue")
public class AutoFarBlue extends AutoFarBase {
    @Override
    public Alliance getAlliance() {
        return Alliance.BLUE;
    }
}