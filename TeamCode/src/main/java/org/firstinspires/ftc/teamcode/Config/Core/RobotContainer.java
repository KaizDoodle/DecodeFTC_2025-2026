package org.firstinspires.ftc.teamcode.Config.Core;


import static org.firstinspires.ftc.teamcode.Config.Core.Util.Opmode.AUTONOMOUS;
import static org.firstinspires.ftc.teamcode.Config.Core.Util.Opmode.TELEOP;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.LMECControl;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ShooterControllerCommand;
import org.firstinspires.ftc.teamcode.Config.Core.Util.Alliance;
import org.firstinspires.ftc.teamcode.Config.Core.Util.Opmode;

import org.firstinspires.ftc.teamcode.Config.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.Config.Subsystems.LMECSubsystem;
import org.firstinspires.ftc.teamcode.Config.Subsystems.LimeLightSubsystem;
import org.firstinspires.ftc.teamcode.Config.Subsystems.ShooterSubsystem;


public class RobotContainer {

    public DriveSubsystem drive;
    public LimeLightSubsystem limeLightSubsystem;
    public LMECSubsystem lmec;
    public ShooterSubsystem shooterSubsystem;
    public Follower follower;
    protected GamepadEx driverPad;
    protected GamepadEx operatorPad;


    Telemetry telemetry;


    private Alliance alliance;
    private Opmode opmode;
    public CommandScheduler cs = CommandScheduler.getInstance();

    public enum FSMStates {
        INTAKE,
        HANG,
        OUTTAKE,
        SPECIMEN,
        FOLD,
        NONE
    }
    //CONSTRUCTOR FOR AUTO TEST
    public RobotContainer(HardwareMap hardwareMap, Alliance alliance){
        this.opmode = AUTONOMOUS;
        this.alliance = alliance;


//        follower = Constants.createFollower(hardwareMap);
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

        limeLightSubsystem = new LimeLightSubsystem(hardwareMap);
//        follower = Constants.createFollower(hardwareMap);
        drive = new DriveSubsystem(hardwareMap, follower);

//        intake = new IntakeSubsystem(hardwareMap, telemetry);
//        wrist = new WristSubsystem(hardwareMap, telemetry);
//        slides = new SlideSubsystem(hardwareMap);
//        lmec = new LMECSubsystem(hardwareMap);
        shooterSubsystem = new ShooterSubsystem(hardwareMap);

//        follower.setStartingPose(new Pose(0,0,0));
        CommandScheduler.getInstance().registerSubsystem();

    }

    public void periodic() {
//        if (lmec.getState() == LMECSubsystem.LockState.UNLOCKED)
//            follower.setTeleOpDrive(driverPad.getLeftY(), -driverPad.getLeftX(), -driverPad.getRightX() * 0.5 , false);
//        if (lmec.getState() == LMECSubsystem.LockState.LOCKED)
//            follower.setTeleOpDrive(driverPad.getLeftY(), 0, -driverPad.getRightX() * 0.8 , false);


//        t.addData("path", f.getCurrentPath());
//        e.periodic();
//        l.periodic();
//        i.periodic();
//        o.periodic();
//        follower.update();
//        t.update();
        CommandScheduler.getInstance().run();
    }
    public void tStart(){
//        follower.startTeleopDrive();
        limeLightSubsystem.limeLightStart();
    }

    public void teleOpControl(){

        // right bumber = shoot
        // left bumber = aim
        // Dpad down = shoot all
        // left trigger = LMEC
        // A = outtake

        new Trigger(() -> driverPad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0).whenActive(
                new LMECControl(lmec, true )
        );


        driverPad.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(new ShooterControllerCommand(shooterSubsystem, 0));

    }
    public FSMStates robotState = FSMStates.NONE;

    public void update() {
        CommandScheduler.getInstance().run();
    }
    public void end() {
        cs.reset();
    }

    public void setState(FSMStates state) {
        robotState = state;
    }

    public FSMStates getState() {
        return robotState;
    }

//    public Follower getFollower() {
//        return follower;
//    }

}