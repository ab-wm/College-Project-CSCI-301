package edu.wm.cs.cs301.sudoku.view;

import javax.swing.*;
import java.awt.*;

/**
 * An extended JToggleButton that stores the coordinates (row and col),
 * representing its position on the Sudoku Grid
 */
public class GridButton extends JToggleButton {
    private final int sudRow;
    private final int sudCol;

    /**
     * Constructor that appropriately assigns the coordinates to private variables
     * @param row: The row number the button was placed in
     * @param col: The col number the button was placed in
     */
    public GridButton(int row, int col) {
        this.sudRow = row;
        this.sudCol = col;
        initButton();
    }

    /**
     * Method for the creation of the JToggleButton
     */
    private void initButton() {
        int cellSize = 5;
        super.setSize(cellSize, cellSize);
        super.setFocusPainted(true);
        super.setBackground(Color.WHITE);
        super.setHorizontalTextPosition(SwingConstants.CENTER);
        super.setFont(ModelFonts.getGridButtonFont());
        super.setMargin(new Insets(0, 0, 0, 0)); // Source: https://coderanch.com/t/345647/java/JButton-text-padding
    }

    public int getRow() {
        return sudRow;
    }
    public int getCol() {
        return sudCol;
    }
}
