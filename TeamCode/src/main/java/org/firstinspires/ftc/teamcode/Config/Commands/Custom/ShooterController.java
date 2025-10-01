package org.firstinspires.ftc.teamcode.Config.Commands.Custom;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.Config.Subsystems.ShooterSubsystem;

public class ShooterController extends InstantCommand {

    ShooterSubsystem shooterSubsystem;
    double speed;

    public ShooterController(ShooterSubsystem shooterSubsystem, double speed) {
        this.shooterSubsystem = shooterSubsystem;
        this.speed = speed;
        addRequirements(shooterSubsystem);

    }

    @Override
    public void initialize() {

        shooterSubsystem.setShooterSpeed(speed);


    }

}
