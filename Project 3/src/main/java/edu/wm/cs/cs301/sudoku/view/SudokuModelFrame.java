package edu.wm.cs.cs301.sudoku.view;

import edu.wm.cs.cs301.sudoku.model.SudokuPuzzle;

import javax.swing.*;
import java.awt.*;

/**
 * The main class that create and displays the game to the user
 * Relies on the SudokuPuzzle class for data to display on the grid
 */
public class SudokuModelFrame {
    private final NumpadPanel NumberPadPanel;
    private final int[][] originalPuzzle;
    private final GridButtonGroup buttonGroup;
    private final int modelWidth = 400;
    private final int modelHeight = 750;
    private final JFrame sudoKuFrame;

    /**
     * @param model: The Sudoku data storing object
     */
    public SudokuModelFrame(SudokuPuzzle model) {
        buttonGroup = new GridButtonGroup();
        this.originalPuzzle = model.getCurrent(); // While current may change, only the original state is stored
        this.NumberPadPanel = new NumpadPanel(this, model);
        sudoKuFrame = createFrame();
        //JOptionPane.showMessageDialog(frame,"Ahh", "test", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Creates the frame/container that builds the main frame of the game, utilizing the NumpadPanel class
     * for the numpad.
     * @return JFrame: The entire frame of the user facing program
     */
    private JFrame createFrame() {
        JFrame frame = new JFrame("Sudoku");
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setMinimumSize(new Dimension(100,100));
        frame.setJMenuBar(createMenu());
        frame.add(createTitlePanel());
        frame.add(createSudokuGridPanel());
        frame.add(NumberPadPanel.getNumbersPanel());
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
        return frame;
    }

    /**
     * Creates the decorative title panel of the window
     * @return JPanel
     */
    private JPanel createTitlePanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(modelWidth, modelHeight/10));
        panel.setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("Sudoku");
        titleLabel.setFont(ModelFonts.getTitleFont());
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Creates the menu bar of the frame with specification meeting behavior
     * @return JMenuBar
     */
    private JMenuBar createMenu() {
        JMenuBar menuBar = new JMenuBar();
        ToolTipManager.sharedInstance().setInitialDelay(0); // Source: https://stackoverflow.com/questions/1190290

        // Menu Items
        JMenu optionsMenu = new JMenu("Options");
        JMenuItem instructionsMenu = new JMenuItem("Instructions");
        JMenuItem restartMenu = new JMenuItem("Restart");
        JMenuItem quitMenu = new JMenuItem("Quit");
        instructionsMenu.addActionListener(e -> new InstructionsDialog(this));
        restartMenu.addActionListener(e -> gameRestart());
        restartMenu.setToolTipText("May take upto 30s to create a new game");
        quitMenu.addActionListener(e -> gameQuit());

        optionsMenu.add(instructionsMenu);
        optionsMenu.add(restartMenu);
        optionsMenu.add(quitMenu);
        menuBar.add(optionsMenu);
        return menuBar;
    }

    /**
     * Panel that creates the grid of buttons and labels that represents the Sudoku grid
     * @implNote Relies on the createsSubGridsPanel that actually creates the grid. This
     * method formats the output of the method into a grid.
     * @return JPanel: The grid
     */
    private JPanel createSudokuGridPanel() {
        // Create a grid panel
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(3,3,10,10));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        gridPanel.setPreferredSize(new Dimension(modelWidth,modelHeight/2));
        // Panel consists of 9 sub-grids which are built by createSubGridsPanel()
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                gridPanel.add(createSubGridsPanel(i, j));
        return gridPanel;
    }


    /**
     * Create a sub-grid of Sudoku squares, adjusted for the subgrid defined by createSudokuGridPanel()
     * @implNote Method assigns coordinates to the created buttons according to the subgrid, as defined
     * by the calling method.
     * @param subGridRow: The coordinate of the row (top left corner coordinate)
     * @param subGridCol: The coordinate of the col (top left corner coordinate)
     * @return JPanel: The subgrids of the grid
     */
    private JPanel createSubGridsPanel(int subGridRow, int subGridCol) {
        // Create panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,3,5,5));

        // Create buttons and labels according to the values of original array of values
        // Empty cells create button with no text
        // Filled cells create un-editable labels with text
        // Adjust according to subgrid chosen by createSudokuGridPanel();
        for (int iRow = subGridRow * 3; iRow < subGridRow * 3 + 3; iRow++) {
            for (int jCol = subGridCol * 3; jCol < subGridCol * 3 + 3; jCol++) {
                int EMPTY = 0;
                if (originalPuzzle[iRow][jCol] == EMPTY) { // Create button for empty cells
                    GridButton b = new GridButton(iRow, jCol);
                    panel.add(b);
                    buttonGroup.add(b);
                }
                else
                {
                    JLabel label = new JLabel(String.valueOf(originalPuzzle[iRow][jCol]));
                    label.setSize(5, 5);
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    panel.add(label);
                }
            }
        }
        return panel;
    }

    /**
     * Creates an error dialog according to the specified message
     * @param message: The error message
     */
    public void createErrorDialog(String message) {
        JOptionPane.showMessageDialog(sudoKuFrame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Updates the selected button with the appropriate value
     * @param val: The value that the button should display
     */
    public void updateButton(int val) {
        // Get the selected button, and change the value displayed on the button
        GridButton selectedButton = GridButtonGroup.getSelectedButton(buttonGroup);
        if (selectedButton != null) {   // Safegaurd if the state of the grid had not been previously checked
            selectedButton.setText(String.valueOf(val));
            selectedButton.setSelected(false);
        }
    }

    /**
     * Method to restart the game. Disposes the current frame and creates a
     * new instance of the puzzle
     */
    public void gameRestart() {
        sudoKuFrame.dispose();
        new SudokuModelFrame(new SudokuPuzzle());
    }

    /**
     * Method to quit the game and close the program. Disposes current frame and exits
     */
    public void gameQuit() {
        sudoKuFrame.dispose();
        System.exit(0);
    }

    public JFrame getFrame() {
        return sudoKuFrame;
    }
    public int getWidth() {
        return modelWidth;
    }
    public int getHeight() {
        return modelHeight;
    }
    public GridButtonGroup getButtonGroup() {
        return buttonGroup;
    }

}
