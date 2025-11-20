package org.firstinspires.ftc.teamcode.Config.Commands.Custom;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.sun.tools.javac.comp.Todo;

import org.firstinspires.ftc.teamcode.Config.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Config.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.Config.Core.Util.ShooterPosition;

public class IntakeControlCommand extends InstantCommand {

    private final IntakeSubsystem intakeSubsystem;
    private final double power;

    public IntakeControlCommand(IntakeSubsystem intakeSubsystem, double power) {
        this.intakeSubsystem = intakeSubsystem;
        this.power = power;
        addRequirements(intakeSubsystem);
    }

    @Override
    public void initialize() {
        intakeSubsystem.intakeSpeed(power);
        // @TODO add color sensor logic later

    }
}
