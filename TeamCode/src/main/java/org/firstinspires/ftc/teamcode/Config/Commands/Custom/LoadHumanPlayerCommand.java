package org.firstinspires.ftc.teamcode.Config.Commands.Custom;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.Config.Core.Util.ShooterPosition;
import org.firstinspires.ftc.teamcode.Config.Subsystems.ShooterSubsystem;

public class LoadHumanPlayerCommand extends InstantCommand {

    ShooterSubsystem shooterSubsystem;

    public LoadHumanPlayerCommand(ShooterSubsystem shooterSubsystem){
        this.shooterSubsystem = shooterSubsystem;
        addRequirements(shooterSubsystem);

    }

    @Override
    public void initialize() {
        shooterSubsystem.setShooterSpeed(-0.2);
        shooterSubsystem.loadManual(ShooterPosition.ALL);

    }
}
