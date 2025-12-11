package org.firstinspires.ftc.teamcode.Config.Core;


import static org.firstinspires.ftc.teamcode.Config.Core.Util.Opmode.AUTONOMOUS;
import static org.firstinspires.ftc.teamcode.Config.Core.Util.Opmode.TELEOP;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.util.Timing;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups.MasterLaunchCommand;
//import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ResetIMUCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups.StaggeredShotCommand;
import org.firstinspires.ftc.teamcode.Config.Core.Util.Alliance;
import org.firstinspires.ftc.teamcode.Config.Core.Util.Opmode;

import org.firstinspires.ftc.teamcode.Config.Core.Util.RobotStates;
import org.firstinspires.ftc.teamcode.Config.Core.Util.ShooterPosition;
import org.firstinspires.ftc.teamcode.Config.Subsystems.ColorSubsystem;
import org.firstinspires.ftc.teamcode.Config.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Config.Subsystems.LMECSubsystem;
import org.firstinspires.ftc.teamcode.Config.Subsystems.LimeLightSubsystem;
import org.firstinspires.ftc.teamcode.Config.Subsystems.PatternSubsystem;
import org.firstinspires.ftc.teamcode.Config.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.Config.pedroPathing.Constants;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;


public class RobotContainer {
    public LimeLightSubsystem limeLightSubsystem;
    public LMECSubsystem lmecSubsystem;
    public ShooterSubsystem shooterSubsystem;
    public IntakeSubsystem intakeSubsystem;
    public ColorSubsystem colorSubsystem;
    public Follower follower;
    public PatternSubsystem patternSubsystem;

    protected GamepadEx driverPad;
    protected GamepadEx operatorPad;

    Telemetry telemetry;
    private double headingPower = 0;
    private double rotation;
    Object[] ballColors;
    ShooterPosition[] sequence = new ShooterPosition[3];
    Object[] pattern;
    Timing.Timer time;
    double speed;

    public Alliance alliance;
    private Opmode opmode;
    private double distance;
    public CommandScheduler cs = CommandScheduler.getInstance();
    private LLResultTypes.FiducialResult tag;

    //CONSTRUCTOR FOR AUTO TEST
    public RobotContainer(HardwareMap hardwareMap, Alliance alliance, Telemetry telemetry){
        this.opmode = AUTONOMOUS;
        this.alliance = alliance;
        this.telemetry = telemetry;

        follower = Constants.createFollower(hardwareMap);

        limeLightSubsystem = new LimeLightSubsystem(hardwareMap, alliance);
        intakeSubsystem = new IntakeSubsystem(hardwareMap);
        shooterSubsystem = new ShooterSubsystem(hardwareMap);
        patternSubsystem = new PatternSubsystem();
        lmecSubsystem = new LMECSubsystem(hardwareMap);
        colorSubsystem = new ColorSubsystem(hardwareMap);

        CommandScheduler.getInstance().registerSubsystem(
                limeLightSubsystem,
                intakeSubsystem,
                shooterSubsystem,
                patternSubsystem);
    }

    public RobotContainer(HardwareMap hardwareMap, Gamepad driver, Gamepad operator, Alliance alliance, Telemetry telemetry){
        this.opmode = TELEOP;
        this.alliance = alliance;
        this.driverPad = new GamepadEx(driver);
        this.operatorPad = new GamepadEx(operator);
        this.telemetry = telemetry;
        follower = Constants.createFollower(hardwareMap);

        limeLightSubsystem = new LimeLightSubsystem(hardwareMap, alliance);
        intakeSubsystem = new IntakeSubsystem(hardwareMap);
        shooterSubsystem = new ShooterSubsystem(hardwareMap);
        colorSubsystem = new ColorSubsystem(hardwareMap);
        patternSubsystem = new PatternSubsystem();
        lmecSubsystem = new LMECSubsystem(hardwareMap);


        follower.setStartingPose(new Pose(0,0,0));
        CommandScheduler.getInstance().registerSubsystem(
                limeLightSubsystem,
                intakeSubsystem,
                shooterSubsystem,
                patternSubsystem);
    }


    // ------------------------------- STATE MANAGER -------------------------------

