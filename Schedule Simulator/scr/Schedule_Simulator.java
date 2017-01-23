package schedule_Sim;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Schedule_Simulator {

	public static int quantum;
	public static int cst; // Context Switch Time
	public static List<Process> priorityList = new ArrayList<Process>();
	public static int globalTimeCounter;
	public static boolean finished = false;
	private static int ec;
	private static String fileName;

	/**
	 * checks if the entered file is found. if not, a new one will be generated
	 * and the program continues
	 */
	private static void fileCheck() {
		ec++;
		try {
			Scanner scan = new Scanner(new File(fileName));
			ProcessHandler hand = new ProcessHandler(scan);
			hand.start();
		} catch (FileNotFoundException e) {
			System.out.println("\nAttempt " + ec + "/3.\nFile not found, creating a new one.\n"
					+ "The program will continue automatically.\n");
			new Input();
			fileName = "input.txt";

			if (ec < 3) {
				fileCheck();
			}

			System.exit(1);
		}
	}

	public static void main(String[] args) {

		if (args.length != 3) {
			System.out.println("Incorrect amount of arguments.\n" + "Arguments:\n"
					+ "\"input_file\" \"quantum\" \"context_switch_time\"\n"
					+ "If the file is not found, a new one will automatically be generated.");
			System.exit(1);
		}

		Scanner in = new Scanner(System.in);

		System.out.println(
				"Welcome to our scheduling simulator!\nWould you like to create a new input file instead of using the given one? (Y/N)");
		boolean flag = false;
		while (!flag) {
			String c = in.next().toLowerCase();
			if (c.equals("y")) {
				System.out.println("File will be created.\n");
				new Input();
				flag = true;
			} else if (c.equals("n")) {
				System.out.println("No file will be created.");
				flag = true;
			} else {
				System.out.println("Wrong input, try again.");
			}
		}

		in.close();

		fileName = args[0];
		quantum = Integer.parseInt(args[1]);
		cst = Integer.parseInt(args[2]);

		fileCheck();
	}
}
