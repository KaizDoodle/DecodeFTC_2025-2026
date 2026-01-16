package org.firstinspires.ftc.teamcode.Config.Commands.Custom;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.Config.Subsystems.LimeLightSubsystem;
import org.firstinspires.ftc.teamcode.Config.Subsystems.ShooterSubsystem;

public class ShooterControllerCommand extends InstantCommand {


    ShooterSubsystem shooterSubsystem;
    LimeLightSubsystem limeLightSubsystem;
    double distance;
    double speed;


    public ShooterControllerCommand(ShooterSubsystem shooterSubsystem, double speed) {
        this.shooterSubsystem = shooterSubsystem;
        this.speed = speed;
        addRequirements(shooterSubsystem);
    }

    public ShooterControllerCommand(ShooterSubsystem shooterSubsystem, LimeLightSubsystem limeLightSubsystem, int tagID) {
        this.limeLightSubsystem = limeLightSubsystem;
        addRequirements(shooterSubsystem);
        distance = limeLightSubsystem.getDistance();

    }

    @Override
    public void execute() {
        shooterSubsystem.setShooterVelocity(speed);
    }

}
