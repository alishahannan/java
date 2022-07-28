/**
 * @author Alisha Hannan
 * @version 2.4
 * @since 1.0
 * CEN 3024C
 * Program to count occurrences of words in a poem file, after 
 * stripping the file of HTML tags, with use of a UI to perform actions.
 *
 */
package application;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Map.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javafx.application.Application;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.*;
import javafx.stage.Stage;

/**
 * Class containing frequently used throughout the program.
 * 
 * @param title     String used for the title of the poem in the document to be
 *                  imported.
 * @param author    String used for the author of the poem in the document to be
 *                  imported.
 * 
 * @param path      String of the path to the document to import into the
 *                  program.
 * 
 * @param wordMap   HashMap of String keys and Integer variables, will be used
 *                  to keep track of how often singular words occur in the
 *                  imported poem. Each word will be stored as the String keys
 *                  and how often the word occurs will be stored in the Integer
 *                  values. Use of the HashMap ensures there are not duplicate
 *                  words as the keys for the program.
 * @param occurList LinkedList of HashMap entries, that use String and Integer
 *                  parameters. Will be used to sort the occurrences of words
 *                  with the more often occurring first.
 */
class v {
	static String title = "";
	static String author = "";
	static String path = "C:/Users/alish/Documents/1065-h.htm";
	static HashMap<String, Integer> wordMap = new HashMap<>();
	static List<Entry<String, Integer>> occurList = new LinkedList<>();
}

/**
 * Main is an extension of the Application class used for JavaFX. Main creates a
 * windowed program titled 'Word Search Program' that displays two buttons below
 * explanation text, explaining what each button does. A box at the bottom of
 * the window will display the occurrences of words for a poem, as well as the
 * title and author of the poem at the top of the windows when the show button
 * is selected. The clear button clears anything from the window that was
 * displayed by the show button.
 * 
 * @see Application
 */

public class Main extends Application {

	/**
	 * Returns String formatted by removing punctuation and non-word characters from
	 * the given String. It first replaces all punctuation from the String without a
	 * replacement character, and trims any whitespace from the String. All non-word
	 * characters are replaced with a single space character, and trimmed of
	 * additional white space. Any space made of two space characters is replaced
	 * with a single space between words in the String.
	 * 
	 * @param source String source to be formatted
	 * @return Formatted source String after replacing punctuation, non-word
	 *         characters, and double spaces
	 */
	public static String removePunct(String source) {
		source = source.replaceAll("\\p{Punct}", "").trim();
		source = source.replaceAll("\\W", " ").trim();
		source = source.replaceAll("  ", " ");
		return source;
	}

	/**
	 * Method that takes in a String path to a file location. The contents at that
	 * location are converted to a document that contains HTML elements. The method
	 * returns a newly formated String. It only returns sections of the document
	 * that have specified parts of the poem without any HTML tags, or punctuation
	 * and non-word characters due to the removePunct method within the method.
	 * <p>
	 * Method also sets the v class's title and author values.
	 * 
	 * @param path String to the file that is to be formatted
	 * @return Formatted String with only the text contained in the h1, h2, and
	 *         chapter sections of a given documented
	 * 
	 * @see <a href=
	 *      "https://jsoup.org/apidocs/org/jsoup/parser/package-summary.html">parse</a>
	 */
	public static String makeDoc(String path) {

		String formatted = "";
		try {

			Document doc = Jsoup.parse(new File(path));
			Elements h1 = doc.getElementsByTag("h1");
			Elements h2 = doc.getElementsByTag("h2");
			Elements divTag = doc.getElementsByClass("chapter");

			v.title = h1.text();
			v.author = h2.text();
			formatted = (h1.text() + " " + h2.text() + " " + divTag.text()).toLowerCase();

		} catch (IOException e) {

		}
		return removePunct(formatted);
	}

