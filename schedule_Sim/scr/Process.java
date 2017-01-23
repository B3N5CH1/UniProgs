package schedule_Sim;

import java.util.ArrayList;
import java.util.List;

public class Process {

	private String name;
	private int priority;
	private int arrivalTime;
	private int startTime;
	private int endTime;
	private boolean firstAccess = false;
	private List<Integer> bursts = new ArrayList<Integer>();

	/**
	 * creates a new process object
	 * 
	 * @param i
	 *            the name
	 * @param prio
	 *            its priority
	 * @param arr
	 *            the arrival time
	 */
	public Process(String i, int prio, int arr) {
		name = i;
		priority = prio;
		arrivalTime = arr;
	}

	/**
	 * gets the name of the process
	 * 
	 * @return the name of the process
	 */
	public String getName() {
		return name;
	}

	/**
	 * adds a pair of bursts to the list of bursts
	 * 
	 * @param cpu
	 *            the cpu burst
	 * @param io
	 *            the io burst
	 */
	public void addBurst(int cpu, int io) {
		bursts.add(cpu);
		bursts.add(io);
	}

	/**
	 * gives the burst at index i
	 * 
	 * @param i
	 *            the index in the list of bursts
	 * @return the desired burst
	 */
	public int getBurst(int i) {
		return bursts.get(i);
	}

	/**
	 * sets a specific burst at position i new
	 * 
	 * @param i
	 *            position in the list
	 */
	public void setBurst(int i) {
		bursts.set(0, i);
	}

	/**
	 * removes the first burst
	 */
	public void removeBurst() {
		bursts.remove(0);
	}

	/**
	 * checks the size of the burst list
	 * 
	 * @return the size of the burst list
	 */
	public int getBurstsSize() {
		return bursts.size();
	}

	/**
	 * this sets the start time of the process (when the first burst was
	 * decreased)
	 * 
	 * @param start
	 *            the start time (according to the global time counter)
	 */
	public void setStart(int start) {
		firstAccess = true;
		startTime = start;
	}

	/**
	 * returns the start time of the process (used for calculation of used time)
	 * 
	 * @return the start time of the process
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * this sets the end time of the process (when the last burst was removed)
	 * 
	 * @param end
	 *            the end time (according to the global time counter)
	 */
	public void setEnd(int end) {
		endTime = end;
	}

	/**
	 * returns the end time of the process (used for calculation of used time)
	 * 
	 * @return the end time of the process
	 */
	public int getEndTime() {
		return endTime;
	}

	/**
	 * returns the priority of the current process
	 * 
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * returns the arrival time of the current process
	 * 
	 * @return the arrival time
	 */
	public int getArrivalTime() {
		return arrivalTime;
	}

	/**
	 * a flag to check, if the process is used the first time. if this returns
	 * true, the start time gets set)
	 * 
	 * @return true if this is the first time, the process gets touched for
	 *         decreasing a burst
	 */
	public boolean isFirstTime() {
		return firstAccess;
	}

	/**
	 * gives a nice String with the process's name and priority
	 */
	@Override
	public String toString() {
		return "" + name + ": " + priority;
	}

}
