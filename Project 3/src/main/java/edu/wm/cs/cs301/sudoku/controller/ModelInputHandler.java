package edu.wm.cs.cs301.sudoku.controller;

import edu.wm.cs.cs301.sudoku.model.SudokuInputValidator;
import edu.wm.cs.cs301.sudoku.model.SudokuPuzzle;
import edu.wm.cs.cs301.sudoku.view.CompletionDialog;
import edu.wm.cs.cs301.sudoku.view.GridButton;
import edu.wm.cs.cs301.sudoku.view.GridButtonGroup;
import edu.wm.cs.cs301.sudoku.view.SudokuModelFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Extends AbstractAction to provide functionality to numpad buttons to
 * modify the values of the Sudoku Grid
 */
public class ModelInputHandler extends AbstractAction{
    SudokuPuzzle model;
    SudokuModelFrame modelFrame;
    SudokuInputValidator inputValidator;

    /**
     * @param model The object containing data of the puzzle
     * @param puzzleFrame The frame containing the buttons
     */
    public ModelInputHandler(SudokuPuzzle model, SudokuModelFrame puzzleFrame) {
        this.model = model;
        this.modelFrame = puzzleFrame;
    }

    /**
     * Verifies if the program is in the correct state, and updates the puzzle and the view
     * @param e The ActionEvent
     */
    @Override
    public void actionPerformed (ActionEvent e) {
        JButton buttonPressed = (JButton) e.getSource();
        int inputValue = Integer.parseInt(buttonPressed.getText());
        try {
            // Check if a grid button has been selected, and the value is valid
            validateStateAndInput(inputValue);
            // Update the grid and view once validated
            GridButton selectedButton = GridButtonGroup.getSelectedButton(modelFrame.getButtonGroup());
            setModelValues(selectedButton.getRow(), selectedButton.getCol(), inputValue);
            // Check if the puzzle is complete. If so, trigger the completion dialog
            if (checkCompletion())
                new CompletionDialog(modelFrame);
        } catch (Exception ex) {
            // Create an error dialog using the method in SudokuModelFrame
            modelFrame.createErrorDialog(ex.getMessage());
        }
    }

    /**
     * Validates the input value and ensures that a button has been pressed on the grid.
     * Throws errors relevant to the validation errors.
     * <p>Activates completion panel if the puzzle is complete</p>
     * @param value Input Value
     * @throws IllegalStateException No button selected on the grid
     * @throws IllegalArgumentException Input is invalid
     */
    private void validateStateAndInput(int value)
        throws IllegalStateException, IllegalArgumentException {
        int row;
        int col;
        // Check if a button has been pressed in the sudokuGrid.
        // If not trigger error to user.
        // Else store coordinates of button
        GridButton selectedButton = GridButtonGroup.getSelectedButton(modelFrame.getButtonGroup());
        if (selectedButton == null)
            throw new IllegalStateException("No button selected on the Sudoku grid");
        else {
            row = selectedButton.getRow();
            col = selectedButton.getCol();
        }
        // Check if the inputted value is valid. Method throws IllegalArgumentException
        inputValidator = new SudokuInputValidator(model, row, col, value);
        inputValidator.verifyValidity();
    }

    /**
     * Updates values stored in the model, and updates the view
     * @param row Row to be changed
     * @param col Col to be changed
     * @param value Value to be inputted
     */
    private void setModelValues(int row, int col, int value) {
        // Set values in model
        model.updatePuzzle(row, col, value);
        // Update button
        modelFrame.updateButton(value);
    }

    /**
     * @return boolean, if the puzzle has been completed
     */
    private boolean checkCompletion() {
        return model.isSolved();
    }

    @Deprecated
    // Not required due to change in intended interface
    public void inputValue() {}

}
