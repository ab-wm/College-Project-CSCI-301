package edu.wm.cs.cs301.sudoku.view;

import edu.wm.cs.cs301.sudoku.controller.ModelInputHandler;
import edu.wm.cs.cs301.sudoku.model.SudokuPuzzle;

import javax.swing.*;
import java.awt.*;

/**
 * Class that creates the numpad that is displayed on the game panel
 */
public class NumpadPanel {
    final private ModelInputHandler action;
    final private int modelWidth;
    final private int modelHeight;

    /**
     * @param sudokuFrame: Main game pane
     * @param model: Sudoku Puzzle model
     */
    public NumpadPanel(SudokuModelFrame sudokuFrame, SudokuPuzzle model) {
        action = new ModelInputHandler(model, sudokuFrame);
        modelWidth = sudokuFrame.getWidth();
        modelHeight = sudokuFrame.getHeight();
    }

    /**
     * Creates the entire panel
     * @return JPanel: The numpad panel
     */
    private JPanel createNumpadPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.setPreferredSize(new Dimension((modelWidth), (int) (modelHeight*(float)1.5/5)));
        panel.add(Box.createHorizontalGlue());
        panel.add(createMainNumbersPanel());
        panel.add(createResetRow());
        return panel;
    }

    /**
     * Creates the portion of the numpad panel containing 1-9
     * @return JPanel
     */
    private JPanel createMainNumbersPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,3,2,2));
        panel.setBorder(BorderFactory.createEmptyBorder(3, 100, 3, 100));
        panel.setPreferredSize(new Dimension(125,150));
        for (int i = 1; i <= 9; i++) {
            JButton button = new JButton("" + i);
            button.setHorizontalAlignment(JButton.CENTER);
            button.setVerticalAlignment(JButton.CENTER);
            button.setBorder(BorderFactory.createRaisedBevelBorder());
            button.setBackground(Color.DARK_GRAY);
            button.setForeground(Color.WHITE);
            button.setActionCommand("" + i);
            button.addActionListener(action);
            panel.add(button);
        }
        return panel;
    }

    /**
     * Create the panel containing the 0 button, the cell reset button
     * @return JPanel
     */
    private JPanel createResetRow() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 100, 5, 100));
        panel.setPreferredSize(new Dimension(150,20));
        JButton button = new JButton("0");
        button.setHorizontalAlignment(JButton.CENTER);
        button.setVerticalAlignment(JButton.CENTER);
        button.setBorder(BorderFactory.createEmptyBorder(0,2,0,2));
        button.setBackground(Color.RED);
        button.setForeground(Color.WHITE);
        button.setActionCommand("0");
        button.addActionListener(action);
        panel.add(button);
        return panel;
    }

    public JPanel getNumbersPanel() {
        return createNumpadPanel();
    }
}
