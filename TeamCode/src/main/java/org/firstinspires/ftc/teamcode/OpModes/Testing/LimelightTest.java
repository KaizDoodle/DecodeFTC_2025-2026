package org.firstinspires.ftc.teamcode.OpModes.Testing;

import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ShooterControllerCommand;
import org.firstinspires.ftc.teamcode.Config.Core.RobotContainer;
import org.firstinspires.ftc.teamcode.Config.Core.Util.Alliance;
import org.firstinspires.ftc.teamcode.Config.Core.Util.OpModeCommand;
import org.firstinspires.ftc.teamcode.Config.Subsystems.ShooterSubsystem;

@Autonomous(name = "LimeLightTest", group = "Examples")

public class LimelightTest extends OpModeCommand {
    RobotContainer robot;
    @Override
    public void initialize() {
        robot = new RobotContainer(hardwareMap, gamepad1, gamepad2, Alliance.BLUE, telemetry);

    }
    @Override
    public void start(){
        robot.tStart();
    }

    @Override
    public void loop() {
        telemetry.addData("asd", robot.limeLightSubsystem.getDistance());
        telemetry.update();
        schedule(
                new RunCommand(robot::periodic),
                new SequentialCommandGroup(
                        new ShooterControllerCommand( robot.shooterSubsystem, robot.limeLightSubsystem.getDistance())

                )
        );
    }


}
