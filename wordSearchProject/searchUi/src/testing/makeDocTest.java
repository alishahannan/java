package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import application.Main;

/**
 * Class to test the makeDoc method in the Main.java file.
 * <p>
 * Test resulted in Element h2 to be selected by tag instead of class. Method
 * can only be used by specific files that use a class listed as "chapter"
 * within the file.
 * 
 * @see <a href=
 *      "https://junit.org/junit5/docs/current/api/org.junit.jupiter.api/org/junit/jupiter/api/Test.html">Test</a>
 */
class makeDocTest {

	@Test
	void test() {
		String testCase = "C:/Users/alish/Documents/1068-h.htm";
		String expected = "title author lorem ipsum is simply dummy " + "text of the printing and typesetting industry";
		Main test = new Main();
		String output = test.makeDoc(testCase);
		assertEquals(expected, output);
	}
}
