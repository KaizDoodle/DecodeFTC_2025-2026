package org.firstinspires.ftc.teamcode.Config.Commands.Custom;
import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.Config.Subsystems.LMECSubsystem;

public class LMECControl extends InstantCommand {

    LMECSubsystem lmecSubsystem;
    boolean locked;


    public LMECControl(LMECSubsystem lmecSubsystem, boolean locked ) {
        this.lmecSubsystem  = lmecSubsystem;
        this.locked = locked;
        addRequirements(lmecSubsystem);
    }

    @Override
    public void initialize() {
        if (locked)
            lmecSubsystem.lockMechanum();
        else
            lmecSubsystem.unlockMechanum();
    }
}