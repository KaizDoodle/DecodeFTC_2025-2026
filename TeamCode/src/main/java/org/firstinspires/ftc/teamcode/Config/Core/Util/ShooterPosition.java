package org.firstinspires.ftc.teamcode.Config.Core.Util;

public enum ShooterPosition {
    LEFT,
    MIDDLE,
    RIGHT,
    ALL,
    INTAKE,
    NONE;

    // helper to map 0 → LEFT, 1 → MIDDLE, 2 → RIGHT
    public static ShooterPosition fromIndex(int i) {
        switch(i) {
            case 0: return LEFT;
            case 1: return MIDDLE;
            case 2: return RIGHT;
            default: return NONE;
        }
    }
}