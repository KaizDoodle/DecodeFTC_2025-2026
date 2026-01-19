package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.Config.AutoBases.AutoCloseSorted12Base;
import org.firstinspires.ftc.teamcode.Config.Core.Util.Alliance;

@Disabled
@Autonomous(name="Blue Auto Close Sorted 12", group="Blue")
public class AutoCloseBlueSorted extends AutoCloseSorted12Base {
    @Override
    public Alliance getAlliance() {
        return Alliance.BLUE;
    }
}