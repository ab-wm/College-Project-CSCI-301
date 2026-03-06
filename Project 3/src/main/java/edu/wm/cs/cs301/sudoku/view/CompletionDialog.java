package edu.wm.cs.cs301.sudoku.view;

import javax.swing.*;
import java.awt.*;

/**
 * Creates a window on top of the main panel/game that provides the user with options
 * to quit the game or restart it
 * Uses methods from SudokuModelFrame to quit and restart the game
 */
public class CompletionDialog extends JDialog {
    int modelWidth, modelHeight;
    SudokuModelFrame sudokuFrame;

    /**
     * Constructor that builds and shows the completion window
     * @param sudokuFrame: The calling frame, the game
     */
    public CompletionDialog(SudokuModelFrame sudokuFrame) {
        super(sudokuFrame.getFrame(), "Puzzle Completed", true);
        this.sudokuFrame = sudokuFrame;
        modelWidth = (int) (sudokuFrame.getWidth()*1.15);
        modelHeight = (sudokuFrame.getHeight()/2);

        this.setPreferredSize(new Dimension(modelWidth, modelHeight));
        this.setAlwaysOnTop(true);
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.setResizable(false);

        this.add(createTitlePanel(), BorderLayout.NORTH);
        this.add(createMessagePanel(), BorderLayout.CENTER);
        this.add(createButtonPanel(), BorderLayout.SOUTH);
        this.pack();
        this.setLocationRelativeTo(sudokuFrame.getFrame());
        this.setVisible(true);
    }

    /**
     * Creates a JPanel with the decorative title of the JDialog
     * @return JPanel
     */
    private JPanel createTitlePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 5, 0, 5));
        panel.setPreferredSize(new Dimension(modelWidth, modelHeight/4));

        JLabel label = new JLabel("Congratulations!");
        label.setFont(ModelFonts.getCongratulationsTitleFont());
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Creates the main body of the window
     * @return JPanel
     */
    private JPanel createMessagePanel() {
        JPanel messagePanel = new JPanel();  // Help with alignment: stackoverflow.com/questions/29345551/
        messagePanel.setLayout(new GridLayout(3,1));
        messagePanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 5, 15));
        messagePanel.setPreferredSize(new Dimension(modelWidth, modelHeight/4));

        JLabel label_1 = new JLabel();
        label_1.setText("You have successfully completed the puzzle!");
        label_1.setFont(ModelFonts.getCongratulationsTextFont());
        label_1.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel label_2 = new JLabel();
        label_2.setText("If you would like to play again press the \"Next\" button");
        label_2.setFont(ModelFonts.getCongratulationsTextFont());
        label_2.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel label_3 = new JLabel();
        label_3.setText("Otherwise, press \"Quit\" to exit");
        label_3.setFont(ModelFonts.getCongratulationsTextFont());
        label_3.setHorizontalAlignment(SwingConstants.CENTER);

        messagePanel.add(label_1);
        messagePanel.add(label_2);
        messagePanel.add(label_3);

        return messagePanel;
    }

    /**
     * Create buttons of the panel and assigns appropriate behavior according to their
     * descriptions
     * @return JPanel
     */
    public JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1,2,20,0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 45, 15, 45));

        JButton quitButton = new JButton("Quit"); // Button calls the main frame's gameQuit() method
        quitButton.setPreferredSize(new Dimension(modelWidth/5, modelHeight/10));
        quitButton.addActionListener(e -> {
            dispose();
            sudokuFrame.gameQuit();
        });

        JButton nextButton = new JButton("Next"); // Button calls the main frame;s gameRestart() method
        nextButton.setPreferredSize(new Dimension(modelWidth/5, modelHeight/10));
        nextButton.addActionListener(e -> {
            dispose();
            sudokuFrame.gameRestart();
        });
        buttonPanel.add(quitButton);
        buttonPanel.add(nextButton);
        return buttonPanel;
    }
}
