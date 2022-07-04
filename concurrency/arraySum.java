package Module8;

import java.util.Arrays;
import java.util.Random;

/**
 * @author Alisha Hannan
 *Module 8
 */

/*
 * Set of variables used by both classes Amount of numbers to total startTime is
 * the starting point of the thread endTime is the ending time of the thread sum
 * is total computed in each thread
 */
class variables {
	public static int size = 200000000; 
	public long startTime = 0;
	public long endTime = 0;
	public int sum = 0;
}

/*
 * Class for single thread Uses instance of variables class Contains 2 methods,
 * findSum and run
 */
class single extends Thread {

	variables a = new variables();

	/*
	 * Creates an array of random integers based on static size integer Returns the
	 * sum of the integers in the array
	 */
	int findSum() {

		Random rando = new Random();
		int min = 1;
		int max = 10;
		int[] theSum = new int[variables.size];

		for (int i = 0; i < theSum.length; i++) {
			theSum[i] = rando.nextInt((max - min) + 1) + min;
		}

		return Arrays.stream(theSum).sum();
	}

	/*
	 * Sets startTime, endTime, and sum for instance a, prints completed output
	 */
	public void run() {
		a.startTime = System.nanoTime();
		a.sum = findSum();

		a.endTime = System.nanoTime() - a.startTime;
		System.out.println("Sum of single thread: " + a.sum + " completed in: " + a.endTime + " nanoseconds");
	}
}

/*
 * Class for using multiple threads Uses instance b of variables Initializes an
 * array of threads Initializes int threads for the amount of threads that will
 * be used Initializes loopSize by dividing the variables.size by the amount of
 * threads chosen to compute
 */
class multi implements Runnable {

	variables b = new variables();
	private Thread splitThreads[];
	private int loopSize, threads;

	public multi() {
		threads = 10;
		splitThreads = new Thread[threads];
		loopSize = (variables.size / threads);
	}

	/*
	 * Synchronizes the b.sum value so that it is only updated after each thread has
	 * been run Takes to calculated totals from the smaller amount of inputs and
	 * finds their totals
	 * 
	 */
	private synchronized int totalSums(int value) {
		b.sum += value;
		return b.sum;
	}

	/*
	 * Similar to findSum in single class Uses a division of the total amount of
	 * inputs requested for the loop Returns the sum of the calculated variables
	 */
	public int findSum() {

		Random rando = new Random();
		int sum = 0;
		int min = 1;
		int max = 10;
		int[] theSum = new int[loopSize];

		for (int i = 0; i < theSum.length; i++) {
			theSum[i] = rando.nextInt((max - min) + 1) + min;
		}
		sum = Arrays.stream(theSum).sum();

		return sum;
	}

	/*
	 * Thread calls the totalSums method, which uses the return of the findSum
	 * method
	 */
	public void run() {

		totalSums(findSum());
	}

	/*
	 * Method to create and run multiple threads Creates additional threads for the
	 * splithThread array Starts each thread after after it is created Second loop
	 * is used to join the newly created threads b.endTime is not updated until the
	 * threads have completed Prints out the calculated sum and ending time of the
	 * thread
	 */
	public void runThreads() {
		b.startTime = System.nanoTime();
		for (int i = 0; i < splitThreads.length; i++) {
			splitThreads[i] = new Thread(this);
			splitThreads[i].start();
		}
		for (int i = 0; i < threads; i++) {
			try {
				splitThreads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		b.endTime = System.nanoTime() - b.startTime;
		System.out.println("Sum of multiple threads: " + b.sum + " completed in: " + b.endTime + " nanoseconds");
	}
}

public class arraySum {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws InterruptedException {
		single one = new single();
		one.start();

		multi many = new multi();
		many.runThreads();
	}
}
