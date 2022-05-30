/*
 * Student Name: Steven (Kyong Ho) Kim
 * Lab Professor: George Kriger
 * Due Date: May 22th, 2022
 */

package lab2;

import java.util.Scanner;
import java.nio.file.Paths;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

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
public class Numbers {
	
	/**
	 * an array that stores Float values.
	 */
	private Float[] numbers;

	/**
	 * Stores the number of items currently in the array.
	 */
	private int numItems;

	/**
	 * Stores the size of the array.
	 */
	private int arraySize;

	/**
	 * a constant that describes default array size of 10
	 */
	private static final int DEFAULT_ARRAY_SIZE = 10;

	/**
	 * Default Constructor Initialized to have a size of 3 (DEFAULT_ARRAY_SIZE)
	 */
	public Numbers() {
		// Write code here to initialize a "default" array since this is the default
		// constructor
		numbers = new Float[DEFAULT_ARRAY_SIZE]; // initializing an array with size 3
		numItems = 0; // numItems is 0 for a new instance
		arraySize = DEFAULT_ARRAY_SIZE;
	}

	/**
	 * Constructor that initializes the numbers array.
	 * 
	 * @param size - Max size of the numbers array
	 */
	public Numbers(int size) {
		numbers = new Float[size]; // initializing an array with size that user specified
		numItems = 0; // numItems is 0 for a new instance
		arraySize = size;
	}

	/**
	 * getter for access of numItems in main method
	 * 
	 * @return numItems
	 * 			number of items added to the array
	 */
	public int getNumItems() {
		return numItems;
	}

	/**
	 * Adds a value in the array
	 * 
	 * @param keyboard
	 * 			Scanner object to use for input
	 */
	public void addValue(Scanner keyboard) {
		
		float value;

		// If the array is full, user is prohibited from trying to add more values.
		if (arraySize == numItems) {
			System.out.println("\nArray is full. You cannot add any more value.\n");
			return; // If the array is full, the program exits the method
		}

		System.out.print("Enter value:\n> ");
		value = floatInputHandler(keyboard); // use of method to handle float input

		numbers[numItems] = value;
		numItems++;
	}
	
	/**
	 * Adds multiple values in the array
	 * 
	 * @param keyboard
	 * 			Scanner object to use for input
	 */
	public void addValues(Scanner keyboard) {
		int numberOfNewValues = 0;
		int totalNumOfValues = 0;
		
		System.out.println("How many values do you wish to add? ");
		numberOfNewValues = intInputHandler(keyboard);
		
		totalNumOfValues = numberOfNewValues + numItems;
		
		if (totalNumOfValues > arraySize) {
			System.out.println("Your array is not big enough for that many more values. Please try again.");
			return;
		}
		
		for (int i = 0; i < numberOfNewValues; i++) {
			addValue(keyboard);
		}
		
		System.out.printf("\nYou now have %d values in the array of size %d \n\n", numItems, arraySize);
	}
	
	/**
	 * This method asks for file name to read from, and reads inputs from that file.
	 * 
	 * @param keyboard
	 * 			Scanner object to use for input
	 * @throws IOException
	 * 			Input output exception to be handled in main method
	 */
	public void readFile(Scanner keyboard) throws IOException {
		String fileName = "";
		
		System.out.println("Name of the file to read from:");
		fileName = stringInputHandler(keyboard);
		
		Scanner fileInput = new Scanner(Paths.get(fileName));
		
		try {
			int numberOfItems = fileInput.nextInt();
			float number;
			for (int i = 0; i < numberOfItems; i++) {
				number = fileInput.nextFloat();
				numbers[numItems] = number;
				numItems++;	
			}
		} catch (NoSuchElementException e) {
			System.out.println("Reading from a file with error(s). Please fix the error(s).");
			fileInput.close();
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("You are trying to add too many values. Values are added but only up until array reached a limit.");
			fileInput.close();
		} catch (Exception e) {
			e.printStackTrace();
			fileInput.close();
			throw new RuntimeException("Something very wrong happened. Aborting program\n",e);
		}
		
		fileInput.close();
		
		System.out.printf("\nValid inputs from %s has been read.\n\n", fileName);
		System.out.printf("You now have %d values in the array of size %d\n\n", numItems, arraySize);
		
	}
	
