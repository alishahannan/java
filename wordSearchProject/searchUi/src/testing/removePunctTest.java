package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import application.Main;

/**
 * Class to test the removePunct method from the Main.java file.
 * <p>
 * Test resulted in adjustment to code for only one space between each word.
 * 
 * @see <a href=
 *      "https://junit.org/junit5/docs/current/api/org.junit.jupiter.api/org/junit/jupiter/api/Test.html">Test</a>
 */
class removePunctTest {

	@Test
	void test() {
		String testCase = "melancholy burden bore " + "Of ‘Never—nevermore.’”";

		Main test = new Main();
		String output = test.removePunct(testCase);
		assertEquals("melancholy burden bore Of Never nevermore", output);
	}
}
