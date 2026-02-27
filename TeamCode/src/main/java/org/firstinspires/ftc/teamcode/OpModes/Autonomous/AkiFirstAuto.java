package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Config.AutoBases.AutoCloseBase;
import org.firstinspires.ftc.teamcode.Config.AutoBases.otameshi;
import org.firstinspires.ftc.teamcode.Config.Core.Util.Alliance;

@Autonomous(name="Aki First Auto", group="Blue")
public class AkiFirstAuto extends otameshi {
    @Override
    public Alliance getAlliance() {
        return Alliance.BLUE;
    }
}