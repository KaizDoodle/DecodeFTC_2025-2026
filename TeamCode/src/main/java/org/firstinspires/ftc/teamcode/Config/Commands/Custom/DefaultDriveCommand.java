package org.firstinspires.ftc.teamcode.Config.Commands.Custom;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.Config.Subsystems.DriveSubsystem;
import java.util.function.DoubleSupplier;

public class DefaultDriveCommand extends CommandBase {
    private final DriveSubsystem driveSubsystem;
    private final DoubleSupplier forward, strafe, rotation;

    public DefaultDriveCommand(DriveSubsystem drive, DoubleSupplier forward, DoubleSupplier strafe, DoubleSupplier rotation) {
        this.driveSubsystem = drive;
        this.forward = forward;
        this.strafe = strafe;
        this.rotation = rotation;

        // IMPORTANT: This tells the scheduler that this command controls the drive base
        addRequirements(driveSubsystem);
    }

    @Override
    public void execute() {
        // Reads from the suppliers (gamepad) every loop
        driveSubsystem.setTeleOpDrive(
                forward.getAsDouble(),
                strafe.getAsDouble(),
                rotation.getAsDouble(),
                false // fieldCentric boolean
        );
    }
}