package org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ManualLaunchCommand;
import org.firstinspires.ftc.teamcode.Config.Core.Util.Pattern;
import org.firstinspires.ftc.teamcode.Config.Core.Util.ShooterPosition;
import org.firstinspires.ftc.teamcode.Config.Subsystems.ShooterSubsystem;

/**
 * Shoots the next available ball of a specified color, based on a statically set pattern.
 * This command is stateful and keeps track of which shooters have already fired.
 * The order of preference for shooting is Left, then Middle, then Right.
 */
public class PatternLaunchCommand extends InstantCommand {

    private final ShooterSubsystem shooter;
    private final char colorToShoot; // 'G' for Green, 'P' for Purple

    // Static state to track the sequence across multiple command calls.
    private static Pattern currentPattern = Pattern.GPP; // Default pattern
    private static final boolean[] shotPositions = new boolean[3]; // Index 0:Left, 1:Middle, 2:Right

    // detect pattern using limelight

    // pickup balls and catagorize into an string  GPP, PGP, PPG

    // create a supply int with  either purple or green that gets incrimented from values from a color senseor based on how many of each char

    //





    /**
     * Creates a command to shoot the next available ball of a specific color.
     *
     * @param shooter      The shooter subsystem.
     * @param colorToShoot The color of the ball to shoot ('G' or 'P').
     */
    public PatternLaunchCommand(ShooterSubsystem shooter, char colorToShoot) {
        this.shooter = shooter;
        this.colorToShoot = colorToShoot;
        addRequirements(shooter);
    }

    @Override
    public void initialize() {
        // Find the first available (un-shot) shooter position that matches the desired color.
        for (int i = 0; i < 3; i++) {
            char colorAtPosition = currentPattern.name().charAt(i);

            // Check if this position has the right color and hasn't been shot yet.
            if (colorAtPosition == colorToShoot && !shotPositions[i]) {
                // Fire the corresponding shooter.
                new ManualLaunchCommand(shooter, i);
                // Mark this position as shot.
                shotPositions[i] = true;
                // Exit after firing one ball.
                break;
            }
        }
    }

    public static void setPattern(Pattern pattern) {
        currentPattern = pattern;
        resetSequence();
    }

    public static void resetSequence() {
        for (int i = 0; i < 3; i++) {
            shotPositions[i] = false;
        }
    }

    public static boolean isSequenceComplete() {
        for (int i = 0; i < 3; i++) {
            if (!shotPositions[i]) {
                return false;
            }
        }
        return true;
    }
}
