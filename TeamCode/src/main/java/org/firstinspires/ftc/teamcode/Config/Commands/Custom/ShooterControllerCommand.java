package org.firstinspires.ftc.teamcode.Config.Commands.Custom;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.Config.Subsystems.LimeLightSubsystem;
import org.firstinspires.ftc.teamcode.Config.Subsystems.ShooterSubsystem;

public class ShooterControllerCommand extends InstantCommand {


    ShooterSubsystem shooterSubsystem;
    LimeLightSubsystem limeLightSubsystem;
    double distance;
    public ShooterControllerCommand(ShooterSubsystem shooterSubsystem, double distance) {
        this.shooterSubsystem = shooterSubsystem;
        this.distance = distance;
        addRequirements(shooterSubsystem);
    }
    public ShooterControllerCommand(ShooterSubsystem shooterSubsystem, LimeLightSubsystem limeLightSubsystem) {
        this.limeLightSubsystem = limeLightSubsystem;
        addRequirements(shooterSubsystem);
        distance = limeLightSubsystem.getDistance();

    }

    @Override
    public void initialize() {

        shooterSubsystem.setShooterSpeed(shooterSubsystem.calculatePowerPercentage(distance));



    }

}