    private void applyState() {

        // schedule commands only on state entry
        switch (robotState) {
            case INTAKING:
                intakeSubsystem.intakeSpeed(1);
                break;
            case LOADING:
                shooterSubsystem.setShooterSpeed(-0.3);
                intakeSubsystem.stop();
                break;
            case OUTAKING:
                intakeSubsystem.intakeSpeed(-1);
                break;
            case AIMING:
                shooterSubsystem.setShooterVelocity(speed);
                intakeSubsystem.stop();
                break;
            case SHOOTING:
                shooterSubsystem.setShooterVelocity(speed);
                intakeSubsystem.stop();
                if (shooterSubsystem.atVelocity(speed))
                    driverPad.gamepad.rumble(100);
                break;
            case NONE:
                shooterSubsystem.setShooterSpeed(0);
//                intakeSubsystem.intakeSpeed(-0.5);
                intakeSubsystem.stop();
//                lmecSubsystem.unlockMechanum();
                break;
        }

        double yawNormalized = limeLightSubsystem.getYawOffset(tag) / 24;

        if (tag != null) {
            headingPower = 0.25 * Math.pow(Math.abs(yawNormalized), 0.6 ) * Math.signum(yawNormalized);
        } else {
            headingPower = driverPad.getRightX() * 0.7;
        }

        // ---------------- Manual Drive Control ----------------
        double forward = driverPad.getLeftY();

        switch (robotState) {
            case AIMING:
                rotation = -headingPower; // heading lock
                break;
            case SHOOTING:
                rotation = 0; // don't rotate
                break;
            default:
                rotation = -driverPad.getRightX() * 0.7;
                break;
        }

        if (lmecSubsystem.state != LMECSubsystem.LockState.LOCKED)
            follower.setTeleOpDrive(forward, -driverPad.getLeftX(), rotation, false);
        else
            follower.setTeleOpDrive(forward, 0, rotation, true);


    }

    public void periodic() {
        tag = limeLightSubsystem.getAllianceAprilTag();
        distance = limeLightSubsystem.getDistance(tag);
        speed = shooterSubsystem.calculatePowerPercentage(distance);

        ballColors = colorSubsystem.getBallColors();

        patternSubsystem.setPattern(limeLightSubsystem.getPattern());

//
        pattern = patternSubsystem.getPattern();
//

        if (pattern[0]!= null) {
            for (int i = 0; i < 3; i++) {
                if (pattern[i].equals('g')) {
                    sequence[i] = colorSubsystem.getGreenLocation(ballColors);
                } else if (pattern[i].equals('p')) {
                    sequence[i] = colorSubsystem.getPurpleLocation(ballColors);
                }
            }
        }


        follower.update();
        tTel();


        if (opmode == TELEOP) {
            applyState();
        }

        cs.run();
    }
    public void aPeriodic() {
        aTel();
    }

    public void tStart(){
        follower.update();
        follower.startTeleopDrive();
        limeLightSubsystem.limeLightStart();
        shooterSubsystem.resetManual(ShooterPosition.ALL);
        robotState = RobotStates.NONE;
        ballColors = colorSubsystem.getBallColors();
//        patternSubsystem.setPattern(limeLightSubsystem.getPattern(tag));
        time = new Timing.Timer(1000, TimeUnit.MILLISECONDS);

    }

    public void teleOpControl(){

        // right bumber = shoot
        // left bumber = aim
        // Dpad down = shoot all
        // left trigger = LMEC
        // A = outtake

        //        driverPad.getGamepadButton(GamepadKeys.Button.BACK).whenPressed(
//                        new ResetIMUCommand();
//        );

        Supplier<ShooterPosition[]> object = () -> new ShooterPosition[] {ShooterPosition.MIDDLE,ShooterPosition.LEFT  ,ShooterPosition.RIGHT};

        //TODO leo look this here it should work if you uncommnet out this line of code it just doesnt properly build sequnce for some reason
//                Supplier<ShooterPosition[]> object = () -> sequence;

        // shoot auto
        driverPad.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(
                new StaggeredShotCommand(shooterSubsystem, ()-> (1.3* Math.pow(Range.clip(distance, 50, 100), 1.3)),object)
        );
        driverPad.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(
                new MasterLaunchCommand(shooterSubsystem, ShooterPosition.LEFT)
        );
        driverPad.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(
                new MasterLaunchCommand(shooterSubsystem, ShooterPosition.LEFT)
        );
        driverPad.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(
                new MasterLaunchCommand(shooterSubsystem, ShooterPosition.LEFT)
        );
        driverPad.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(
                new MasterLaunchCommand(shooterSubsystem, ShooterPosition.MIDDLE)
        );
        driverPad.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(
                new MasterLaunchCommand(shooterSubsystem, ShooterPosition.RIGHT)
        );



        //aim command limelight
        // @TODO set states for the loading and MasterLaunchCommand

        driverPad.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whileHeld(new InstantCommand(() -> setState(RobotStates.AIMING)))
                .whenReleased(new InstantCommand(() -> setState(RobotStates.NONE)));



        driverPad.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whileHeld(new InstantCommand(() -> setState(RobotStates.OUTAKING)))
                .whenReleased(new InstantCommand(() -> setState(RobotStates.NONE)));

        driverPad.getGamepadButton(GamepadKeys.Button.A)
                .whileHeld(
                        new ParallelCommandGroup(
                                new MasterLaunchCommand(shooterSubsystem, ShooterPosition.ALL, true),
                                new InstantCommand(() -> setState(RobotStates.LOADING))
                        )
                )
                .whenReleased(
                        new ParallelCommandGroup(
                                new MasterLaunchCommand(shooterSubsystem, ShooterPosition.ALL, false),
                                new InstantCommand(() -> setState(RobotStates.NONE))
                        )
                );

        //Right trigger hold, intake
        new Trigger(() -> driverPad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0)
                .whileActiveContinuous(
                        new ParallelCommandGroup(
                                new InstantCommand(() -> setState(RobotStates.INTAKING)),
                                new MasterLaunchCommand(shooterSubsystem, ShooterPosition.INTAKE, true)
                        )
                )
                .whenInactive(
                        new ParallelCommandGroup(
                                new InstantCommand(() -> setState(RobotStates.NONE)),
                                new MasterLaunchCommand(shooterSubsystem, ShooterPosition.INTAKE, false)
                        )
                );

        new Trigger(() -> driverPad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0)
                .whileActiveContinuous(
                        new InstantCommand(() -> lmecSubsystem.lockMechanum())
                )
                .whenInactive(
                        new InstantCommand(() -> lmecSubsystem.unlockMechanum())

                );
    }
    public RobotStates robotState = RobotStates.NONE;

