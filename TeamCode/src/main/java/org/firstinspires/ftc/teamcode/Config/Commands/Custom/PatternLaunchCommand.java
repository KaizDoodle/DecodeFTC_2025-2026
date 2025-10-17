package org.firstinspires.ftc.teamcode.Config.Commands.Custom;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import org.firstinspires.ftc.teamcode.Config.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Config.Subsystems.ShooterSubsystem;

public class PatternLaunchCommand extends SequentialCommandGroup {

    private static final long SHOT_DURATION_MS = 500;
    private static final long INTER_SHOT_DELAY_MS = 250;

    public enum Pattern {
        GPP,
        PGP,
        PPG
    }

    public PatternLaunchCommand(ShooterSubsystem shooter, IntakeSubsystem intake, Pattern pattern) {
        addRequirements(shooter, intake);

        // Create the sequence of shots based on the pattern
        switch (pattern) {
            case GPP: // Green, Purple, Purple
                addCommands(
                        // Shoot Green
                        createShotCommand(shooter, shooter::shootLeft),
                        new WaitCommand(INTER_SHOT_DELAY_MS),
                        // Shoot Purple
                        createShotCommand(shooter, shooter::shootMiddle),
                        new WaitCommand(INTER_SHOT_DELAY_MS),
                        // Shoot Purple
                        createShotCommand(shooter, shooter::shootRight)
                );
                break;
            case PGP: // Purple, Green, Purple
                addCommands(
                        // Shoot Purple
                        createShotCommand(shooter, shooter::shootLeft),
                        new WaitCommand(INTER_SHOT_DELAY_MS),
                        // Shoot Green
                        createShotCommand(shooter, shooter::shootMiddle),
                        new WaitCommand(INTER_SHOT_DELAY_MS),
                        // Shoot Purple
                        createShotCommand(shooter, shooter::shootRight)
                );
                break;
            case PPG: // Purple, Purple, Green
                addCommands(
                        // Shoot Purple
                        createShotCommand(shooter, shooter::shootLeft),
                        new WaitCommand(INTER_SHOT_DELAY_MS),
                        // Shoot Purple
                        createShotCommand(shooter, shooter::shootMiddle),
                        new WaitCommand(INTER_SHOT_DELAY_MS),
                        // Shoot Green
                        createShotCommand(shooter, shooter::shootRight)
                );
                break;
        }
    }

    // Helper method to create a single shot sequence
    private SequentialCommandGroup createShotCommand(ShooterSubsystem shooter, Runnable shotAction) {
        return new SequentialCommandGroup(
                new InstantCommand(shotAction, shooter), // Open the cage
                new WaitCommand(SHOT_DURATION_MS),      // Wait for the ball to be shot
                new InstantCommand(shooter::resetCages, shooter) // Reset the cages
        );
    }
}
