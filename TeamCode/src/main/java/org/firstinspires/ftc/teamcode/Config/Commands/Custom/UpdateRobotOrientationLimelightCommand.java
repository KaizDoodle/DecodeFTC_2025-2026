package org.firstinspires.ftc.teamcode.Config.Commands.Custom;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.Config.Subsystems.LimeLightSubsystem;

public class UpdateRobotOrientationLimelightCommand extends InstantCommand {

    LimeLightSubsystem limelight;
    double heading;
    public UpdateRobotOrientationLimelightCommand(LimeLightSubsystem limelight, double heading){
        this.limelight = limelight;
        this.heading = heading;
    }
    @Override
    public void initialize() {
        limelight.inputDistance(heading);
    }


}
