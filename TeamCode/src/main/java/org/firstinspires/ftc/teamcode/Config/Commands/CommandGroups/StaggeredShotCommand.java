package org.firstinspires.ftc.teamcode.Config.Commands.CommandGroups;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.Config.Commands.Custom.DynamicWaitCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ManualCageControlCommand;
import org.firstinspires.ftc.teamcode.Config.Commands.Custom.ManualResetCommand;
import org.firstinspires.ftc.teamcode.Config.Core.Util.ShooterPosition;
import org.firstinspires.ftc.teamcode.Config.Subsystems.LimeLightSubsystem;
import org.firstinspires.ftc.teamcode.Config.Subsystems.ShooterSubsystem;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public class StaggeredShotCommand extends SequentialCommandGroup {
    ShooterPosition[] shooterPosition;


    public StaggeredShotCommand(ShooterSubsystem shooter, DoubleSupplier time , Supplier<ShooterPosition[]> seqSupplier) {

        ShooterPosition[] shooterPosition = seqSupplier.get();   // <-- NOW updated every time the command starts

        // Build the staggered sequence at runtime
        addCommands(
                new ParallelCommandGroup(
                        new SequentialCommandGroup(
                                new MasterLaunchCommand(shooter, () -> indexClosure(seqSupplier,0).get())
                        ),
                        new SequentialCommandGroup(
                                new DynamicWaitCommand(time),
                                new MasterLaunchCommand(shooter, () -> indexClosure(seqSupplier,1 ).get())
                        ),
                        new SequentialCommandGroup(
                                new DynamicWaitCommand(time, 2),
                                new MasterLaunchCommand(shooter,() ->   indexClosure(seqSupplier,2).get())
                        )
                )
        );

//        super.initialize();
    }

    public Supplier<ShooterPosition> indexClosure(Supplier<ShooterPosition[]> shooterPositions, int index){
        return () -> shooterPositions.get()[index];
    }

    public StaggeredShotCommand(ShooterSubsystem shooter, DoubleSupplier time) {
        this(shooter,time, ()-> new ShooterPosition[]{ShooterPosition.LEFT,ShooterPosition.MIDDLE,ShooterPosition.RIGHT});
    }





}