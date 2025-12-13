package org.firstinspires.ftc.teamcode.Config.Commands.Custom;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.Config.Core.Util.ShooterPosition;
import org.firstinspires.ftc.teamcode.Config.Subsystems.ShooterSubsystem;

import java.util.function.Supplier;

public class ManualCageControlCommand extends InstantCommand {

    private ShooterPosition staticPos;
    private Supplier<ShooterPosition> dynamicPos;

    private final ShooterSubsystem shooterSubsystem;

    // STATIC version (old behavior)
    public ManualCageControlCommand(ShooterSubsystem shooterSubsystem, ShooterPosition shooter) {
        this.staticPos = shooter;
        this.shooterSubsystem = shooterSubsystem;
    }

    // DYNAMIC version (new behavior) — updates live
    public ManualCageControlCommand(ShooterSubsystem shooterSubsystem, Supplier<ShooterPosition> posSupplier) {
        this.dynamicPos = posSupplier;
        this.shooterSubsystem = shooterSubsystem;
    }

    // int version — unchanged
    public ManualCageControlCommand(ShooterSubsystem shooterSubsystem, int i) {
        this.shooterSubsystem = shooterSubsystem;

        switch (i) {
            case 0: staticPos = ShooterPosition.LEFT; break;
            case 1: staticPos = ShooterPosition.MIDDLE; break;
            case 2: staticPos = ShooterPosition.RIGHT; break;

        }
    }

    @Override
    public void initialize() {
        ShooterPosition pos = (dynamicPos != null)
                ? dynamicPos.get()
                : staticPos;

        shooterSubsystem.loadManual(pos);
    }
}