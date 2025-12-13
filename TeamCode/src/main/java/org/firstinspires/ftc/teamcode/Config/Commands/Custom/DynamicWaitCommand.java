package org.firstinspires.ftc.teamcode.Config.Commands.Custom;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.util.Range;

import java.util.function.DoubleSupplier;

public class DynamicWaitCommand extends CommandBase {
    private final DoubleSupplier timeSupplier;
    private long startTime, waitTime;
    private int multiplier = 1;

    public DynamicWaitCommand(DoubleSupplier supplier) {
        timeSupplier = supplier;
    }
    public DynamicWaitCommand(DoubleSupplier supplier, int multipler) {
        timeSupplier = supplier;
        this.multiplier = multipler;
    }


    @Override
    public void initialize() {
        startTime = System.currentTimeMillis();
        waitTime = (long) timeSupplier.getAsDouble() * multiplier; // ✔ updated each time it runs

    }

    @Override
    public boolean isFinished() {
        return System.currentTimeMillis() - startTime >= waitTime;
    }
}
