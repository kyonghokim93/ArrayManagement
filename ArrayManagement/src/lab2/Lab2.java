/*
 * Student Name: Steven (Kyong Ho) Kim
 * Lab Professor: George Kriger
 * Due Date: May 22th, 2022
 */

package lab2;

import java.util.Scanner;
import java.io.IOException;
import java.util.InputMismatchException;

/**
 * CET - CS Academic Level 3
 * Declaration: I declare that this is my own original work and is free from Plagiarism
 * This class contains the dynamically allocated array and it's processing 
 * Student Name: Steven (Kyong Ho) Kim 
 * 
 * @author Steven (Kyong Ho) Kim
 * @version 1.0
 * @since 1.11
 * 
 */
public class Lab2 {

	/**
	 * When the user starts the program, menu appears to chose an option from available integer. 
	 * If user inputs 1, default array size with 3 is created.
	 * If user inputs 2, user is prompted to enter a valid integer value of array size, and that array is created.
	 * If user inputs 3, user is prompted to provide a float value to add to the array.
	 * If user inputs 4, all values in the array are displayed.
	 * If user inputs 5, average, maximum, minimum, and modulo values of array elements are displayed.
	 * If user inputs 6, user is prompted to enter the number of values to add, and adds values in the array.
	 * If user inputs 7, user is prompted to provide a file name to read from, and reads input from that file.
	 * If user inputs 8, user is prompted to provide a file name to write to, and writes output to that file.
	 * If user inputs 9, program exits. 
	 * If user inputs any other integer, float, or text, user is given an error message, and prompted to provide a valid input.
	 * The program keeps running until the user inputs 6 in the main menu to exit.
	 * 
	 * @param args
	 * 			for command line argument, not used in this program
	 */
	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		int option = 0;
		Numbers numbers = null;

		do {
			displayMainMenu(); // menu display
			option = intInputHandler(keyboard); // user of method to handle integer input
			
			switch (option) {
			case 1:
				numbers = new Numbers();
				System.out.print("\nConstructed an array with default size of 10.\n\n");
				break;
			case 2:
				int size;
				System.out.print("Enter the max size of the array:\n> ");
				size = intInputHandler(keyboard); // use of method to handle integer input
				numbers = new Numbers(size);
				System.out.printf("\nConstructed an array with size of %d.\n\n",size);
				break;
			case 3:
				if (numbers == null) {
					// When user tries to add value without first initializing the array, initialize with default size 3
					//System.out.println("You tried to add value to an array that is not initialized. I will initialize it with default value of 3 for you.");
					numbers = new Numbers();
				}
				numbers.addValue(keyboard);
				break;
			case 4:
				// If numbers is not null, toString method is used to print values in numbers
				if (numbers != null) {
					System.out.println(numbers);
					break;
				}
				// the code below is used to match sample output. If I were allowed to edit, I would print an error message instead.
				System.out.println("Numbers are:\n");
				break;
			case 5:
				float average = 0.0f;
				float[] minMaxModArray = new float[3];
				
				// If numbers is not null and have at least one item added, 2 methods are called
				if (numbers != null && numbers.getNumItems() > 0) {
					average = numbers.calcAverage();
					minMaxModArray = numbers.findMinMax();
				}
				System.out.printf("\nAverage is %.2f, Minimum value is %.2f, Maximum value is %.2f, "
						+ "max mod min is %.2f\n\n", average, minMaxModArray[0], minMaxModArray[1], minMaxModArray[2]);
				
				break;
			case 6:
				if (numbers == null) {
					// When user tries to add value without first initializing the array, initialize with default size 10
					System.out.print("You tried to add values without initializing an array. I will initialize a default one for you this time.");
					numbers = new Numbers();
					System.out.print("\nConstructed an array with default size of 10.\n\n");
				}
				numbers.addValues(keyboard);
				break;
			case 7:
				
				if (numbers == null) {
					System.out.print("You tried to add values without initializing an array. I will initialize a default one for you this time.");
					numbers = new Numbers();
					System.out.print("\nConstructed an array with default size of 10.\n\n");
				}
				
				try {
					keyboard.nextLine(); // remove \n from integer input
					numbers.readFile(keyboard);
				} catch (IOException e) {
					System.out.println("\nFile reading failed. You might have entered a wrong file name.\n");
				} catch (Exception e) {
					throw new RuntimeException("Something very wrong happened. Aborting program\n",e);
				}
				
				break;
			case 8:
				if (numbers == null) {
					System.out.println("\nYou have to initialize an array first!\n");
					break;
				}
				
				try {
					keyboard.nextLine(); // remove \n from integer input
					numbers.writeFile(keyboard);
				} catch (IOException e) {
					System.out.println("\nFile writing failed. You might have entered a wrong file name.\n");
				} catch (Exception e) {
					throw new RuntimeException("Something very wrong happened. Aborting program\n",e);
				}
				
				break;
			case 9:
				System.out.println("\nExiting...\nProgram by Steven Kyong Ho Kim");
				break;
			default:
				System.out.println("\nYou have entered an invalid input. Please only input an integer value from the menu.\n");
			}
		} while (option != 9);
		keyboard.close(); // Scanner object is closed at the end of the program
	}

	/**
	 * displays the main menu
	 */
	private static void displayMainMenu() {
		System.out.println("Please select one of the following:");
		System.out.println("1: Initialize a default array");
		System.out.println("2: To specify the max size of the array");
		System.out.println("3: Add value to the array");
		System.out.println("4: Display value in the array");
		System.out.println("5: Display average of the values, minimum value, maximum value, max mod min");
		System.out.println("6: Enter Multiple values");
		System.out.println("7: Read values from file");
		System.out.println("8: Save values to file");
		System.out.println("9: To Exit");
		System.out.print("> ");
	}
	
	/**
	 * This method handles integer input w/ exception handling.
	 * This method is created to declutter code.
	 * 
	 * @param keyboard
	 * 			Scanner object to read input
	 * @return input
	 * 			User's input integer value
	 */
	private static int intInputHandler(Scanner keyboard) {
		int input = 0;
		boolean validation = false;
		
		do {
			try {
				input = keyboard.nextInt();
				validation=true;
				if (input < 0) { // negative integer input is not accepted
					System.out.print("Negative input is not allowed. Please try again.\n> ");
					validation=false;
				}
			} catch (InputMismatchException e) {
				System.out.print("Please only input an integer. Please try again.\n> ");
				keyboard.nextLine();
			} 
		} while (!validation);
		
		return input;
	}
}