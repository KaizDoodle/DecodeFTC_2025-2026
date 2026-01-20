package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Config.AutoBases.AutoCloseSorted15Base;
import org.firstinspires.ftc.teamcode.Config.Core.Util.Alliance;

@Autonomous(name="Red Auto Close Sorted 15", group="Red")
public class AutoCloseSortedRed15 extends AutoCloseSorted15Base {
    @Override
    public Alliance getAlliance() {
        return Alliance.RED;
    }
}