	/**
	 * Method that returns a list of Strings. Uses a String reader to read a large
	 * string of multiple words, separated by spaces. The buffered reader reads each
	 * line of the given string. When adding variables to the words list, the string
	 * is split whenever a " " is found, resulting in a string of an individual word
	 * instead of multiple word strings.
	 * 
	 * @param doc String of words to be separated into the list
	 * @return list of individual word Strings
	 * @see Reader
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

	/**
	 * Method that takes in a List of Strings, and a HashMap of String keys and
	 * integer values. The strings from the list are scanned, each time starting the
	 * count of that word being scanned at 1. The Strings are added as the keys to
	 * the HashMap, as well as the count for that key. Each time a string that has
	 * already been added to the keys has been scanned, the count for that key is
	 * increased.
	 * 
	 * @param data  List of single word strings used to make the HashMap of word
	 *              occurrences
	 * @param occur HashMap of String keys and Integer values, keys are taken from
	 *              the data List and a count of how often the word is counted is
	 *              used for the Integer value
	 * @return the HashMap with the updated values
	 * @see Scanner
	 */
	public static HashMap<String, Integer> wordCount(List<String> data, HashMap<String, Integer> occur) {

		// need to add insert term here//
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

	/**
	 * Takes in a HashMap, iterates through the entries of the HashMap to insert the
	 * entries into a connected database. The database is updated until all entries
	 * of the HashMap have been iterated through.
	 * 
	 * @param hashMap HashMap of String keys and Integer values, showing the
	 *                occurrence amount for each word.
	 * @throws Exception
	 */
	public static void post(HashMap<String, Integer> hashMap) throws Exception {
		String var1 = null;
		int var2 = 0;

		for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
			var1 = entry.getKey();
			var2 = entry.getValue();

			try {
				Connection con = getConnection();
				PreparedStatement posted = con
						.prepareStatement("INSERT INTO words (word,freq) VALUES ('" + var1 + "','" + var2 + "')");
				posted.executeUpdate();
			} catch (Exception e) {
				System.out.println(e);
			} finally {
				System.out.println("Insert Completed.");
			}
		}
	}

