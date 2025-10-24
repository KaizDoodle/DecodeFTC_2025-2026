package org.firstinspires.ftc.teamcode.Config.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import org.firstinspires.ftc.teamcode.Config.Core.Util.Pattern;

import java.util.concurrent.atomic.AtomicInteger;

public class PatternSubsystem extends SubsystemBase {

    int supplyGreen;
    int supplyPurple;
    public AtomicInteger shotCounter;

    String pattern;

    char getNextColor;
    static int index;

    public PatternSubsystem() {

    }

    public int getSupplyPurple(String pattern){
        int count  = 0;

        for (int i = 0; i < pattern.length(); i++){
            if (pattern.charAt(i) == 'p'){
                count++;
            }
        }
        return count;
    }

    public int getSupplyGreen(String pattern){
        int count  = 0;

        for (int i = 0; i < pattern.length(); i++){
            if (pattern.charAt(i) == 'p'){
                count++;
            }
        }
        return count;
    }

    // gets a pattern in the parameters and returns the next color of the pattern
    public char getNextColor() {
        if (pattern == null || pattern.isEmpty()) return '0';
        int index = shotCounter.get() % pattern.length();
        return pattern.charAt(index);
    }

    // increments the shot count after a successful launch
    public void recordShot() {
        shotCounter.incrementAndGet();
    }

    // reset shot counter if needed
    public void resetCounter() {
        shotCounter.set(0);
    }

    // allow pattern update at runtime
    public void setPattern(String newPattern) {
        this.pattern = newPattern.toLowerCase();
        resetCounter();
    }

    // for debugging
    public int getShotCount() {
        return shotCounter.get();
    }


    // Method to convert the string into individual chars
    // getPattern command to get it from the limelight, need a variable to be changed
    // create a variable called supply to determine how much of each ball we currently have
    // create a variable called getSupply to figure out how much of each ball we need to get
    // create a variable called getNextColor to figure out what color we need next

}
