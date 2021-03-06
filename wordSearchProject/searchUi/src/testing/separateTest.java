package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import application.Main;

/**
 * Class to test the separate method from the Main.java file.
 * 
 * @see <a href=
 *      "https://junit.org/junit5/docs/current/api/org.junit.jupiter.api/org/junit/jupiter/api/Test.html">Test</a>
 */
class separateTest {

	@Test
	void test() {
		String testCase = "one twoThree four Five";
		List<String> expected = new ArrayList<String>();
		expected.add("one");
		expected.add("twoThree");
		expected.add("four");
		expected.add("Five");

		Main test = new Main();
		List<String> output = test.separate(testCase);
		assertEquals(expected, output);
	}
}
