/**
 * @author Alisha Hannan
 * CEN 3024C
 * Program to count occurrences of words in a poem file, after 
 * stripping the file of HTML tags, with use of a UI
 *
 */
package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.text.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
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

public class Main extends Application {

	/*
	 * Method that takes in a string and removes punctuation characters and non word
	 * characters, trims white space after removing punctuation, returns the
	 * formatted String
	 */
	public static String removePunct(String source) {
		source = source.replaceAll("\\p{Punct}", "").trim();
		source = source.replaceAll("\\W", " ").trim();
		source = source.replaceAll("  ", " ");
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
			Elements h2 = doc.getElementsByTag("h2");
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
	 * Stage for UI, enables user interaction with program, when Stage is launched
	 * the makeDoc, separate, wordCount, and sortCount methods are called, takes in
	 * the v classes path variables, updates the v class wordMap and occurList
	 * variables, the scene contains a panel, with 2 Text items, 2 Button items, and
	 * a ListView item, there are 2 EventHandlers, 1 that updates the header Text
	 * and ListView items, the ListView items are the first 20 of the occurList, the
	 * 2nd clears/resets the Text and ListView items
	 */

	@Override
	public void start(Stage primaryStage) {

		v.occurList = sortCount(wordCount(separate(makeDoc(v.path)), v.wordMap));

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

					for (int i = 0; i < 20; i++) {
						list.getItems().add((i + 1) + ": " + (v.occurList.get(i)).getKey() + " occurs "
								+ (v.occurList.get(i)).getValue() + " times");
					}
				}
			};

			show.setOnAction(display);

			EventHandler<ActionEvent> reset = new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					header.setText(" ");
					list.getItems().clear();
				}
			};

			clear.setOnAction(reset);

			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene); // use scene for window
			primaryStage.show(); // displays window to the user
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * Starts the application
	 */
	public static void main(String[] args) {
		launch(args);

	}

}
