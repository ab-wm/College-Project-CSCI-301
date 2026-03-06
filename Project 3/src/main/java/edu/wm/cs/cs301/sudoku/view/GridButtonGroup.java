package edu.wm.cs.cs301.sudoku.view;

import javax.swing.*;
import java.util.Enumeration;

public class GridButtonGroup extends ButtonGroup {

    /**
     * Method to return to find and return a selected button
     * Null object returned when no selected button is found
     * Source: Oracle Java Docs (Enumeration<E>) and Intellij autocomplete
     * @param buttonGroup: Extended ButtonGroup that stores the GridButtons
     * @return GridButton (A button has been found) or null (no buttons are found)
     */
    static public GridButton getSelectedButton(GridButtonGroup buttonGroup) {
        Enumeration<AbstractButton> buttons = buttonGroup.getElements(); // Store buttons from button grid in enumeration
        while (buttons.hasMoreElements()) {  // Iterate through buttons, return selected button
            GridButton button = (GridButton) buttons.nextElement();
            if (button.isSelected()) {
                return button;
            }
        }
        return null; // No button found
    }
}

