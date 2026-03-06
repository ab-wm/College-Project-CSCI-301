package edu.wm.cs.cs301.sudoku;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Variables / Objects
        Scanner usrInp = new Scanner(System.in);
        boolean inpFailure = false; // Help with formatting output
        String input;

        // Introduction
        System.out.println("Welcome to sudoku!");
        System.out.println("Generating Puzzle...");
        SudokuPuzzle sudokuPuzzle = new SudokuPuzzle();
        System.out.println("Puzzle generated.");
        System.out.print('\n');

        // Do-while loop until the puzzle is filled.
        do {
            if (!inpFailure) {
                // Ask for input. Avoid printing again on input failure in the previous loop
                System.out.println("Enter the desired row (A - I), column (A - I), and number (0-9), or 'Q' to quit.");
                System.out.println("Please enter in that order, without spaces:");
                System.out.println("Note: Entering 0 will remove a value");
                sudokuPuzzle.drawPuzzle(sudokuPuzzle.getCurrent());
            }
            inpFailure = false; // Reset flag

            input = usrInp.nextLine(); // Grab input from user
            if (input.equalsIgnoreCase("Quit")) // User wishes to quit
                break;
            try {
                isInputValid(input);
                sudokuPuzzle.enforceRules(input.charAt(0), input.charAt(1), input.charAt(2));
                sudokuPuzzle.inputPuzzle(input.charAt(0), input.charAt(1), input.charAt(2));
            } catch (IllegalArgumentException iae) {
                System.out.println(iae.getMessage());
                System.out.println("Please input again");
                inpFailure = true;
            }

        } while (!sudokuPuzzle.isSolved());


        System.out.println("Thank you for playing Sudoku!");
        if (sudokuPuzzle.isSolved())
            System.out.println("Congratulations! You have solved the puzzle!");
        System.out.println("Solution:");
        sudokuPuzzle.drawPuzzle(sudokuPuzzle.getSolution());
    }

    private static void isInputValid(String input)
            throws IllegalArgumentException {
        if (input.length() != 3)
            throw new IllegalArgumentException("Input must be 3 characters");
        if (Character.toUpperCase(input.charAt(0)) < 'A' || Character.toUpperCase(input.charAt(0)) > 'I')
            throw new IllegalArgumentException("Input is invalid. Incorrect row identifier");
        if (Character.toUpperCase(input.charAt(1)) < 'A' || Character.toUpperCase(input.charAt(1)) > 'I')
            throw new IllegalArgumentException("Input is invalid. Incorrect column identifier");
        if (input.charAt(2) < '0' || input.charAt(2) > '9')
            throw new IllegalArgumentException("Input is invalid. Must be a number");
    }
}



