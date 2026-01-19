package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.Config.AutoBases.AutoCloseSorted12Base;
import org.firstinspires.ftc.teamcode.Config.Core.Util.Alliance;

@Disabled
@Autonomous(name="Red Auto Close Sorted 12", group="Red")
public class AutoCloseRedSorted extends AutoCloseSorted12Base {
    @Override
    public Alliance getAlliance() {
        return Alliance.RED;
    }
}