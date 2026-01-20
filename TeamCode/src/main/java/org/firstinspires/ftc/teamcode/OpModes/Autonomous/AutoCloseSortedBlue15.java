package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Config.AutoBases.AutoCloseSorted15Base;
import org.firstinspires.ftc.teamcode.Config.Core.Util.Alliance;

@Autonomous(name="Blue Auto Close Sorted 15", group="Blue")
public class AutoCloseSortedBlue15 extends AutoCloseSorted15Base {
    @Override
    public Alliance getAlliance() {
        return Alliance.BLUE;
    }
}