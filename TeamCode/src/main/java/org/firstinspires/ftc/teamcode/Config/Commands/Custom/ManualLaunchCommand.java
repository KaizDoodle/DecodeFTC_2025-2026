package org.firstinspires.ftc.teamcode.Config.Commands.Custom;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.Config.Core.Util.ShooterPosition;
import org.firstinspires.ftc.teamcode.Config.Subsystems.ShooterSubsystem;

public class ManualLaunchCommand extends InstantCommand {

    ShooterPosition shooter;
    ShooterSubsystem shooterSubsystem;

    public ManualLaunchCommand(ShooterSubsystem shooterSubsystem, ShooterPosition shooter) {
        this.shooter = shooter;
        this.shooterSubsystem = shooterSubsystem;
        addRequirements(shooterSubsystem);

    }

    @Override
    public void initialize() {

        shooterSubsystem.loadManual(shooter);





    }

}
