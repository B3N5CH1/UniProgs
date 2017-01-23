package schedule_Sim;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ScheduleWorker extends Thread {

	private List<Process> processes = new ArrayList<Process>();
	private int runningTime, q, p, csts;

	public void run() {
		fetch();
		work();
	}

	/**
	 * we control, if something new arrived in the global list, if yes -> put
	 * this in our local list. we make a sleep/delay, so the thread
	 * "ProcessHandler" have enough time to push the new processes to the global
	 * list.
	 */
	private void fetch() {
		q++;

		try {
			Thread.sleep(2);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}

		while (Schedule_Simulator.priorityList.size() > 0) {
			processes.add(Schedule_Simulator.priorityList.get(0));
			Schedule_Simulator.priorityList.remove(0);
		}
	}

	/**
	 * If a contextSwitch occurs, we note here and increase the time/counter
	 */
	public void contextSwitch() {
		for (int i = 0; i <= Schedule_Simulator.cst; i++) {
			Schedule_Simulator.globalTimeCounter++;
			fetch();
		}

		q = 0;
		csts++;
	}

	/**
	 * over here all the processes, which are in the local list, get processed
	 * according to the given task we control the priority, the quantum etc
	 */
	private void work() {
		while (true) {

			Collections.sort(processes, new Comparator<Process>() {
				@Override
				public int compare(Process o1, Process o2) {
					return o2.getPriority() - o1.getPriority();
				}
			});

			if (processes.size() > 0) {
				if (!processes.get(p).isFirstTime()) {
					processes.get(p).setStart(Schedule_Simulator.globalTimeCounter);
				}

				if (processes.get(p).getBurst(0) > 0) {
					processes.get(p).setBurst(processes.get(p).getBurst(0) - 1);
				} else {
					processes.get(p).removeBurst();
					if (processes.get(p).getBurstsSize() == 0) {
						processes.get(p).setEnd(Schedule_Simulator.globalTimeCounter - 1);
						runningTime += processes.get(p).getEndTime() - processes.get(p).getStartTime();
						System.out.println(processes.get(p).getName() + ": " + processes.get(p).getStartTime() + ", "
								+ processes.get(p).getEndTime());
						processes.remove(p);
						if (p > 0 ) p--;
					}

					Schedule_Simulator.globalTimeCounter--;
					contextSwitch();
				}
			}

			if (Schedule_Simulator.quantum == q) {
				q = 0;
				if (processes.size() > p + 1) {
					if (processes.get(p).getPriority() == processes.get(p + 1).getPriority()) {
						p++;
						Schedule_Simulator.globalTimeCounter--;
						contextSwitch();
					} else {
						p = 0;
						Schedule_Simulator.globalTimeCounter--;
						contextSwitch();
					}
				} else {
					if (p > 0) {
						contextSwitch();
						p = 0;
					}
				}
			}

			Schedule_Simulator.globalTimeCounter++;
			fetch();

			if ((Schedule_Simulator.finished == true) && (processes.size() == 0)) {
				break;
			}
		}
		System.out.println("\nTime spent on context switches: " + csts * Schedule_Simulator.cst);
		System.out.println("Time spent on running threads: " + runningTime);
	}

}
