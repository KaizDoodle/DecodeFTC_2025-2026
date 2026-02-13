package org.firstinspires.ftc.teamcode.Config.Core;

import static org.firstinspires.ftc.teamcode.Config.Core.Util.Opmode.AUTONOMOUS;
import static org.firstinspires.ftc.teamcode.Config.Core.Util.Opmode.TELEOP;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups.MasterLaunchCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups.StaggeredShotCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.AimLockCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.DefaultDriveCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ResetIMUCommand;
import org.firstinspires.ftc.teamcode.Config.Core.Util.Alliance;
import org.firstinspires.ftc.teamcode.Config.Core.Util.Opmode;
import org.firstinspires.ftc.teamcode.Config.Core.Util.RobotStates;
import org.firstinspires.ftc.teamcode.Config.Core.Util.ShooterPosition;
import org.firstinspires.ftc.teamcode.Config.Subsystems.*; // Collapsed imports

import java.util.function.Supplier;

public class RobotContainer {

    // --- Tuning Constants ---
    private static final double DRIVE_SPEED_MULTIPLIER = 0.7;
    private static final double HEADING_P = 0.25; // Proportional gain for aiming
    private static final double HEADING_LOCK_SCALAR = 24.0;

    // --- Subsystems ---
    public LimeLightSubsystem limeLightSubsystem;
    public LMECSubsystem lmecSubsystem;
    public ShooterSubsystem shooterSubsystem;
    public IntakeSubsystem intakeSubsystem;
    public ColorSubsystem colorSubsystem;
    public PatternSubsystem patternSubsystem;
    public DriveSubsystem driveSubsystem;


    // --- Inputs ---
    protected GamepadEx driverPad;
    protected GamepadEx operatorPad;
    private final Telemetry telemetry;

    // --- State Variables ---
    public Alliance alliance;
    private final Opmode opmode;
    public RobotStates robotState = RobotStates.NONE;

    // Logic Variables
    private Object[] ballColors;
    private ShooterPosition[] sequence = new ShooterPosition[3];
    private LLResultTypes.FiducialResult currentTag;

    private boolean sortingMode = false;
    private boolean hasInitializedColors = false;
    private double targetShooterSpeed;
    private double distanceFromTag;
    private double shootingStaggerDelay;

    // --- Auto Constructor ---
    public RobotContainer(HardwareMap hardwareMap, Alliance alliance, Telemetry telemetry) {
        this.opmode = AUTONOMOUS;
        this.alliance = alliance;
        this.telemetry = telemetry;
        initSubsystems(hardwareMap);
    }

    // --- TeleOp Constructor ---
    public RobotContainer(HardwareMap hardwareMap, Gamepad driver, Gamepad operator, Alliance alliance, Telemetry telemetry) {
        this.opmode = TELEOP;
        this.alliance = alliance;
        this.telemetry = telemetry;
        this.driverPad = new GamepadEx(driver);
        this.operatorPad = new GamepadEx(operator);

        initSubsystems(hardwareMap);

        // TeleOp specific init
        driveSubsystem.setStartingPose(new Pose(0, 0, 0));
    }

    // --- Initialization Helper ---
    private void initSubsystems(HardwareMap hardwareMap) {
        limeLightSubsystem = new LimeLightSubsystem(hardwareMap, alliance);
        intakeSubsystem = new IntakeSubsystem(hardwareMap);
        shooterSubsystem = new ShooterSubsystem(hardwareMap);
        patternSubsystem = new PatternSubsystem();
        lmecSubsystem = new LMECSubsystem(hardwareMap);
        colorSubsystem = new ColorSubsystem(hardwareMap);
        driveSubsystem = new DriveSubsystem(hardwareMap);


        CommandScheduler.getInstance().registerSubsystem(
                limeLightSubsystem, intakeSubsystem, shooterSubsystem, patternSubsystem
        );
    }

    // =========================================================================
    //                            MAIN LOOPS
    // =========================================================================

    public void periodic() {
        // 1. Update Sensors
        updateSensorData();

        // 2. Logic Update
        patternSubsystem.setPattern(limeLightSubsystem.getPattern());

        // Ensure colors are read at least once
        if (!hasInitializedColors) {
            refreshColorData();
            hasInitializedColors = true;
        }

        sequence = patternSubsystem.buildSequence(ballColors);
        driveSubsystem.update(); // PedroPathing update

        // 3. State & Drive Logic (TeleOp Only)

        handleSubsystemState();


        // 4. Run Commands & Telemetry
        CommandScheduler.getInstance().run();
        printTelemetry();
    }

    public void aPeriodic() {
        limeLightSubsystem.getPattern(); // Ensure pattern is read
        printTelemetry();
    }

