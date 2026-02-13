package org.firstinspires.ftc.teamcode.Config.Commands.Custom;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Config.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.Config.Subsystems.LimeLightSubsystem;
import java.util.function.DoubleSupplier;

public class AimLockCommand extends CommandBase {
    private final LimeLightSubsystem limelight;
    private final DriveSubsystem driveSubsystem;
    private final DoubleSupplier forwardInput, strafeInput, manualTurnInput;

    // --- PIDF Values ---
    public static double kP = 0.035;
    public static double kI = 0.0;
    public static double kD = 0.001;
    public static double kF = 0.05;

    private final PIDFController controller;

    public AimLockCommand(LimeLightSubsystem limelight, DriveSubsystem driveSubsystem,
                          DoubleSupplier forward, DoubleSupplier strafe, DoubleSupplier turn) {
        this.limelight = limelight;
        this.driveSubsystem = driveSubsystem;
        this.forwardInput = forward;
        this.strafeInput = strafe;
        this.manualTurnInput = turn;

        this.controller = new PIDFController(kP, kI, kD, 0);
        addRequirements(limelight, driveSubsystem);
    }

    @Override
    public void execute() {
        double rotationPower;

        // CHECK: Does the Limelight actually see a tag?
        if (limelight.getAllianceAprilTag() != null) {
            // --- PID LOGIC ---
            double error = limelight.getYawOffset();
            double pidOutput = controller.calculate(error, 0);
            double feedforward = kF * Math.signum(error);

            rotationPower = Range.clip(pidOutput + feedforward, -0.7, 0.7);
        } else {
            // --- FALLBACK: Manual Control ---
            // If tag is lost, use the manual turn input from the joystick
            rotationPower = manualTurnInput.getAsDouble() * 0.7;
        }

        driveSubsystem.setTeleOpDrive(
                forwardInput.getAsDouble(),
                strafeInput.getAsDouble(),
                rotationPower,
                false
        );
    }
}