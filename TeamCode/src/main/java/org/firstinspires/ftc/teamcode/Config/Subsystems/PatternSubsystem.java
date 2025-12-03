package org.firstinspires.ftc.teamcode.Config.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import java.util.concurrent.atomic.AtomicInteger;

public class PatternSubsystem extends SubsystemBase {

    private final AtomicInteger shotCounter;
    private Object[] pattern;

    public PatternSubsystem() {
        this.pattern = new Object[3]; // default empty until set
        this.shotCounter = new AtomicInteger(0);
    }



    // Returns the next color we need to shoot based on current pattern
    public Object getNextColor() {
        if (pattern == null ) return '0';
        int index = shotCounter.get() % pattern.length;
        shotCounter.incrementAndGet();

        return pattern[index];
    }

    // Records that a shot happened (so next call moves on)
    public void recordShot() {
        shotCounter.incrementAndGet();
    }

    // Reset to start over
    public void resetCounter() {
        shotCounter.set(0);
    }

    // set the target pattern gotten from april tags
    public void setPattern(Object[] newPattern) {
        this.pattern = newPattern;
        resetCounter();
    }

    public Object[] getPattern() {
        return pattern;
    }

    public int getShotCount() {
        return shotCounter.get();
    }
}
