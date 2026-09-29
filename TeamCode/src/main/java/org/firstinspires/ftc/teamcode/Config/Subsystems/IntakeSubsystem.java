
package org.firstinspires.ftc.teamcode.Config.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Intake motor wrapper.
 *
 * <p><b>Status:</b> motor output is currently disabled (see the TODO below) because the intake
 * hardware was broken at the time of writing. Re-enable by uncommenting the {@code setPower} calls.
 */
public class IntakeSubsystem extends SubsystemBase {
    public DcMotor intakeMotor;
    public IntakeSubsystem(HardwareMap hardwareMap){
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
    }

    //TODO intake disabled because broken uncomment when fixed
    public void intakeSpeed(double power){
        intakeMotor.setPower(power);
    }

    public void stop(){
        intakeMotor.setPower(0);
    }

}
