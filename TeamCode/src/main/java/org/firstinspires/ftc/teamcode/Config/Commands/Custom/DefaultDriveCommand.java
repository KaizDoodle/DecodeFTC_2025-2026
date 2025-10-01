//package org.firstinspires.ftc.teamcode.Config.Commands.Custom;
//
//import com.arcrobotics.ftclib.command.CommandBase;
//
//import org.firstinspires.ftc.robotcore.external.Telemetry;
//
//import java.util.function.DoubleSupplier;
//
//import org.firstinspires.ftc.teamcode.Config.Subsystems.DriveSubsystem;
//
//public class DefaultDriveCommand extends CommandBase {
//    DriveSubsystem driveSubsystem;
//    DoubleSupplier x, y, rx;
//    Telemetry telemetry;
//
//    public DefaultDriveCommand(DriveSubsystem driveSubsystem, DoubleSupplier inputY, DoubleSupplier inputX, DoubleSupplier inputRx) {
//        this.driveSubsystem = driveSubsystem;
//        this.x = inputX;
//        this.y = inputY;
//        this.rx = inputRx;
//        addRequirements(driveSubsystem);
//
//    }
//
//    @Override
//    public void execute() {
//        driveSubsystem.driveFieldCentric(x.getAsDouble() + getXModPower(), y.getAsDouble() + getYModPower(), rx.getAsDouble() + getRModPower());
//    }
//
//    public double getXModPower() {
//        return 0.0;
//    }
//
//    public double getYModPower() {
//        return 0.0;
//    }
//
//    public double getRModPower() {
//        return 0.0;
//    }
//
//
//}