	/**
	 * This method asks for file name to writes to, and writes output to that file.
	 * 
	 * @param keyboard
	 * 			Scanner object to use for input
	 * @throws IOException
	 * 			Input output exception to be handled in main method
	 */
	public void writeFile(Scanner keyboard) throws IOException {
		String fileName = "";
		
		System.out.println("Name of the file to save to:");
		fileName = stringInputHandler(keyboard);
		
		File file = new File(fileName);
		FileWriter fileWriter = new FileWriter(file);
		PrintWriter printWriter = new PrintWriter(file);
		
		if (numItems == 0) {
			System.out.printf("\nNo values in the array. File named %s is created but it is empty.\n\n",fileName);
			printWriter.close();
			fileWriter.close();
			return;
		}
		
		printWriter.printf("%d\n", numItems);
		
		for (int i = 0; i < numItems; i++) {
			printWriter.printf("%.2f\n",numbers[i]);
		}
		
		printWriter.close();
		fileWriter.close();
		
		System.out.printf("\nFile writing to %s has completed. There should be %d values in the file\n\n", fileName, numItems);
	}

	/**
	 * Calculates the average of all the values in the numbers array.
	 * 
	 * @return average
	 * 			float value that represents the average
	 */
	public float calcAverage() {
		
		float average = 0.0f;

		// all the values in the array are first added
		for (int i = 0; i < numItems; i++) {
			average += numbers[i];
		}
		average = average / numItems; // average is calculated by dividing with number of items

		return average;
	}

	/**
	 * This method finds minimum, maximum, and max mod min value in numbers array
	 * 
	 * @return minMaxModArray
	 * 			float array with size 3 to return min, max, and mod
	 */
	public float[] findMinMax() {
		float[] minMaxModArray = new float[3];
		float min = numbers[0]; // initialized to be first element of array
		float max = numbers[0]; // initialized to be first element of array
		float mod = 0.0f;

		// iterates through numbers array to find min and max
		for (int i = 0; i < numItems; i++) {
			if (numbers[i] > max) {
				max = numbers[i];
			} else if (numbers[i] < min) {
				min = numbers[i];
			}
		}

		mod = max % min;

		minMaxModArray[0] = min; // first array element is min
		minMaxModArray[1] = max; // second array element is max
		minMaxModArray[2] = mod; // third array elemtn is mod

		return minMaxModArray;
	}

	@Override
	/**
	 * toString method that is overridden. 
	 * float numbers in the array is formulated into a string of text.
	 * 
	 * @return textToReturn 
	 * 			list of values in the float array
	 */
	public String toString() {
		String textToReturn = "\nNumbers are: \n";

		for (int i = 0; i < numItems; i++) {
			textToReturn += String.format("%.2f\n", numbers[i]);
		}

		return textToReturn;
	}

	/**
	 * This method handles float input w/ exception handling. 
	 * This method is created to declutter code.
	 * 
	 * @param keyboard 
	 * 			Scanner object to read input
	 * @return input 
	 * 			User's input float value
	 */
	private static float floatInputHandler(Scanner keyboard) {
		float input = 0.0f;
		boolean validation = false;

		do {
			try {
				input = keyboard.nextFloat();
				validation = true; // this line only gets read if input is correct, otherwise validation stays false
			} catch (InputMismatchException e) {
				System.out.print("Not a float input. Please try again.\nEnter value:\n> ");
				keyboard.nextLine();
			}
		} while (!validation);

		return input;
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
	
	/**
	 * This method handles String input w/ exception handling.
	 * This method is created to declutter code.
	 * 
	 * @param keyboard
	 * 			Scanner object to read input
	 * @return input
	 * 			User's input String 
	 */
	private static String stringInputHandler(Scanner keyboard) {
		String input = "";
		boolean validation = false;
		
		do {
			try {
				input = keyboard.nextLine();
				validation=true;
				if (input == null) { // negative integer input is not accepted
					System.out.print("You've entered an invalid name. Please try again.\n> ");
					validation=false;
				}
			} catch (InputMismatchException e) {
				System.out.print("Please only input a string of text. Please try again.\n> ");
				keyboard.nextLine();
			} 
		} while (!validation);
		
		return input;
	}
}
