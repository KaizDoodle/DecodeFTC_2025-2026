package org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.Config.Core.Util.Pattern;
import org.firstinspires.ftc.teamcode.Config.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Config.Subsystems.ShooterSubsystem;

public class PatternLaunchCommand extends SequentialCommandGroup {

    private static final long SHOT_DURATION_MS = 500;
    private static final long INTER_SHOT_DELAY_MS = 250;


    // detect pattern using limelight

    // pickup balls and catagorize into an string  GPP, PGP, PPG

    // create a supply int with  either purple or green that gets incrimented from values from a color senseor based on how many of each char

    //





    //@TODO LEO lets take out the wait command here and add it into the gamepad control maybe. lets have a static varible which holds the color of next shot or place in the sequnce and then then when the command is called it will just shoot the next in color in sequnce
    public PatternLaunchCommand(ShooterSubsystem shooter, IntakeSubsystem intake, Pattern pattern) {

        // Create the sequence of shots based on the pattern
        switch (pattern) {
            case GPP: // Green, Purple, Purple
                addCommands(
                        // Shoot Green
                        createShotCommand(shooter, shooter::shootGreen),
                        new WaitCommand(INTER_SHOT_DELAY_MS),
                        // Shoot Purple
                        createShotCommand(shooter, shooter::shootPurple),
                        new WaitCommand(INTER_SHOT_DELAY_MS),
                        // Shoot Purple
                        createShotCommand(shooter, shooter::shootPurple)
                );
                break;
            case PGP: // Purple, Green, Purple
                addCommands(
                        // Shoot Purple
                        createShotCommand(shooter, shooter::shootPurple),
                        new WaitCommand(INTER_SHOT_DELAY_MS),
                        // Shoot Green
                        createShotCommand(shooter, shooter::shootGreen),
                        new WaitCommand(INTER_SHOT_DELAY_MS),
                        // Shoot Purple
                        createShotCommand(shooter, shooter::shootPurple)
                );
                break;
            case PPG: // Purple, Purple, Green
                addCommands(
                        // Shoot Purple
                        createShotCommand(shooter, shooter::shootPurple),
                        new WaitCommand(INTER_SHOT_DELAY_MS),
                        // Shoot Purple
                        createShotCommand(shooter, shooter::shootPurple),
                        new WaitCommand(INTER_SHOT_DELAY_MS),
                        // Shoot Green
                        createShotCommand(shooter, shooter::shootGreen)
                );
                break;
        }
    }

    // Helper method to create a single shot sequence
    private SequentialCommandGroup createShotCommand(ShooterSubsystem shooter, Runnable shotAction) {
        return new SequentialCommandGroup(
                new InstantCommand(shotAction, shooter), // Open the cage
                new WaitCommand(SHOT_DURATION_MS),      // Wait for the ball to be shot
                new InstantCommand(shooter::resetManual, shooter) // Reset the cages
        );
    }
}