    // =========================================================================
    //                        LOGIC & CONTROL
    // =========================================================================

    private void updateSensorData() {
        currentTag = limeLightSubsystem.getAllianceAprilTag();
        distanceFromTag = limeLightSubsystem.getDistance(currentTag);

        // Calculate shooter speed based on distance
        targetShooterSpeed = shooterSubsystem.calculatePowerPercentage(distanceFromTag);

        // Calculate stagger delay based on distance & mode
        double distClipped = Range.clip(distanceFromTag, 50, 150);
        if (sortingMode) {
            shootingStaggerDelay = 2.5 * Math.pow(distClipped, 1.2);
        } else {
            shootingStaggerDelay = 0.7 * Math.pow(distClipped, 1.3);
        }
    }

    private void handleSubsystemState() {
        // Rumble feedback when aiming and at speed
        if (robotState == RobotStates.AIMING || robotState == RobotStates.SHOOTING) {
            if (shooterSubsystem.atVelocity(targetShooterSpeed)) {
                driverPad.gamepad.rumble(100);
            }
        }
        // State Machine
        switch (robotState) {
            case INTAKING:
                intakeSubsystem.intakeSpeed(1);
                break;
            case LOADING:
                shooterSubsystem.setShooterVelocity(-0.3);
                intakeSubsystem.stop();
                break;
            case OUTAKING:
                intakeSubsystem.intakeSpeed(-1);
                break;
            case AIMING:
                shooterSubsystem.setShooterVelocity(targetShooterSpeed);
                intakeSubsystem.stop();
                break;
            case SHOOTING:
//                driveSubsystem.holdPosition();
                shooterSubsystem.setShooterVelocity(targetShooterSpeed);
                intakeSubsystem.stop();
                break;
            case NONE:
            default:
                shooterSubsystem.setShooterVelocity(0);
                intakeSubsystem.intakeSpeed(-0.75); // Idle outtake speed
                break;
        }
    }

    public void teleOpControl() {
        // --- Driver Controls ---

        // RESET POSE
        driverPad.getGamepadButton(GamepadKeys.Button.BACK)
                .whenPressed(new InstantCommand(() -> driveSubsystem.setPose(driveSubsystem.getPose().withHeading(0))));

        // RESET IMU
        driverPad.getGamepadButton(GamepadKeys.Button.START)
                .whenPressed(new ResetIMUCommand(driveSubsystem.getFollower()));

        // SHOOTING (Right Bumper)
        driverPad.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(
                new StaggeredShotCommand(shooterSubsystem, () -> shootingStaggerDelay, this::getSequenceArray, false)
        );

        // MANUAL SHOOTING (D-Pad)
        driverPad.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(
                new MasterLaunchCommand(shooterSubsystem, ShooterPosition.LEFT));
        driverPad.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(
                new MasterLaunchCommand(shooterSubsystem, ShooterPosition.MIDDLE));
        driverPad.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(
                new MasterLaunchCommand(shooterSubsystem, ShooterPosition.RIGHT));

        // AIMING (Left Bumper)
        driverPad.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whileHeld(
                        new AimLockCommand(
                                limeLightSubsystem,
                                driveSubsystem,
                                () -> driverPad.getLeftY(),      // Forward
                                () -> -driverPad.getLeftX(),     // Strafe
                                () -> -driverPad.getRightX()     // Manual Turn Fallback
                        ).alongWith(new InstantCommand(() -> setState(RobotStates.AIMING)))
                )
                .whenReleased(new InstantCommand(() -> setState(RobotStates.NONE)));


        // SORTING TOGGLE (Y)
        driverPad.getGamepadButton(GamepadKeys.Button.Y)
                .toggleWhenActive(
                        new InstantCommand(() -> setSorting(true)),
                        new InstantCommand(() -> setSorting(false)));

        // OUTTAKE (D-Pad Down)
        driverPad.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whileHeld(new InstantCommand(() -> setState(RobotStates.OUTAKING)))
                .whenReleased(new InstantCommand(() -> setState(RobotStates.NONE)));

        // AMP/LOADING (A)
        driverPad.getGamepadButton(GamepadKeys.Button.A)
                .whileHeld(new ParallelCommandGroup(
                        new MasterLaunchCommand(shooterSubsystem, ShooterPosition.ALL, true),
                        new InstantCommand(() -> setState(RobotStates.LOADING))
                ))
                .whenReleased(new ParallelCommandGroup(
                        new MasterLaunchCommand(shooterSubsystem, ShooterPosition.ALL, false),
                        new InstantCommand(() -> setState(RobotStates.NONE))
                ));

        // INTAKE (Right Trigger)
        new Trigger(() -> driverPad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.1)
                .whileActiveContinuous(new ParallelCommandGroup(
                        new InstantCommand(() -> setState(RobotStates.INTAKING)),
                        new MasterLaunchCommand(shooterSubsystem, ShooterPosition.INTAKE, true)
                ))
                .whenInactive(new ParallelCommandGroup(
                        new InstantCommand(() -> setState(RobotStates.NONE)),
                        new MasterLaunchCommand(shooterSubsystem, ShooterPosition.INTAKE, false)
                ));

        // LMEC LOCK (Left Trigger)
        new Trigger(() -> driverPad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.1)
                .whileActiveContinuous(new InstantCommand(() -> lmecSubsystem.lockMechanum()))
                .whenInactive(new InstantCommand(() -> lmecSubsystem.unlockMechanum()));
    }

