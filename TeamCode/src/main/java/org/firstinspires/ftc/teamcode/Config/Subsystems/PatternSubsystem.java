package org.firstinspires.ftc.teamcode.Config.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import org.firstinspires.ftc.teamcode.Config.Core.Util.ShooterPosition;

public class PatternSubsystem extends SubsystemBase {

    private Object[] pattern = new Object[3];  // 'g', 'p', or 'n'

    public PatternSubsystem() {}

    public ShooterPosition[] buildSequence(Object[] ballColors) {
        boolean[] used = new boolean[3];
        ShooterPosition[] result = new ShooterPosition[3];

            for (int i = 0; i < 3; i++) {
                // cast Object to Character safely
                char desired = 'n';
                if (pattern[i] != null && pattern[i] instanceof Character) {
                    desired = (Character) pattern[i];
                }

                ShooterPosition chosen;
                switch (desired) {
                    case 'g':
                        chosen = tryConsumeColor('g', ballColors, used);
                        if (chosen == null) chosen = consumeAny(used);
                        break;
                    case 'p':
                        chosen = tryConsumeColor('p', ballColors, used);
                        if (chosen == null) chosen = consumeAny(used);
                        break;
                    default:  // 'n' or any other
                        chosen = consumeAny(used);
                        break;
                }

                result[i] = chosen;
            }


        return result;
    }

    private ShooterPosition tryConsumeColor(Object color, Object[] ballColors, boolean[] used) {
        for (int i = 0; i < 3; i++) {
            if (!used[i] && ballColors[i].equals(color)) {
                used[i] = true;
                return ShooterPosition.fromIndex(i);
            }
        }
        return null; // desired color not available
    }

    private ShooterPosition consumeAny( boolean[] used) {
        for (int i = 0; i < 3; i++) {
            if (!used[i]) {
                used[i] = true;
                return ShooterPosition.fromIndex(i);
            }
        }
        // This should never happen if you always have 3 balls
        return ShooterPosition.NONE;
    }

    public void setPattern(Object[] newPattern) {
        this.pattern = newPattern;
    }

    public Object[] getPattern() {
        return pattern;
    }
}
