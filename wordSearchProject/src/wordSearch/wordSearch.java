/**
 * @author Alisha Hannan
 * CEN 3024C
 * Program to count occurrences of words in a poem file, after 
 * stripping the file of HTML tags
 *
 */
package wordSearch;

import java.util.*;
import java.util.Map.*;
import java.io.*;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import org.jsoup.nodes.*;

/* class of variables frequently used throughout program
 */
class v {
	static String title = "";
	static String author = "";
	static String path = "C:/Users/alish/Documents/1065-h.htm";
	static HashMap<String, Integer> wordMap = new HashMap<>();
	static List<Entry<String, Integer>> occurList = new LinkedList<>();
}

public class wordSearch {

	/*
	 * Method that takes in a string and removes punctuation characters and non word
	 * characters, trims white space after removing punctuation, returns the
	 * formatted String
	 */
	public static String removePunct(String source) {
		source = source.replaceAll("\\p{Punct}", "").trim();
		source = source.replaceAll("\\W", " ");
		return source;
	}

	/*
	 * Method that takes in a String of a path to a file, the contents of the file
	 * are converted to a document, the title, author, and poem are extracted from
	 * the document, v class variables are set from the derived elements, and a new
	 * String is returned with only the requested parts of the file, the removePunct
	 * method is used format the file
	 */
	public static String makeDoc(String path) {

		String formatted = "";
		try {

			Document doc = Jsoup.parse(new File(path));
			Elements h1 = doc.getElementsByTag("h1");
			Elements h2 = doc.getElementsByClass("no-break");
			Elements divTag = doc.getElementsByClass("chapter");

			v.title = h1.text();
			v.author = h2.text();
			formatted = (h1.text() + " " + h2.text() + " " + divTag.text()).toLowerCase();

		} catch (IOException e) {

		}
		return removePunct(formatted);
	}

	/*
	 * Method that takes in a String, reads the string into an Array List of
	 * individual words, split by the delimiter " ", and returns the List
	 */
	public static List<String> separate(String doc) {
		List<String> words = null;
		try (StringReader reader = new StringReader(doc); BufferedReader br = new BufferedReader(reader)) {
			String line = br.readLine();
			words = new ArrayList<>(Arrays.asList(line.split(" ")));

		} catch (IOException e) {
			e.printStackTrace();
		}
		return words;
	}

	/*
	 * Method that takes in a List of Strings, and HashMap of String keys and
	 * Integer values, each word from the List is scanned, and each time a word is
	 * read the count value increases, the word values are set as the Key values for
	 * the HashMap to ensure no duplicates, and the count number is set as the Value
	 * for the word Key, the HashMap is returned
	 */
	public static HashMap<String, Integer> wordCount(List<String> data, HashMap<String, Integer> occur) {

		for (String line : data) {
			Scanner scan = new Scanner(line);
			while (scan.hasNext()) {
				String word = (scan.next());
				Integer count = occur.get(word);
				if (count != null)
					count++;
				else
					count = 1;
				occur.put(word, count);
			}
			scan.close();
		}
		return occur;
	}

	/*
	 * Method that takes in a HashMap, reads in the entry sets into a List, and
	 * sorts the sets based on the Map values, returns a List of sorted sets
	 */
	public static List<Entry<String, Integer>> sortCount(HashMap<String, Integer> og) {
		List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(og.entrySet());

		Collections.sort(list, new Comparator<Entry<String, Integer>>() {
			public int compare(Entry<String, Integer> v1, Entry<String, Integer> v2) {
				return (v2.getValue()).compareTo(v1.getValue());
			}
		});
		return list;
	}

	/*
	 * main methods calls the makeDoc, separate, wordCount, and sortCount methods,
	 * using the v class path variable, and returns updated wordMap and occurList
	 * variables from the v class, the Keys and Values from the occurList are
	 * printed, as well as the title and author of the poem that was stored in the
	 * makeDoc method
	 */
	public static void main(String[] args) {

		v.occurList = sortCount(wordCount(separate(makeDoc(v.path)), v.wordMap));

		System.out.println("Title: " + v.title + "\n" + "Author: " + v.author);
		for (int i = 0; i < 20; i++) {
			System.out.println((i + 1) + ": " + (v.occurList.get(i)).getKey() + " occurs "
					+ (v.occurList.get(i)).getValue() + " times");
		}
	}
}
