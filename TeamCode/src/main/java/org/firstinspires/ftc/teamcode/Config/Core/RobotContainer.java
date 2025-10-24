package org.firstinspires.ftc.teamcode.Config.Core;


import static org.firstinspires.ftc.teamcode.Config.Core.Util.Opmode.AUTONOMOUS;
import static org.firstinspires.ftc.teamcode.Config.Core.Util.Opmode.TELEOP;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups.LockOnCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups.MasterLaunchCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.IntakeControlCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.LMECControl;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ManualLaunchCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ManualResetCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ShooterControllerCommand;
import org.firstinspires.ftc.teamcode.Config.Core.Util.Alliance;
import org.firstinspires.ftc.teamcode.Config.Core.Util.Opmode;

import org.firstinspires.ftc.teamcode.Config.Core.Util.RobotStates;
import org.firstinspires.ftc.teamcode.Config.Core.Util.ShooterPosition;
import org.firstinspires.ftc.teamcode.Config.Subsystems.ColorSubsystem;
import org.firstinspires.ftc.teamcode.Config.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.Config.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Config.Subsystems.LMECSubsystem;
import org.firstinspires.ftc.teamcode.Config.Subsystems.LimeLightSubsystem;
import org.firstinspires.ftc.teamcode.Config.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.Config.pedroPathing.Constants;


public class RobotContainer {

    public DriveSubsystem driveSubsystem;
    public LimeLightSubsystem limeLightSubsystem;
    public LMECSubsystem lmecSubsystem;
    public ShooterSubsystem shooterSubsystem;
    public IntakeSubsystem intakeSubsystem;
//    public ColorSubsystem colorSubsystem;
    public Follower followerSubsystem;
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


//        drive = new DriveSubsystem(hardwareMap, follower);
        limeLightSubsystem = new LimeLightSubsystem(hardwareMap);

//        intake = new IntakeSubsystem(hardwareMap, telemetry);

//        lmec = new LMECSubsystem(hardwareMap);
        shooterSubsystem = new ShooterSubsystem((hardwareMap));

//        follower.setStartingPose(new Pose(0,0,0));
        CommandScheduler.getInstance().registerSubsystem();

    }

    public RobotContainer(HardwareMap hardwareMap, Gamepad driver, Gamepad operator, Alliance alliance, Telemetry telemetry){
        this.opmode = TELEOP;
        this.alliance = alliance;
        this.driverPad = new GamepadEx(driver);
        this.operatorPad = new GamepadEx(operator);
        this.telemetry = telemetry;
        followerSubsystem = Constants.createFollower(hardwareMap);

        limeLightSubsystem = new LimeLightSubsystem(hardwareMap);
        driveSubsystem = new DriveSubsystem(hardwareMap, followerSubsystem);
        intakeSubsystem = new IntakeSubsystem(hardwareMap);
        shooterSubsystem = new ShooterSubsystem(hardwareMap);
//        colorSubsystem = new ColorSubsystem(hardwareMap);



        followerSubsystem.setStartingPose(new Pose(0,0,0));
        CommandScheduler.getInstance().registerSubsystem();

    }

    public void periodic() {
//        if (lmec.getState() == LMECSubsystem.LockState.UNLOCKED)
//            followerSubsystem.setTeleOpDrive(driverPad.getLeftY(), -driverPad.getLeftX(), -driverPad.getRightX() * 0.5 , false);
//        if (lmec.getState() == LMECSubsystem.LockState.LOCKED)
//            followerSubsystem.setTeleOpDrive(driverPad.getLeftY(), 0, -driverPad.getRightX() * 0.8 , false);


//        t.addData("path", f.getCurrentPath());
//        e.periodic();
//        l.periodic();
//        i.periodic();
//        o.periodic();
        followerSubsystem.update();
//        t.update();
        CommandScheduler.getInstance().run();
    }
    public void tStart(){
        followerSubsystem.startTeleopDrive();
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