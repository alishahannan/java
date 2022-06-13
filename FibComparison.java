
/**
 * Alisha Hannan
 * CEN3024C
 * Program compare efficiency of recursive and iterative functions
 */

import java.util.*;
import java.io.*;

/**
 * Class of variables to use with each thread. Includes long start and finish
 * variables to store nanosecond time stamps. Results of Fibonacci computations.
 */

class variables {
	long start;
	long finish;
	int result;
}

/**
 * Thread to perform Fibonacci sequence computations.
 */
class fibonacci extends Thread {
	/**
	 * Variables to use within the threads. Int n tells which Fibonacci term to
	 * compute to. String type to determine which methods to run
	 */
	int n = 40;
	String type;

	variables v = new variables();

	/**
	 * / Sets type variable for thread.
	 */
	public fibonacci(String type) {
		this.type = type;
	}

	/**
	 * Solves the Fibonacci sequence using recursion. Function repeatedly calls
	 * itself until base case is met.
	 */
	int recursive(int x) {
		if (x == 0 || x == 1)
			return x;
		return (recursive(x - 1) + recursive(x - 2));
	}

	/**
	 * Solves the Fibonacci sequence using iteration. Uses loops to move towards the
	 * condition value.
	 */
	int iterative(int x) {
		int v1 = 0, v2 = 1, sum = 0;
		if (x == 0 || x == 1)
			return x;
		for (int i = 2; i <= n; i++) {
			sum = v1 + v2;
			v1 = v2;
			v2 = sum;
		}
		return sum;
	}

	/**
	 * Prints results of final computed term in the sequence.
	 */
	void printResults(String t, int x, int a, long f) {
		System.out.println(t + " thread found Fibonacci term " + x + ": " + a + " in " + f + "ns");
	}

	/**
	 * Stores the current Fibonacci term n and the number at the n'th term in an
	 * ArrayList. Returns the ArrayList with a comma delimiter.
	 */
	public static ArrayList<String> listResults(int n, long f) {
		ArrayList<String> results = new ArrayList<String>();
		results.add(n + "," + f);
		return results;
	}

	/**
	 * Takes in an ArrayList and writes the list to a text file.
	 */
	public static void writeFiles(String file, ArrayList<String> output) {
		String path = "C:/Users/alish/Documents/" + file + ".txt";
		try (FileWriter writer = new FileWriter(path, true); BufferedWriter bw = new BufferedWriter(writer)) {
			for (String i : output) {
				bw.write(i);
				bw.newLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Executes the thread depending on String input
	 */
	public void run() {
		/**
		 * Will compute the Fibonacci sequence till the n term is reached.
		 */
		for (int x = 0; x <= n; x++) {

			if (type == "recursive") {
				variables r = new variables();
				r.start = System.nanoTime();
				r.result = recursive(x);
				try {
					sleep(10);
				} catch (InterruptedException e) {
				}
				r.finish = System.nanoTime() - r.start;

				writeFiles(type, listResults(x, r.finish));

				if (x == n) {
					printResults(type, x, r.result, r.finish);
				}
			}

			if (type == "iterative") {
				variables i = new variables();
				i.start = System.nanoTime();
				i.result = iterative(x);
				try {
					sleep(10);
				} catch (InterruptedException e) {
				}
				i.finish = System.nanoTime() - i.start;

				writeFiles(type, listResults(x, i.finish));

				if (x == n) {
					printResults(type, x, i.result, i.finish);
				}
			}
		}
	}
}

public class FibComparison {

	/**
	 * Method to clear output text files before running program. Empties previous
	 * contents of file. Adds headers for input values and output values based on
	 * which method will be run
	 */
	public static void clearFile(String file) {
		String path = "C:/Users/alish/Documents/" + file + ".txt";
		try (FileWriter writer = new FileWriter(path); BufferedWriter bw = new BufferedWriter(writer)) {
			bw.write("input," + file);
			bw.newLine();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Main function of the program
	 */
	public static void main(String[] args) {

		/**
		 * Resets the output text files to just have the headers
		 */
		clearFile("recursive");
		clearFile("iterative");

		/**
		 * Start the Fibonacci computation threads. Runs different methods depending on
		 * String input. Outputs results in separate files.
		 */
		fibonacci rec = new fibonacci("recursive");
		rec.start();

		fibonacci iter = new fibonacci("iterative");
		iter.start();
	}
}
