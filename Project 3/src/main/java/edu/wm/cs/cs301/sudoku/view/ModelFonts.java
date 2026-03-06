package edu.wm.cs.cs301.sudoku.view;

import java.awt.*;

/**
 * Class with static methods that returns preset Fonts
 * <p>Purpose is to centralize fonts to make changing the look and feel of the program easier</p>
 */
public class ModelFonts {
    public static Font getTitleFont() { return new Font(Font.SANS_SERIF, Font.BOLD, 36); }
    public static Font getCongratulationsTitleFont() { return new Font(Font.SERIF, Font.ITALIC, 36); }
    public static Font getCongratulationsTextFont() { return new Font(Font.DIALOG, Font.PLAIN, 18); }
    public static Font getGridButtonFont() { return new Font(Font.MONOSPACED, Font.BOLD, 14); }
}