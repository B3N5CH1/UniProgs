package schedule_Sim;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Input {

	/**
	 * This part is used to set the different variables for the input file.
	 * Changing these values will result in an adjustment of the file.
	 */

	private static int widthLimit = 0; // The max amount of CPU/IO bursts
	private static int depthLimit = 0; // The max amount of threads

	private static int maxArrivalTime = 0; // Max arrival time
	private static int maxCPUBurst = 0; // Max CPU burst
	private static int maxIOBurst = 0; // Max IO burst

	private static int minPriority = 0; // Min priority
	private static int maxPriority = 0; // Max priority

	/**
	 * Input variable block end.
	 */

	private Random rd = new Random();

	private String burst() {
		int cpu = rd.nextInt(maxCPUBurst) + 1;
		int io = rd.nextInt(maxIOBurst) + 1;
		return "(" + cpu + "," + io + ")";
	}

	private String lineStart(int index) {
		String ret = "T" + index + "[";
		int dif = maxPriority - minPriority;
		int prio = rd.nextInt(dif + 1) + minPriority;
		ret += prio + "]: ";
		ret += rd.nextInt(maxArrivalTime + 1);
		ret += ", ";
		return ret;
	}

	private void createFile() {
		try (FileWriter file = new FileWriter("input.txt")) {
			int depth = rd.nextInt(depthLimit) + 1;

			for (int i = 1; i <= depth; i++) {
				file.write(lineStart(i));
				int width = rd.nextInt(widthLimit);
				for (int j = 1; j < width; j++) {
					file.write(burst() + ", ");
				}
				file.write(burst() + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This automatically (also magically) creates a new input file
	 */
	public Input() {
		readSettings();
		createFile();
	}

	/**
	 * reads the settings from the external file
	 */
	private void readSettings() {
		try (Scanner scan = new Scanner(new File("src/schedule_Sim/InputCreatorSettings.txt"))) {
			scan.useDelimiter("[\\D]+");

			widthLimit = scan.nextInt();
			depthLimit = scan.nextInt();

			maxArrivalTime = scan.nextInt();
			maxCPUBurst = scan.nextInt();
			maxIOBurst = scan.nextInt();

			minPriority = scan.nextInt();
			maxPriority = scan.nextInt();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
}
