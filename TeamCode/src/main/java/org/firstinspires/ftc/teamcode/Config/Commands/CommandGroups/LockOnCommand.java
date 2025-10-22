package org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.Config.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.Config.Subsystems.LimeLightSubsystem;

public class LockOnCommand extends SequentialCommandGroup {
    LimeLightSubsystem limeLightSubsystem;
    DriveSubsystem driveSubsystem;
    public LockOnCommand(LimeLightSubsystem limeLightSubsystem, DriveSubsystem driveSubsystem){
        this.limeLightSubsystem = limeLightSubsystem;
        this.driveSubsystem = driveSubsystem;
    }
    @Override
    public void initialize() {

    }



}
