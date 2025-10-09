package org.firstinspires.ftc.teamcode.Config.Commands.Custom;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TelemetryAddCommand extends InstantCommand {
    Telemetry telemetry;
    public TelemetryAddCommand(Telemetry telemetry){
        this.telemetry = telemetry;

    }

}
