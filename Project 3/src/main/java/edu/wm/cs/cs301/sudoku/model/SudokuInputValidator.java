package edu.wm.cs.cs301.sudoku.model;

public class SudokuInputValidator {
    public static final int EMPTY = 0;
    private final int userRow;
    private final int userCol;
    private final int userVal;
    private final int[][] currentSudoku;

    /**
     * Constructor that checks the desired input, pulling data from the model
     * @param puzzle The model object storing the data on the puzzle
     * @param row: Row to be checked.
     * @param col: Col to be checked.
     * @param value: Value to be checked.
     */
    public SudokuInputValidator(SudokuPuzzle puzzle, int row, int col, int value) {
        this.userRow = row;
        this.userCol = col;
        this.userVal = value;
        this.currentSudoku = puzzle.getCurrent();
    }

    /**
     * Constructor that checks the desired input, directly checking the 2D Array
     * @param puzzle The 2D array storing the data on the puzzle
     * @param row: Row to be checked.
     * @param col: Col to be checked.
     * @param value: Value to be checked.
     */
    public SudokuInputValidator(int[][] puzzle, int row, int col, int value) {
        this.userRow = row;
        this.userCol = col;
        this.userVal = value;
        this.currentSudoku = puzzle;
    }

    /**
     * Throws errors on validation failures. Uses valueCheck method to check for
     * valid placement
     * @throws IllegalArgumentException Inputs are not correct
     */
    public void verifyValidity()
        throws IllegalArgumentException{
        // Check if the user attempts to remove a value from a blank space
        if (currentSudoku[userRow][userCol] == EMPTY && userVal == EMPTY)
            throw new IllegalArgumentException("Selected location is already empty.");
        // Check if the input violates the rules of sudoku
        if (!valueCheck())
            throw new IllegalArgumentException("Input violates the rules of Sudoku, same value in row, column or sub-square.");
    }

    /**
     * Check if the given inputs are valid
     * @return boolean, validity of input
     */
    private boolean valueCheck() {
        // Bypass checks if value is 0 / Empty
        if (userVal == EMPTY)
            return true;
        // Check if value is present in row, column, or box.
        // Row
        for (int rowVal : currentSudoku[userRow])
            if (rowVal == userVal)
                return false;
        // Columns
        for (int i = 0; i < 9; i++)
            if (currentSudoku[i][userCol] == userVal)
                return false;
        // Square
        // Find the top left corner of the square
        int rowCorner = (userRow / 3) * 3;
        int colCorner = (userCol / 3) * 3;
        int rightSide = rowCorner + 3;
        int bottomSide = colCorner + 3;
        for (int iRow = rowCorner; iRow < rightSide; iRow++)
            for (int jCol = colCorner; jCol < bottomSide; jCol++)
                if (currentSudoku[iRow][jCol] == userVal)
                    return false;
        // Passes every check
        return true;
    }
    
    
}