	/**
	 * Methods takes in HashMap og of values, and reads the paired entries into a
	 * List. The list is sorted based on the Integer values of the HashMap entries,
	 * using the collections sort method to return a list sorted from highest to
	 * lowest occurrences of the String keys. The key and values are kept together
	 * due to the list being made up of HashMap entries.
	 * 
	 * @param og the original HashMap of values, unsorted
	 * @return a list of String and Integer entries made from the og HashMap, sorted
	 *         based on the Integer values
	 * @see Collections
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

	/**
	 * Method to create interactive user interface for the program and call the
	 * program methods. Before creating the UI, the sortCount, wordCount, separate,
	 * and makeDoc methods are called using the v class path and wordMap variables.
	 * The return of those methods is set to the v class's occurList.
	 * <p>
	 * A stage or window is created with default text place holders to be used by
	 * the program and text with instructions already displayed. The window displays
	 * two buttons, with event handlers to either show or clear information from the
	 * window.
	 * <p>
	 * The show button's event handler uses variables from the v class. It sets the
	 * text for the header Text using the author and title variables, and adds items
	 * to the ListView list from the occurList or through the get method's returned
	 * array. The clear button sets the header back to blank, and clears the list.
	 * 
	 * @see Stage
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {

		v.occurList = sortCount(wordCount(separate(makeDoc(v.path)), v.wordMap));
		createTable();
		post(v.wordMap);

		try {

			primaryStage.setTitle("Word Search Program");
			Pane p = new Pane(); // pane for content
			p.prefHeight(547);
			p.prefWidth(360);
			Scene scene = new Scene(p, 360, 547);

			Text header = new Text("");
			header.setLayoutX(130);
			header.setLayoutY(60);
			header.prefHeight(89);
			header.prefWidth(200);
			header.setTextAlignment(TextAlignment.CENTER);
			p.getChildren().add(header);

			Text text = new Text("Click \"Show\" to display 20 most" + " often occurring words in the poem."
					+ " Click \"Clear\" to clear the list.");
			text.setLayoutX(84);
			text.setLayoutY(112);
			text.prefHeight(89);
			text.prefWidth(192);
			text.setWrappingWidth(192);
			text.setTextAlignment(TextAlignment.CENTER);
			p.getChildren().add(text);

			Button show = new Button("Show");
			show.setLayoutX(84);
			show.setLayoutY(175);
			p.getChildren().add(show);

			Button clear = new Button("Clear");
			clear.setLayoutX(233);
			clear.setLayoutY(175);
			p.getChildren().add(clear);

			ListView list = new ListView();
			list.setLayoutX(80);
			list.setLayoutY(259);
			list.setMaxHeight(210);
			list.setMaxWidth(200);
			p.getChildren().add(list);

			EventHandler<ActionEvent> display = new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					header.setText(v.title + "\n" + v.author);

					try {
						ArrayList<String> show = get();
						for (String value : show) {
							list.getItems().add(value);
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}

					/*
					 * //for use of the original occurList for (int i = 0; i < 20; i++) {
					 * list.getItems().add((i + 1) + ": " + (v.occurList.get(i)).getKey() +
					 * " occurs " + (v.occurList.get(i)).getValue() + " times"); }
					 */
				}
			};

			show.setOnAction(display);

			EventHandler<ActionEvent> reset = new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					header.setText("");
					list.getItems().clear();
				}
			};

			clear.setOnAction(reset);

			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Contains the launch method to start and display the application.
	 * 
	 * @param args actions contained in the Main class
	 */
	public static void main(String[] args) throws Exception {
		launch(args);
	}

	/**
	 * Connects to a database, and prepares a statement to select column information
	 * to view, sorted descending by the freq column and limited to the top 20 items
	 * in the database. After executing the query, the results of the query or
	 * iterated through, using each set of values to fill the ArrayList of the
	 * method with String values using the table information.
	 * 
	 * @return an array of strings based on the selection of the database.
	 * @throws Exception
	 */
	public static ArrayList<String> get() throws Exception {
		try {
			Connection con = getConnection();
			PreparedStatement statement = con
					.prepareStatement("SELECT word,freq FROM words ORDER BY freq DESC limit 20");

			ResultSet result = statement.executeQuery();
			int i = 1;
			ArrayList<String> array = new ArrayList<String>();
			while (result.next()) {

				array.add((i + "- " + result.getString("word")) + " occurs " + (result.getInt("freq")) + " times");
				i++;
			}
			System.out.println("All records have been selected!");

			return array;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	/**
	 * Connects to a database, and prepares a statement to create a table for the
	 * database if one doesn't exist, with word column as the primary key and freq
	 * column. Executes an update to the database, printing a message to show the
	 * function was completed.
	 * 
	 * @throws Exception
	 */
	public static void createTable() throws Exception {
		try {
			Connection con = getConnection();
			PreparedStatement create = con.prepareStatement(
					"CREATE TABLE IF NOT EXISTS words(word VARCHAR(40) NOT NULL, freq INT NOT NULL, PRIMARY KEY(word)");
			create.executeUpdate();

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			System.out.println("Function complete.");
		}
		;
	}

	/**
	 * Establishes connection to a local database using provided driver, url,
	 * username and password for the database.
	 * 
	 * @return the established connection, allowing access to modify or view content.
	 * @throws Exception
	 */
	public static Connection getConnection() throws Exception {
		try {
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/word_occurrences";
			String username = "tempUser";
			String password = "apass";
			Class.forName(driver);

			Connection conn = DriverManager.getConnection(url, username, password);
			System.out.println("Connected");
			return conn;

		} catch (Exception e) {
			System.out.println(e);

			return null;
		}
	}
}
