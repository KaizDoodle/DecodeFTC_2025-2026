package org.firstinspires.ftc.teamcode.Config.Commands.Custom;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.Config.Core.Util.ShooterPosition;
import org.firstinspires.ftc.teamcode.Config.Subsystems.ShooterSubsystem;

public class ManualCageControlCommand extends InstantCommand {

    ShooterPosition shooter;
    int i;
    ShooterSubsystem shooterSubsystem;

    public ManualCageControlCommand(ShooterSubsystem shooterSubsystem, ShooterPosition shooter) {
        this.shooter = shooter;
        this.shooterSubsystem = shooterSubsystem;
//        addRequirements(shooterSubsystem);

    }
    public ManualCageControlCommand(ShooterSubsystem shooterSubsystem, int i) {
        this.i = i;
        this.shooterSubsystem = shooterSubsystem;

        switch (i) {
            case 0: // Left
                shooter = ShooterPosition.LEFT;
                break;
            case 1: // Middle
                shooter = ShooterPosition.MIDDLE;
                break;
            case 2: // Right
                shooter = ShooterPosition.RIGHT;
                break;
        }
//        addRequirements(shooterSubsystem);
    }

    @Override
    public void initialize() {

        shooterSubsystem.loadManual(shooter);





    }

}
