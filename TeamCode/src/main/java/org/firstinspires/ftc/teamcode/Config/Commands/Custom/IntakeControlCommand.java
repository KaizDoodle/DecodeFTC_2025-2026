package org.firstinspires.ftc.teamcode.Config.Commands.Custom;
import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.Config.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Config.Subsystems.LMECSubsystem;

public class IntakeControlCommand extends InstantCommand {

    IntakeSubsystem intakeSubsystem;
    double power;


    public IntakeControlCommand(IntakeSubsystem intakeSubsystem, double power ) {
        this.intakeSubsystem  = intakeSubsystem;
        this.power = power;
        addRequirements(intakeSubsystem);
    }

    @Override
    public void initialize() {
            intakeSubsystem.intakeSpeed(power);

    }
}