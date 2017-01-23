package schedule_Sim;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class ProcessHandler extends Thread {

	private Scanner scanner;
	private List<Process> processes = new ArrayList<Process>();
	private int temp = Schedule_Simulator.globalTimeCounter - 1;

	public ProcessHandler(Scanner scan) {
		scanner = scan;
	}

	/**
	 * the file gets read and each process is stored in an individual object the
	 * entire list of processes are then sorted according to their arrival time
	 */
	private void sort() {
		String name, temp;
		int priority;
		int arrivalTime;
		int counter = 0;

		scanner.useDelimiter("[^T^\\d]+");
		name = "" + scanner.next();

		while (scanner.hasNextLine()) {
			priority = scanner.nextInt();
			arrivalTime = scanner.nextInt();
			processes.add(new Process(name, priority, arrivalTime));

			temp = scanner.next();

			while (!temp.substring(0, 1).equals("T") && scanner.hasNext()) {
				processes.get(counter).addBurst(Integer.parseInt(temp), scanner.nextInt());
				if (scanner.hasNext())
					temp = scanner.next();
			}

			if (!scanner.hasNext()) {
				break;
			}

			name = temp;
			counter++;
		}

		Collections.sort(processes, new Comparator<Process>() {
			@Override
			public int compare(Process o1, Process o2) {
				return o1.getArrivalTime() - o2.getArrivalTime();
			}
		});
	}

	/**
	 * if the list contains a process which's arrival time is equal to the
	 * global time counter, it gets pushed into the global list of processes
	 * which is then handled by the schedule worker
	 */
	public void push() {
		for (int i = 0; i < processes.size(); i++) {
			if (processes.get(i).getArrivalTime() == Schedule_Simulator.globalTimeCounter) {
				Schedule_Simulator.priorityList.add(processes.get(i));
				processes.remove(i);
				i--;
			}
		}
	}

	/**
	 * as long the internal list of processes is not empty, this thread runs in
	 * a constant loop
	 */
	public void run() {
		sort();
		push();

		ScheduleWorker worker = new ScheduleWorker();
		worker.start();

		while (true) {
			try {
				Thread.sleep(0);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}

			if (temp != Schedule_Simulator.globalTimeCounter) {
				push();
				temp = Schedule_Simulator.globalTimeCounter;
			}
			if (processes.size() == 0) {
				break;
			}
		}

		Schedule_Simulator.finished = true;

	}

}
