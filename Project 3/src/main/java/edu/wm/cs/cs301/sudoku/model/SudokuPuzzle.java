package edu.wm.cs.cs301.sudoku.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * The class responsible for the logic of the puzzle. Creates, stores, and keep tracks
 * of the data stored in the grid
 */
public class SudokuPuzzle {
    ///  Global variables
    private static final int NUM_ROWS = 9;
    private static final int NUM_COLS = 9;
    private static final int EMPTY = 0;
    private final int[][] current = new int[NUM_ROWS][NUM_COLS];  // the current version of the puzzle including valid player moves
    private final int[][] solution = new int[NUM_ROWS][NUM_COLS]; // the solution to the current puzzle
    private int solCount = 0;     // Variable to assist in ensuring uniqueness of puzzles
    private int puzzleDifficulty = 1; // Adjust for complexity. Minimum one. Used in createPuzzle()
    Random random = new Random(); // Variable to aid in randomization

    /**
     * <p>Constructor</p>
     * Creates a unique, filled puzzle (solution) and then creates a puzzle for the user removing
     * values from the filled puzzle
     */
    public SudokuPuzzle() {
        // Initialize all elements to EMPTY in both solution, current, and original
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                solution[row][col] = EMPTY;
                current[row][col] = EMPTY;
            }
        }
        // Call initSolution to fill the solution with numbers, and then remove values from current
        initSolution();
        createPuzzle(current);
    }

    /**
     * Creates a unique puzzle, and stores the data in the relevant arrays
     * @return boolean: Aids in recursion
     */
    private boolean initSolution() {
        // Create a list of integers 1-9, and shuffle it for randomization of puzzles
        ArrayList<Integer> intList = new ArrayList<>();
        for (int i = 1; i < 10; i++)
            intList.add(i);
        Collections.shuffle(intList);

        // Creating the solution
        // Find the next empty value, go through the list of integers that are randomized on each call,
        // and store in both current and solution if valid
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                if (solution[row][col] == EMPTY) {
                    for (int ranVal : intList) {
                        //Call function to check value
                        if (valueCheck(solution, row, col, ranVal)) {
                            current[row][col] = ranVal;
                            solution[row][col] = ranVal;
                            if (initSolution()) // Call function again to input rest of the numbers
                                return true;
                            else { // If the recursive call fails to add a number, reset values and try again
                                current[row][col] = EMPTY;
                                solution[row][col] = EMPTY;
                            }
                        }
                    }
                    return false; //Forces the previous method call to try another number if no numbers work.
                }
            }
        }
        return true; // Once the entire thing is filled
    }

    /**
     * Creates the puzzle.
     * @param current: The 2D arrays that represents the current state of the puzzle presented
     *               to the user
     */
    private void createPuzzle(int[][] current) {
        // Find a location randomly to avoid testing the same location again. Test until it
        // puzzleDifficulty get exhausted
        while (puzzleDifficulty > 0) {
            int row = random.nextInt(NUM_ROWS);
            int col = random.nextInt(NUM_COLS);
            while (current[row][col] == EMPTY) {
                row = random.nextInt(NUM_ROWS);
                col = random.nextInt(NUM_COLS);
            }
            // Test if chosen location can lead to a unique solution. Reduce puzzleDifficulty by one
            // if the solution is not unique and restore value at location.
            int backup = current[row][col];
            current[row][col] = EMPTY;
            solCount = 0; // Tracks number of solutions
            findSolution(current);
            if (solCount != 1) {
                current[row][col] = backup;
                puzzleDifficulty--;
            }
        }
    }

    /**
     * Method to find a solution for a given puzzle.
     * @param srcSudoku: The 2D array representing the data of a Sudoku puzzle
     */
    private void findSolution(int[][] srcSudoku) {
        // Create an int list 1-9
        ArrayList<Integer> intList = new ArrayList<>();
        for (int i = 1; i < 10; i++)
            intList.add(i);

        // Find a solution for a randomly selected location
        int row = random.nextInt(NUM_ROWS);
        int col = random.nextInt(NUM_COLS);
        while (srcSudoku[row][col] != EMPTY) {
            row = random.nextInt(NUM_ROWS);
            col = random.nextInt(NUM_COLS);
        }

        // Go through each value in the intList, test its validity, and recursively call the function again if
        // the puzzle is not solved.
        for (int ranVal : intList) { // Every possible value is tested to catch multiple solutions
            if (valueCheck(srcSudoku, row, col, ranVal)) {
                srcSudoku[row][col] = ranVal;
                if (fullCheck(srcSudoku)) // Only increase counter when puzzle is full
                    solCount++;
                else
                    findSolution(srcSudoku); // Recursive call if puzzle is not full
                srcSudoku[row][col] = 0; // Reset values after testing each number
            }
        }
    }

    /**
     * Method to check if the puzzle has been solved
     * @param srcSudoku: The 2D array representing the data of a Sudoku puzzle
     * @return boolean: Whether the puzzle has been completed
     */
    private boolean fullCheck(int[][] srcSudoku) {
        for (int row = 0; row < NUM_ROWS; row++)
            for (int col = 0; col < NUM_COLS; col++)
                if (srcSudoku[row][col] == EMPTY)
                    return false;
        return true;
    }

    /**
     * Method to input user input into current
     * @param inpRow: Row to be updated
     * @param inpCol: Col to be updated
     * @param inpVal: Val to be updated
     */
    public void updatePuzzle(int inpRow, int inpCol, int inpVal) {
        current[inpRow][inpCol] = inpVal;
    }


    // Method to check if a given value is valid
    // Modified to use the SudokuInputValidator

    /**
     * Method to check if a given value is valid
     * @implNote Functionality of this method was moved to SudokuInputValidator
     * @param curSudoku: 2D Array containing the data of the puzzle
     * @param row: Selected row to be checked
     * @param col: Selected col to be checked
     * @param value: Selected val to be checked
     * @return boolean: Whether the input is valid
     */
    private boolean valueCheck(int[][] curSudoku, int row, int col, int value) {
        SudokuInputValidator validator = new SudokuInputValidator(curSudoku, row, col, value);
        // If any exceptions are thrown, return false, otherwise, return true
        try {
            validator.verifyValidity();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Method to check if the puzzle is solved by the user
     */
    public boolean isSolved() {
        return fullCheck(current);
    }

    public int[][] getCurrent() {
        return current;
    }

    public int[][] getSolution() {
        return solution;
    }
}
