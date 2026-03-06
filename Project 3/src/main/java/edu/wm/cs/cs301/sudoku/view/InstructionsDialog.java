package edu.wm.cs.cs301.sudoku.view;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Creates a window on top of the main panel/game that provides the user with
 * instructions on how to play the game and use the program
 */
public class InstructionsDialog extends JDialog {
    int modelWidth, modelHeight;

    /**
     * Constructor that builds and shows the instructions window
     * @param sudokuFrame: The calling frame, the game
     */
    public InstructionsDialog(SudokuModelFrame sudokuFrame) {
        super(sudokuFrame.getFrame(), "Instructions", true);

        this.modelWidth = (int)(sudokuFrame.getWidth()*1.2);
        this.modelHeight = (int)(sudokuFrame.getHeight()*0.9);

        this.setPreferredSize(new Dimension(modelWidth,(int)(modelHeight*0.85)));
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setAlwaysOnTop(true);

        this.add(createInstructionsPanel(), BorderLayout.NORTH);
        this.add(createInformationPanel(), BorderLayout.CENTER);
        this.add(createButtonPanel(), BorderLayout.SOUTH);
        this.pack();
        this.setLocationRelativeTo(sudokuFrame.getFrame());
        this.setVisible(true);
    }

    /**
     * The title panel that covers the basic instructions to play the game
     * @return JPanel
     */
    private JPanel createInstructionsPanel() {
        JPanel instructionsPanel = new JPanel();
        instructionsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        instructionsPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        instructionsPanel.setPreferredSize(new Dimension(modelWidth,(int)(modelHeight*0.22)));

        // Title
        JLabel titleLabel = new JLabel("Instructions");
        titleLabel.setFont(ModelFonts.getTitleFont());

        // Basic specifications
        JPanel specificationsPanel = new JPanel();
        specificationsPanel.setLayout(new BoxLayout(specificationsPanel, BoxLayout.Y_AXIS));
        JLabel label_1 = new JLabel("Steps to input numbers:", JLabel.LEFT);
        JLabel label_2 = new JLabel("- Select a cell from the grid", JLabel.LEFT);
        JLabel label_3 = new JLabel("- Select the desired value from the number pad.", JLabel.LEFT);
        JLabel label_4 = new JLabel("- 0 will erase the stored number", JLabel.LEFT);
        specificationsPanel.add(label_1);
        specificationsPanel.add(label_2);
        specificationsPanel.add(label_3);
        specificationsPanel.add(label_4);

        instructionsPanel.add(titleLabel);
        instructionsPanel.add(specificationsPanel);
        return instructionsPanel;
    }

    // The panel that provides more detailed information

    /**
     * Panel that more detailed information on the rules of Sudoku, and more
     * information on how the program can and cannot help
     * @implNote Method relies on the instructions.html resource for displaying relevant information to the user
     * @return JPanel
     */
    private JPanel createInformationPanel() {
        // Main panel
        JPanel informationPanel = new JPanel();
        informationPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        informationPanel.setLayout(new BoxLayout(informationPanel, BoxLayout.Y_AXIS));
        informationPanel.setPreferredSize(new Dimension(modelWidth,(int)(modelHeight*0.7)));

        // Editor pane that contains information of the game, pulled from a local file
        JEditorPane editorPane = new JEditorPane();
        editorPane.setEditable(false);
        editorPane.setContentType("text/html");
        try { // Exception handling as setPage throws IO exception
            editorPane.setPage(InstructionsDialog.class.getResource("/instructions.html"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Scroll pane to fit the information
        JScrollPane scrollPane = new JScrollPane(editorPane);
        informationPanel.add(scrollPane);

        return informationPanel;
    }

    // The panel to exit the instruction dialog

    /**
     * Creates a Panel with a button with appropriate formating that closes the instructions dialog
     * @return JPanel
     */
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        //buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setPreferredSize(new Dimension(modelWidth,(int)(modelHeight*0.07)));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, (int)(modelWidth*0.3),
                                                            5, (int)(modelWidth*0.3)));

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());

        buttonPanel.add(closeButton);
        return buttonPanel;
    }
}
