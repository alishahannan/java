package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import application.Main;

class removePunctTest {

	@Test
	void test() {
		String testCase = "melancholy burden bore "
				+ "Of ‘Never—nevermore.’”";
		
		Main test = new Main();
		String output = test.removePunct(testCase);
		assertEquals("melancholy burden bore Of Never nevermore", output);
		
		//adjusted code to only have one space between words 
		
	}

}
