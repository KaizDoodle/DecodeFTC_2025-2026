package org.firstinspires.ftc.teamcode.Config.Commands.Custom;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.Config.Core.Util.ShooterPosition;
import org.firstinspires.ftc.teamcode.Config.Subsystems.ShooterSubsystem;

import java.util.function.Supplier;

public class ManualResetCommand extends InstantCommand {

    ShooterPosition shooter;
    Supplier<ShooterPosition> posSupplier;

    ShooterSubsystem shooterSubsystem;

    public ManualResetCommand(ShooterSubsystem shooterSubsystem, ShooterPosition shooter) {
        this.shooter = shooter;
        this.shooterSubsystem = shooterSubsystem;
//        addRequirements(shooterSubsystem);

    }
    public ManualResetCommand(ShooterSubsystem shooterSubsystem, Supplier<ShooterPosition> posSupplier) {
        this.shooterSubsystem = shooterSubsystem;
        this.posSupplier = posSupplier;
//        addRequirements(shooter);
    }

    @Override
    public void initialize() {
        ShooterPosition pos = (posSupplier != null)
                ? posSupplier.get()
                : shooter;

        shooterSubsystem.resetManual(pos);
    }
}
