package org.firstinspires.ftc.teamcode.Config.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import java.util.concurrent.atomic.AtomicInteger;

public class PatternSubsystem extends SubsystemBase {

    private int supplyGreen;
    private int supplyPurple;
    private final AtomicInteger shotCounter;
    private String pattern;

    public PatternSubsystem() {
        this.pattern = ""; // default empty until set
        this.shotCounter = new AtomicInteger(0);
    }

    // Counts purple balls in the given pattern
    public int getSupplyPurple(String pattern){
        int count = 0;
        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) == 'p') count++;
        }
        return count;
    }

    // Counts green balls in the given pattern
    public int getSupplyGreen(String pattern){
        int count = 0;
        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) == 'g') count++;
        }
        return count;
    }

    // Returns the next color we need to shoot based on current pattern
    public char getNextColor() {
        if (pattern == null || pattern.isEmpty()) return '0';
        int index = shotCounter.get() % pattern.length();
        return pattern.charAt(index);
    }

    // Records that a shot happened (so next call moves on)
    public void recordShot() {
        shotCounter.incrementAndGet();
    }

    // Reset to start over
    public void resetCounter() {
        shotCounter.set(0);
    }

    // Change pattern and reset sequence
    public void setPattern(String newPattern) {
        this.pattern = newPattern.toLowerCase();
        resetCounter();
        this.supplyGreen = getSupplyGreen(newPattern);
        this.supplyPurple = getSupplyPurple(newPattern);
    }

    public String getPattern() {
        return pattern;
    }

    public int getShotCount() {
        return shotCounter.get();
    }
}
