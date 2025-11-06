package org.firstinspires.ftc.teamcode.Config.Core;


import static org.firstinspires.ftc.teamcode.Config.Core.Util.Opmode.AUTONOMOUS;
import static org.firstinspires.ftc.teamcode.Config.Core.Util.Opmode.TELEOP;

import androidx.loader.content.Loader;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.LLFieldMap;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups.LockOnCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups.PatternLaunchCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups.MasterLaunchCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups.ResetAllCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.IntakeControlCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.LMECControl;
//import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ResetIMUCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.LoadHumanPlayerCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ManualResetCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ShooterControllerCommand;
import org.firstinspires.ftc.teamcode.Config.Core.Util.Alliance;
import org.firstinspires.ftc.teamcode.Config.Core.Util.Opmode;

import org.firstinspires.ftc.teamcode.Config.Core.Util.RobotStates;
import org.firstinspires.ftc.teamcode.Config.Core.Util.ShooterPosition;
import org.firstinspires.ftc.teamcode.Config.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Config.Subsystems.LMECSubsystem;
import org.firstinspires.ftc.teamcode.Config.Subsystems.LimeLightSubsystem;
import org.firstinspires.ftc.teamcode.Config.Subsystems.PatternSubsystem;
import org.firstinspires.ftc.teamcode.Config.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.Config.pedroPathing.Constants;


public class RobotContainer {

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
    double headingPower =0 ;

    public Alliance alliance;
    private Opmode opmode;
    public CommandScheduler cs = CommandScheduler.getInstance();

    //CONSTRUCTOR FOR AUTO TEST
    public RobotContainer(HardwareMap hardwareMap, Alliance alliance, Pose startPose){
        this.opmode = AUTONOMOUS;
        this.alliance = alliance;
        follower = Constants.createFollower(hardwareMap);


        limeLightSubsystem = new LimeLightSubsystem(hardwareMap);
        intakeSubsystem = new IntakeSubsystem(hardwareMap);
        shooterSubsystem = new ShooterSubsystem(hardwareMap);
        patternSubsystem = new PatternSubsystem();
        shooterSubsystem = new ShooterSubsystem((hardwareMap));



//        intake = new IntakeSubsystem(hardwareMap, telemetry);

//        lmec = new LMECSubsystem(hardwareMap);

        follower.setStartingPose(startPose);

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

        limeLightSubsystem = new LimeLightSubsystem(hardwareMap);
        intakeSubsystem = new IntakeSubsystem(hardwareMap);
        shooterSubsystem = new ShooterSubsystem(hardwareMap);
//        colorSubsystem = new ColorSubsystem(hardwareMap);
        patternSubsystem = new PatternSubsystem();


        follower.setStartingPose(new Pose(0,0,0));
        CommandScheduler.getInstance().registerSubsystem(
                limeLightSubsystem,
                intakeSubsystem,
                shooterSubsystem,
                patternSubsystem);

    }
    public void aPeriodic() {

        follower.update();






//        t.addData("path", f.getCurrentPath());
//        e.periodic();
//        l.periodic();
//        i.periodic();
//        o.periodic();

//        t.update();
        CommandScheduler.getInstance().run();
    }
    public void periodic() {


        follower.update();
        int tagID = 0;
        if (alliance.equals( Alliance.BLUE))
            tagID = 20;
        if (alliance.equals( Alliance.RED))
            tagID = 24;

        LLResultTypes.FiducialResult tag = limeLightSubsystem.getAprilTag(tagID);

        double headingPower;
        double yaw = limeLightSubsystem.getYawOffset(tagID) -5 ;

        if (tag != null && tag.getFiducialId() == tagID) {
            // Tag detected: smoothly approach target rotation
            headingPower = (yaw / 24 ) * 0.4;
            headingPower = Range.clip(headingPower, -0.5, 0.5);
        } else{
            headingPower = driverPad.getRightX() * 0.65;
        }

        telemetry.addData("limelight yaw offset ",limeLightSubsystem.getYawOffset(tagID) );
        telemetry.addData("limelight get distance ",limeLightSubsystem.getDistance());
        telemetry.addData("limelight power percentage ", shooterSubsystem.calculatePowerPercentage(limeLightSubsystem.getDistance()));
        telemetry.addData("get shooter power ", shooterSubsystem.getLaunchVelocity());
        telemetry.addData("heading power", headingPower);

        switch (robotState) {
            case AIM:
                // Field-centric drive, rotation controlled by Limelight
                follower.setTeleOpDrive(driverPad.getLeftY(), -driverPad.getLeftX(), -headingPower, false);
//                shooterSubsystem.setShooterSpeed(shooterSubsystem.calculatePowerPercentage(limeLightSubsystem.getDistance()));
                break;
            case NONE:
            default:
                // Normal field-centric drive
                follower.setTeleOpDrive(driverPad.getLeftY(), -driverPad.getLeftX(), -driverPad.getRightX() * 0.65, false);
                shooterSubsystem.setShooterSpeed(0);

                break;

            case INTAKE:

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
        robotState = RobotStates.NONE;
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
                new SequentialCommandGroup(
//                        new InstantCommand(() -> setState(RobotStates.AIM)),
                        new MasterLaunchCommand(shooterSubsystem, ShooterPosition.ALL)
                )
        );


        //aim command limelight
        // @TODO set states for the loading and MasterLaunchCommand

        driverPad.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whileHeld(new InstantCommand(() -> setState(RobotStates.AIM)))
                .whenReleased(new InstantCommand(() -> setState(RobotStates.NONE)));

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
                .whenActive(new IntakeControlCommand(intakeSubsystem, -1))
                    .whenInactive(new IntakeControlCommand(intakeSubsystem, 0));
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
        robotState = state;
    }

    public RobotStates getState() {
        return robotState;
    }

//    public Follower getFollower() {
//        return follower;
//    }

}