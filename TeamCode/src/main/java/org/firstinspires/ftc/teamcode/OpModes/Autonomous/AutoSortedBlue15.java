package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Config.AutoBases.AutoSorted15Base;
import org.firstinspires.ftc.teamcode.Config.Core.Util.Alliance;

@Autonomous(name="Blue Auto Sorted 15", group="Blue")
public class AutoSortedBlue15 extends AutoSorted15Base {
    @Override
    public Alliance getAlliance() {
        return Alliance.BLUE;
    }
}