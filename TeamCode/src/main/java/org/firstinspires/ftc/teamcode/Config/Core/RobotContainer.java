package org.firstinspires.ftc.teamcode.Config.Core;


import static org.firstinspires.ftc.teamcode.Config.Core.Util.Opmode.AUTONOMOUS;
import static org.firstinspires.ftc.teamcode.Config.Core.Util.Opmode.TELEOP;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
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
import org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups.PatternLaunchCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups.MasterLaunchCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups.ResetAllCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups.StateCommands.IntakingStateCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.IntakeControlCommand;
//import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ResetIMUCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.LoadHumanPlayerCommand;
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
    private double headingPower =0 ;
    int tagID = 0;

    public Alliance alliance;
    private Opmode opmode;
    public CommandScheduler cs = CommandScheduler.getInstance();

    //CONSTRUCTOR FOR AUTO TEST
    public RobotContainer(HardwareMap hardwareMap, Alliance alliance, Pose startPose){
        this.opmode = AUTONOMOUS;
        this.alliance = alliance;
        follower = Constants.createFollower(hardwareMap);


        limeLightSubsystem = new LimeLightSubsystem(hardwareMap, alliance);
        intakeSubsystem = new IntakeSubsystem(hardwareMap);
        shooterSubsystem = new ShooterSubsystem(hardwareMap);
        patternSubsystem = new PatternSubsystem();
        shooterSubsystem = new ShooterSubsystem((hardwareMap));
        lmecSubsystem = new LMECSubsystem(hardwareMap);

        follower.setStartingPose(startPose);

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
//        colorSubsystem = new ColorSubsystem(hardwareMap);
        patternSubsystem = new PatternSubsystem();


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

    // ------------------------------- STATE MANAGER -------------------------------


    // -------------------- State Manager --------------------
    private void applyState() {

        // schedule commands only on state entry
            switch (robotState) {

                case INTAKING:
                    intakeSubsystem.intakeSpeed(1);
                    break;
                case AIMING:
                    shooterSubsystem.setShooterSpeed(shooterSubsystem.calculatePowerPercentage(limeLightSubsystem.getDistance()));
                    break;
                case LOADING:
                    shooterSubsystem.setShooterSpeed(-0.35);
                    shooterSubsystem.loadManual(ShooterPosition.LOAD);
                    break;
                case SHOOTING:
                    shooterSubsystem.setShooterSpeed(shooterSubsystem.calculatePowerPercentage(limeLightSubsystem.getDistance()));
//                    lmecSubsystem.lockMechanum();
                    break;
                case NONE:
                    intakeSubsystem.stop();
                    shooterSubsystem.setShooterSpeed(0);
                    intakeSubsystem.intakeSpeed(0);
//                    lmecSubsystem.unlockMechanum();
                    break;
            }


        double yaw = limeLightSubsystem.getYawOffset();
        if (limeLightSubsystem.getAllianceAprilTag() != null) {
            headingPower = Range.clip((yaw / 24) * 0.4, -0.5, 0.5);
        } else {
            headingPower = driverPad.getRightX() * 0.65;
        }

        // ---------------- Manual Drive Control ----------------
        double forward = driverPad.getLeftY();
        double strafe = -driverPad.getLeftX();
        double rotation;

        switch (robotState) {
            case AIMING:
                rotation = -headingPower; // heading lock
                break;
            case SHOOTING:
                rotation = 0; // don't rotate
                break;
            default:
                rotation = -driverPad.getRightX() * 0.4;
                break;
        }

        follower.setTeleOpDrive(forward, strafe, rotation, false);
    }

    public void periodic() {
        follower.update();

        telemetry.addData("Yaw", limeLightSubsystem.getYawOffset());
        telemetry.addData("Distance", limeLightSubsystem.getDistance());
        telemetry.addData("Shooter %", shooterSubsystem.calculatePowerPercentage(limeLightSubsystem.getDistance()));

        applyState();
        cs.run();
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
                .whileHeld(new InstantCommand(() -> setState(RobotStates.AIMING)))
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

        operatorPad.getGamepadButton(GamepadKeys.Button.A)
                .whileHeld(new LoadHumanPlayerCommand(shooterSubsystem))
                .whenReleased(new ResetAllCommand(shooterSubsystem, intakeSubsystem)
        );

        //Right trigger hold, intake
        new Trigger(() -> driverPad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0)
                .whenActive(new InstantCommand(() -> setState(RobotStates.INTAKING)))
                .whenInactive(new InstantCommand(() -> setState(RobotStates.NONE)));

        //Left trigger hold, lock mecanum TODO make sure the when inactive doesnt interfere
//        new Trigger(() -> driverPad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0)
//                .whenActive(new InstantCommand(() -> setState(RobotStates.INTAKING)))
//                .whenInactive(new InstantCommand(() -> setState(RobotStates.NONE)));
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

}