    public void update() {
        CommandScheduler.getInstance().run();
    }
    public void end() {
        cs.reset();
    }

    public Follower getFollower(){
        return follower;
    }
    public Alliance getAlliance(){
        return alliance;
    }

    public void setState(RobotStates state) {
        // TARGET LOCK CHECK AIM → SHOOT
        if (state == RobotStates.AIMING && limeLightSubsystem.isLocked(tag))
            state  = RobotStates.SHOOTING;
        if (state == RobotStates.INTAKING  && colorSubsystem.isFull())
            state = RobotStates.NONE;
        if (robotState == RobotStates.INTAKING && state != RobotStates.INTAKING) {
            time = new Timing.Timer(1000, TimeUnit.MILLISECONDS);
            time.start();
        }
        robotState = state;
    }

    public RobotStates getState() {
        return robotState;
    }

    public void setStartingPose(Pose startingPose){
        follower.setStartingPose(startingPose);

    }

    public void aTel() {
        telemetry.addData("Yaw", limeLightSubsystem.getYawOffset(tag));
        telemetry.addData("Distance", distance);

        telemetry.addData("Shooter %", distance);
        telemetry.addData("shooter one", shooterSubsystem.getLaunchVelocity2());
        telemetry.addData("odom heading", follower.getHeading());

        telemetry.addData("state", getState());

        telemetry.update();
    }
    public void tTel() {

        telemetry.addData("state", getState());
//        telemetry.addData("Yaw", limeLightSubsystem.getYawOffset());

//        telemetry.addData("shooter vel", shooterSubsystem.getLaunchVelocity1());

        telemetry.addData("Distance", distance);
//        telemetry.addData("Shooter %", shooterSubsystem.calculatePowerPercentage(distance));
//        telemetry.addData("Shooter velocity", 1500 * shooterSubsystem.calculatePowerPercentage(distance));

//        telemetry.addData("Balls", colorSubsystem.getBallColors());

        telemetry.addData("pattern", patternSubsystem.getPattern()[0] );
        telemetry.addData("pattern", patternSubsystem.getPattern()[1] );
        telemetry.addData("pattern", patternSubsystem.getPattern()[2] );

        telemetry.addData("next ball color", patternSubsystem.getNextColor());
//        telemetry.addData("shot stagger", 2* Math.pow(distance, 1.3));

        telemetry.addData("next ball color", limeLightSubsystem.getPattern()[0]);

        telemetry.addData("Left", ballColors[0]);
        telemetry.addData("Middle", ballColors[1]);
        telemetry.addData("Right", ballColors[2]);
        telemetry.addData("purple location", colorSubsystem.getPurpleLocation(ballColors));
        telemetry.addData("green location", colorSubsystem.getGreenLocation(ballColors));

//        telemetry.addData("time", time.elapsedTime());
//        telemetry.addData("time", time.remainingTime());

//        telemetry.addData("left" , colorSubsystem.getLeft());
//        telemetry.addData("middle" , colorSubsystem.getMiddle());
//        telemetry.addData("right" , colorSubsystem.getRight());


        telemetry.addData("sequence", sequence[0] );
        telemetry.addData("sequence", sequence[1] );
        telemetry.addData("sequence", sequence[2] );
        telemetry.addData("pattern", pattern[0] );
        telemetry.addData("pattern", pattern[1] );
        telemetry.addData("pattern", pattern[2] );




//        telemetry.addData("total", colorSubsystem.getTotal());
    }

}