    // =========================================================================
    //                          HELPER METHODS
    // =========================================================================

    public void setState(RobotStates nextState) {
        // Logic: If aiming and we have a lock, auto-transition to SHOOTING
        if (nextState == RobotStates.AIMING && limeLightSubsystem.isLocked(currentTag)) {
            nextState = RobotStates.SHOOTING;
        }

        // Logic: On falling edge of INTAKING (when we stop intaking), refresh colors
        if (this.robotState == RobotStates.INTAKING && nextState != RobotStates.INTAKING) {
            refreshColorData();
        }


        // Logic: Full warning
        if (colorSubsystem.isFull() && this.robotState == RobotStates.INTAKING) {
            driverPad.gamepad.rumble(200);
        }

        this.robotState = nextState;
    }

    public void refreshColorData() {
        ballColors = colorSubsystem.getBallColors();
        sequence = patternSubsystem.buildSequence(ballColors);
    }

    public void setSorting(boolean sortingMode) {
        this.sortingMode = sortingMode;
    }

    // Used for start-up
    public void startTeleOp() {
        driveSubsystem.update();
        driveSubsystem.startTeleopDrive();
        limeLightSubsystem.limeLightStart();

        this.robotState = RobotStates.NONE;
        refreshColorData();

        // Register the Default Command
        driveSubsystem.setDefaultCommand(new DefaultDriveCommand(
                driveSubsystem,
                () -> driverPad.getLeftY(),   // Forward Supplier
                () -> -driverPad.getLeftX(),  // Strafe Supplier
                () -> {                       // Rotation Supplier with Logic
                    // If shooting, freeze rotation
                    if (robotState == RobotStates.SHOOTING) {
                        return 0;
                    }
                    // Standard driving rotation
                    return -driverPad.getRightX() * 0.7;
                }
        ));
    }

    // Used for auto start-up
    public void startAuto(Pose startingPose) {
        driveSubsystem.setStartingPose(startingPose);
        limeLightSubsystem.limeLightStart();
        shooterSubsystem.resetManual(ShooterPosition.ALL);

        this.robotState = RobotStates.NONE;
        refreshColorData();
        patternSubsystem.setPattern(limeLightSubsystem.getPattern());
    }

    private ShooterPosition[] getSequenceArray() {
        return sequence;
    }

    public Supplier<ShooterPosition[]> getSequence() {
        return this::getSequenceArray;
    }

    // =========================================================================
    //                             TELEMETRY
    // =========================================================================

    private void printTelemetry() {
        if (opmode == TELEOP) {

            telemetry.addData("State", robotState);
            telemetry.addData("Sorting Mode", sortingMode);
            telemetry.addData("Limelight Yaw", limeLightSubsystem.getYawOffset(currentTag));
//            telemetry.addData("tag locked ", limeLightSubsystem.isLocked(currentTag));

            telemetry.addLine();
            telemetry.addData("Distance", "%.2f", distanceFromTag);
            telemetry.addData("Shot Stagger", "%.2f", shootingStaggerDelay);
            telemetry.addData("Shooter Power %", "%.2f", targetShooterSpeed);
            telemetry.addData("Shooter Vel (Act/Tgt)", "%.0f / %.0f",
                    shooterSubsystem.getLaunchVelocity1(),
                    (2200 * targetShooterSpeed));
            telemetry.addLine();
            // Safety check for arrays before printing
            if (patternSubsystem.getPattern() != null && patternSubsystem.getPattern().length >= 3) {
                telemetry.addData("Pattern", "%s, %s, %s",
                        patternSubsystem.getPattern()[0],
                        patternSubsystem.getPattern()[1],
                        patternSubsystem.getPattern()[2]);
            }
        }

        telemetry.update();
    }

    // Getters
    public Alliance getAlliance() { return alliance; }
}