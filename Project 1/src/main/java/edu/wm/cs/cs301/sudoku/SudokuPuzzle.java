package edu.wm.cs.cs301.sudoku;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SudokuPuzzle {
    ///  Global variables
    public static final int NUM_ROWS = 9;
    public static final int NUM_COLS = 9;
    public static final int EMPTY = 0;
    public static final int FILLED = 1; // To mark the locations of the original numbers of the puzzle
    private final int[][] current = new int[NUM_ROWS][NUM_COLS];  // the current version of the puzzle including valid player moves
    private final int[][] solution = new int[NUM_ROWS][NUM_COLS]; // the solution to the current puzzle
    private final int[][] original = new int[NUM_ROWS][NUM_COLS]; // The original state of the puzzle for input validation
    private int solCount = 0;     // Variable to assist in ensuring uniqueness of puzzles
    private int attempts = 1;     // Adjust for complexity. Minimum one. Used in createPuzzle()
    Random random = new Random(); // Variable to aid in randomization

    /// Constructor
    ///
    /// Creates a unique, filled puzzle (solution) and then creates a puzzle for the user removing
    /// values from the filled puzzle
    public SudokuPuzzle() {
        // Initialize all elements to EMPTY in both solution, current, and original
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                solution[row][col] = EMPTY;
                current[row][col] = EMPTY;
                original[row][col] = EMPTY;
            }
        }
        // Call initSolution to fill the solution with numbers, and then remove values from current
        initSolution();
        createPuzzle(current);
    }

    /// Methods
    ///
    /// Private methods
    // Create a unique puzzle, and store the puzzle in both current, solution, and original
    // Note: Directly uses solution for puzzle creation to avoid copying values after creation of solution
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
                            original[row][col] = FILLED;
                            if (initSolution()) // Call function again to input rest of the numbers
                                return true;
                            else { // If the recursive call fails to add a number, reset values and try again
                                current[row][col] = EMPTY;
                                solution[row][col] = EMPTY;
                                original[row][col] = EMPTY;
                            }
                        }
                    }
                    return false; //Forces the previous method call to try another number if no numbers work.
                }
            }
        }
        return true; // Once the entire thing is filled
    }

    // Method to create the puzzle. Any 2d-array of appropriate size can be used.
    private void createPuzzle(int[][] current) {
        // Find a location randomly to avoid testing the same location again. Test until it
        // attempts get exhausted
        while (attempts > 0) {
            int row = random.nextInt(NUM_ROWS);
            int col = random.nextInt(NUM_COLS);
            while (current[row][col] == EMPTY) {
                row = random.nextInt(NUM_ROWS);
                col = random.nextInt(NUM_COLS);
            }
            // Test if chosen location can lead to a unique solution. Reduce attempts by one
            // if the solution is not unique and restore value at location.
            int backup = current[row][col];
            current[row][col] = EMPTY;
            original[row][col] = EMPTY;
            solCount = 0; // Tracks number of solutions
            findSolution(current);
            if (solCount != 1) {
                current[row][col] = backup;
                original[row][col] = FILLED;
                attempts--;
            }
        }
    }

    // Method to find a solution for a given puzzle. Any 2d-array of appropriate size can be solved.
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

    // Method to check if the puzzle has been solved
    private boolean fullCheck(int[][] srcSudoku) {
        for (int row = 0; row < NUM_ROWS; row++)
            for (int col = 0; col < NUM_COLS; col++)
                if (srcSudoku[row][col] == EMPTY)
                    return false;
        return true;
    }

    // Method to check if a given value is valid
    private boolean valueCheck(int[][] curSudoku, int row, int col, int value) {
        // Bypass checks if value is 0 / Empty
        if (value == EMPTY)
            return true;
        // Check if value is present in row, column, or box.
        // Row
        for (int rowVal : curSudoku[row])
            if (rowVal == value)
                return false;
        // Columns
        for (int i = 0; i < 9; i++)
            if (curSudoku[i][col] == value)
                return false;
        // Square
        // Find the top left corner of the square
        int rowCorner = (row / 3) * 3;
        int colCorner = (col / 3) * 3;
        int rightSide = rowCorner + 3;
        int bottomSide = colCorner + 3;
        for (int iRow = rowCorner; iRow < rightSide; iRow++)
            for (int jCol = colCorner; jCol < bottomSide; jCol++)
                if (curSudoku[iRow][jCol] == value)
                    return false;
        // Passes every check
        return true;
    }

    ///
    ///  Public Methods
    ///
    public int[][] getCurrent() {
        return current;
    }

    public int[][] getSolution() {
        return solution;
    }

    // Method to draw the puzzle
    public void drawPuzzle(int[][] srcPuzzle) {
        /// Variable for spacers
        String spacer = "  |               |               |               |\n";
        String horizontalLine = "  +-----------------------------------------------+\n";

        /// List of Characters for labeling
        ArrayList<Character> labels = new ArrayList<>();
        for (int i = 0; i < 9; i++)
            labels.add((char) ('A' + i));

        /// Print top header
        System.out.print("     ");
        for (int i = 0; i < 9; i++) {
            System.out.print(labels.get(i));
            if (i % 3 == 2)
                System.out.print("     ");
            else
                System.out.print("    ");
        }
        System.out.print('\n');
        System.out.print(horizontalLine);

        /// Print rest of puzzle
        for (int row = 0; row < NUM_ROWS; row++) {
            // Space out lines and appropriately label
            System.out.print(spacer);
            System.out.printf("%s |  ", labels.get(row));
            // Print out a row, adjust for edges of sub-square and empty values
            for (int col = 0; col < NUM_COLS; col++) {
                if (col % 3 == 2) { // Edge
                    if (srcPuzzle[row][col] == EMPTY)
                        System.out.print("   |  ");
                    else
                        System.out.print(srcPuzzle[row][col] + "  |  ");
                } else { // Middle of the sub-square
                    if (srcPuzzle[row][col] == EMPTY)
                        System.out.print("     ");
                    else
                        System.out.print(srcPuzzle[row][col] + "    ");
                }
            }
            // Print spacer below row, adjust for bottom edge of sub-square
            System.out.print('\n');
            if (row % 3 == 2)
                System.out.print(spacer + horizontalLine);
            else
                System.out.print(spacer);
        }
    }

    // Method for input validation and inputting into puzzle
    public void enforceRules(int inpRow, int inpCol, int inpVal)
            throws IllegalArgumentException {
        /// Adjust for ASCII in variables
        inpRow = Character.toUpperCase(inpRow) - 'A';
        inpCol = Character.toUpperCase(inpCol) - 'A';
        inpVal = inpVal - '0';
        /// Input validation
        // Check if the input attempts to overwrite an original value
        if (original[inpRow][inpCol] == FILLED)
            throw new IllegalArgumentException("Changing original values is not allowed.");
        // Check if the user attempts to remove a value from a blank space
        if (current[inpRow][inpCol] == EMPTY && inpVal == EMPTY)
            throw new IllegalArgumentException("Selected location is already empty.");
        // Check if the input violates the rules of sudoku
        if (!valueCheck(current, inpRow, inpCol, inpVal))
            throw new IllegalArgumentException("Input violates the rules of Sudoku, same value in row, column or sub-square.");
    }

    // Method to input user input into current
    public void inputPuzzle(int inpRow, int inpCol, int inpVal) {
        inpRow = Character.toUpperCase(inpRow) - 'A';
        inpCol = Character.toUpperCase(inpCol) - 'A';
        inpVal = inpVal - '0';
        current[inpRow][inpCol] = inpVal;
    }

    // Method to check if the puzzle is solved by the user
    public boolean isSolved() {
        return fullCheck(current);
    }
}
