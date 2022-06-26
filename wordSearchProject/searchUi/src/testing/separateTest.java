package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import application.Main;

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
		assertEquals(expected,output);
		
	}

}
