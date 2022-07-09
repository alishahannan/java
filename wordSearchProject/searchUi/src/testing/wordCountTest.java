package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

import application.Main;

/**
 * Class to test the wordCount method from the Main.java file.
 * 
 * @see <a href=
 *      "https://junit.org/junit5/docs/current/api/org.junit.jupiter.api/org/junit/jupiter/api/Test.html">Test</a>
 */
class wordCountTest {

	@Test
	void test() {
		List<String> testCase = new ArrayList<String>();
		testCase.add("Lorem");
		testCase.add("Ipsum");
		testCase.add("Lorem");
		testCase.add("dummy");
		testCase.add("simply");
		testCase.add("Lorem");
		testCase.add("print");

		HashMap<String, Integer> blank = new HashMap<String, Integer>();

		HashMap<String, Integer> expected = new HashMap<String, Integer>();
		expected.put("Lorem", 3);
		expected.put("Ipsum", 1);
		expected.put("dummy", 1);
		expected.put("simply", 1);
		expected.put("print", 1);

		Main test = new Main();
		HashMap<String, Integer> output = test.wordCount(testCase, blank);
	}
}
