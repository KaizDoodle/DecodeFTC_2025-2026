package org.firstinspires.ftc.teamcode.Config.Commands.Custom;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.Config.Core.Util.ShooterPosition;
import org.firstinspires.ftc.teamcode.Config.Subsystems.ShooterSubsystem;

public class ManualResetCommand extends InstantCommand {

    ShooterPosition shooter;
    ShooterSubsystem shooterSubsystem;

    public ManualResetCommand(ShooterSubsystem shooterSubsystem, ShooterPosition shooter) {
        this.shooter = shooter;
        this.shooterSubsystem = shooterSubsystem;
        addRequirements(shooterSubsystem);

    }

    @Override
    public void initialize() {
        shooterSubsystem.resetManual(shooter);
    }

}
