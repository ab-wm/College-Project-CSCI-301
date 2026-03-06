package edu.wm.cs.cs301.sudoku;

import edu.wm.cs.cs301.sudoku.model.SudokuPuzzle;
import edu.wm.cs.cs301.sudoku.view.InstructionsDialog;
import edu.wm.cs.cs301.sudoku.view.SudokuModelFrame;

import javax.swing.*;

public class Main implements Runnable {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Main());
    }

    @Override
    public void run() {
        SudokuModelFrame sudokuFrame = new SudokuModelFrame(new SudokuPuzzle());
        new InstructionsDialog(sudokuFrame);
    }
}