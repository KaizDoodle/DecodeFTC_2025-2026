package org.firstinspires.ftc.teamcode.Config.Core;


import static org.firstinspires.ftc.teamcode.Config.Core.Util.Opmode.AUTONOMOUS;
import static org.firstinspires.ftc.teamcode.Config.Core.Util.Opmode.TELEOP;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups.LockOnCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups.PatternLaunchCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups.MasterLaunchCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.IntakeControlCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.LMECControl;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ShooterControllerCommand;
import org.firstinspires.ftc.teamcode.Config.Core.Util.Alliance;
import org.firstinspires.ftc.teamcode.Config.Core.Util.Opmode;

import org.firstinspires.ftc.teamcode.Config.Core.Util.Pattern;
import org.firstinspires.ftc.teamcode.Config.Core.Util.RobotStates;
import org.firstinspires.ftc.teamcode.Config.Core.Util.ShooterPosition;
import org.firstinspires.ftc.teamcode.Config.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.Config.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Config.Subsystems.LMECSubsystem;
import org.firstinspires.ftc.teamcode.Config.Subsystems.LimeLightSubsystem;
import org.firstinspires.ftc.teamcode.Config.Subsystems.PatternSubsystem;
import org.firstinspires.ftc.teamcode.Config.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.Config.pedroPathing.Constants;


public class RobotContainer {

    public DriveSubsystem driveSubsystem;
    public LimeLightSubsystem limeLightSubsystem;
    public LMECSubsystem lmecSubsystem;
    public ShooterSubsystem shooterSubsystem;
    public IntakeSubsystem intakeSubsystem;
//    public ColorSubsystem colorSubsystem;
    public Follower follower;
    public PatternSubsystem patternSubsystem;

    protected GamepadEx driverPad;
    protected GamepadEx operatorPad;

    Telemetry telemetry;

    private Alliance alliance;
    private Opmode opmode;
    public CommandScheduler cs = CommandScheduler.getInstance();

    //CONSTRUCTOR FOR AUTO TEST
    public RobotContainer(HardwareMap hardwareMap, Alliance alliance){
        this.opmode = AUTONOMOUS;
        this.alliance = alliance;


        driveSubsystem = new DriveSubsystem(follower);
        limeLightSubsystem = new LimeLightSubsystem(hardwareMap);

//        intake = new IntakeSubsystem(hardwareMap, telemetry);

//        lmec = new LMECSubsystem(hardwareMap);
        shooterSubsystem = new ShooterSubsystem((hardwareMap));

        follower.setStartingPose(new Pose(0,0,0));
        CommandScheduler.getInstance().registerSubsystem();

    }

    public RobotContainer(HardwareMap hardwareMap, Gamepad driver, Gamepad operator, Alliance alliance, Telemetry telemetry){
        this.opmode = TELEOP;
        this.alliance = alliance;
        this.driverPad = new GamepadEx(driver);
        this.operatorPad = new GamepadEx(operator);
        this.telemetry = telemetry;
        follower = Constants.createFollower(hardwareMap);

        limeLightSubsystem = new LimeLightSubsystem(hardwareMap);
        driveSubsystem = new DriveSubsystem(follower);
        intakeSubsystem = new IntakeSubsystem(hardwareMap);
        shooterSubsystem = new ShooterSubsystem(hardwareMap);
//        colorSubsystem = new ColorSubsystem(hardwareMap);
        patternSubsystem = new PatternSubsystem();


        follower.setStartingPose(new Pose(0,0,0));
        CommandScheduler.getInstance().registerSubsystem(
                driveSubsystem,
                limeLightSubsystem,
                intakeSubsystem,
                shooterSubsystem,
                patternSubsystem);

    }

    public void periodic() {
        follower.update();
//
//        double headingError;
//        double kP = 0.03; // <-- tune this value between 0.02–0.05 for your bot
//        rotationPower = yawError * kP;
//
//        // Clamp to avoid over-rotation
//        rotationPower = Math.max(-1, Math.min(1, rotationPower));

        switch (robotState) {
            case AIM:
                // Field-centric drive, rotation controlled by Limelight
                follower.setTeleOpDrive(driverPad.getLeftY(), driverPad.getLeftX(), limeLightSubsystem.getYawOffset(), false);
                break;
            case NONE:
            default:
                // Normal field-centric drive
                follower.setTeleOpDrive(driverPad.getLeftY(), driverPad.getLeftX(), -driverPad.getRightX() * 0.65, false);
                break;
        }


//        t.addData("path", f.getCurrentPath());
//        e.periodic();
//        l.periodic();
//        i.periodic();
//        o.periodic();

//        t.update();
        CommandScheduler.getInstance().run();
    }
    public void tStart(){
        follower.update();
        follower.startTeleopDrive();
        limeLightSubsystem.limeLightStart();
        shooterSubsystem.resetManual(ShooterPosition.ALL);
    }

    public void teleOpControl(){

        // right bumber = shoot
        // left bumber = aim
        // Dpad down = shoot all
        // left trigger = LMEC
        // A = outtake

        // shoot auto
        driverPad.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(
                new MasterLaunchCommand(shooterSubsystem, ShooterPosition.ALL)
        );
        //aim command limelight
        driverPad.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whileHeld(
                new LockOnCommand(limeLightSubsystem, driveSubsystem)
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

        driverPad.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(
                new PatternLaunchCommand(shooterSubsystem, patternSubsystem.getNextColor())
        );

        //Right trigger hold, intake
        new Trigger(() -> driverPad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0)
                .whenActive(new IntakeControlCommand(intakeSubsystem, 1))
                .whenInactive(new IntakeControlCommand(intakeSubsystem, 0));

        //Left trigger hold, lock mecanum
        new Trigger(() -> driverPad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0)
                .whenActive(new LMECControl(lmecSubsystem, true ))
                .whenInactive(new LMECControl(lmecSubsystem, false));

        driverPad.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(new ShooterControllerCommand(shooterSubsystem, 1));

    }
    public RobotStates robotState = RobotStates.NONE;

    public void update() {
        CommandScheduler.getInstance().run();
    }
    public void end() {
        cs.reset();
    }

    public void setState(RobotStates state) {
        robotState = state;
    }

    public RobotStates getState() {
        return robotState;
    }

//    public Follower getFollower() {
//        return follower;
//    }

}