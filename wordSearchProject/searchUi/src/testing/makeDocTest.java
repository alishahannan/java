package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import application.Main;

class makeDocTest {

	@Test
	void test() {
		String testCase = "C:/Users/alish/Documents/1068-h.htm";
		String expected = "title author lorem ipsum is simply dummy "
				+ "text of the printing and typesetting industry";
		Main test = new Main();
		String output = test.makeDoc(testCase);
		assertEquals(expected, output);
	}

	//updated h2 to be selected by tag instead of by class
	//is heavily catered to file that is being read "class chapter"
}
