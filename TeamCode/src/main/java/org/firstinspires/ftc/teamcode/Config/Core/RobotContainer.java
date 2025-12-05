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
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups.PatternLaunchCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups.MasterLaunchCommand;
//import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ResetIMUCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups.StaggeredShotCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ManualCageControlCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ManualResetCommand;
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


public class RobotContainer {
    int count = 0;
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
    int tagID = 0;
    Object l;
    Object m;
    Object r;

    double speed;

    public Alliance alliance;
    private Opmode opmode;
    public CommandScheduler cs = CommandScheduler.getInstance();

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

        if (alliance.equals( Alliance.BLUE))
            tagID = 20;
        if (alliance.equals( Alliance.RED))
            tagID = 24;


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

        if (alliance.equals( Alliance.BLUE))
            tagID = 20;
        if (alliance.equals( Alliance.RED))
            tagID = 24;


    }


    // ------------------------------- STATE MANAGER -------------------------------

    private void applyState() {
        speed = shooterSubsystem.calculatePowerPercentage(limeLightSubsystem.getDistance());

        // schedule commands only on state entry
        switch (robotState) {
            case INTAKING:
                intakeSubsystem.intakeSpeed(1);
                break;
            case LOADING:
                shooterSubsystem.setShooterSpeed(-0.3);
                break;
            case OUTAKING:
                intakeSubsystem.intakeSpeed(-1);
                break;
            case AIMING:
                shooterSubsystem.setShooterVelocity(speed);
                break;
            case SHOOTING:
                shooterSubsystem.setShooterVelocity(speed);
                lmecSubsystem.lockMechanum();
                if (shooterSubsystem.atVelocity(speed))
                    driverPad.gamepad.rumble(100);
                break;
            case NONE:
                intakeSubsystem.stop();
                shooterSubsystem.setShooterSpeed(0);
                intakeSubsystem.intakeSpeed(0);
                lmecSubsystem.unlockMechanum();
                break;
        }

        double yawNormalized = limeLightSubsystem.getYawOffset() / 24;

        if (limeLightSubsystem.getAllianceAprilTag() != null) {
            headingPower = 0.25 * Math.pow(Math.abs(yawNormalized), 0.6 ) * Math.signum(yawNormalized);
        } else {
            headingPower = driverPad.getRightX() * 0.7;
        }

        // ---------------- Manual Drive Control ----------------
        double forward = driverPad.getLeftY();
        double strafe = -driverPad.getLeftX();

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

        follower.setTeleOpDrive(forward, strafe, rotation, false);
    }

    public void periodic() {
        follower.update();
//        patternSubsystem.setPattern(limeLightSubsystem.getPattern());


//        telemetry.addData("Yaw", limeLightSubsystem.getYawOffset());
//        telemetry.addData("roataton", headingPower);
        telemetry.addData("state", getState());

        telemetry.addData("shooter one", shooterSubsystem.getLaunchVelocity1());
//        telemetry.addData("shooter two", shooterSubsystem.getLaunchVelocity2());
//        telemetry.addData("shooter three", shooterSubsystem.getLaunchVelocity3());

        telemetry.addData("Distance", limeLightSubsystem.getDistance());
        telemetry.addData("Shooter %", shooterSubsystem.calculatePowerPercentage(limeLightSubsystem.getDistance()));
        telemetry.addData("Shooter velocity", 1500 *shooterSubsystem.calculatePowerPercentage(limeLightSubsystem.getDistance()));

//        telemetry.addData("Balls", colorSubsystem.getBallColors());

        telemetry.addData("pattern", patternSubsystem.getPattern()[0] );
        telemetry.addData("pattern", patternSubsystem.getPattern()[1] );
        telemetry.addData("pattern", patternSubsystem.getPattern()[2] );
        telemetry.addData("next ball color", patternSubsystem.getNextColor());
        telemetry.addData("next ball color", patternSubsystem.getShotCount());

//        telemetry.addData("left", colorSubsystem.getBallColors()[0]);
//        telemetry.addData("Middle", colorSubsystem.getBallColors()[1]);
        telemetry.addData("Left", l);
        telemetry.addData("Middle", m);
        telemetry.addData("Right", r);

        telemetry.addData("count", count);



        if (opmode == TELEOP) {
            applyState();
        }
        cs.run();
    }
    public void aPeriodic() {
        telemetry.addData("Yaw", limeLightSubsystem.getYawOffset());
        telemetry.addData("Distance", limeLightSubsystem.getDistance());

        telemetry.addData("Shooter %", shooterSubsystem.calculatePowerPercentage(limeLightSubsystem.getDistance()));
        telemetry.addData("shooter one", shooterSubsystem.getLaunchVelocity2());


        telemetry.addData("odom heading", follower.getHeading());

        telemetry.addData("state", getState());



        telemetry.update();
    }

    public void tStart(){
        follower.update();
        follower.startTeleopDrive();
        limeLightSubsystem.limeLightStart();
        shooterSubsystem.resetManual(ShooterPosition.ALL);
        robotState = RobotStates.NONE;
//        patternSubsystem.setPattern(limeLightSubsystem.getPattern());

        l = colorSubsystem.getBallColors()[0];
        m = colorSubsystem.getBallColors()[1];
        r = colorSubsystem.getBallColors()[2];
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


        // shoot auto
        driverPad.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(
                new StaggeredShotCommand(shooterSubsystem, ()-> (10* Math.pow(limeLightSubsystem.getDistance(), 1.3)))
        ).whenReleased(new ManualResetCommand(shooterSubsystem, ShooterPosition.ALL)
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
        if (state == RobotStates.AIMING && limeLightSubsystem.isLocked())
            state  = RobotStates.SHOOTING;
        robotState = state;
    }

    public RobotStates getState() {
        return robotState;
    }

    public void setStartingPose(Pose startingPose){
        follower.setStartingPose(startingPose);

    }
}