package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.jupiter.api.Test;

import application.Main;

class sortCountTest {

	@Test
	void test() {
		HashMap<String, Integer> testCase = new HashMap<String, Integer>();
		testCase.put("cat", 8);
		testCase.put("dog", 11);
		testCase.put("bird", 2);
		testCase.put("snake", 21);
		testCase.put("horse", 13);
		testCase.put("chicken", 18);
		testCase.put("pig", 26);
		testCase.put("tiger", 5);

		Main test = new Main();

		List<Entry<String, Integer>> expected = new LinkedList<Map.Entry<String, Integer>>();

		expected.add((Map.entry("pig", 26)));
		expected.add((Map.entry("snake", 21)));
		expected.add((Map.entry("chicken", 18)));
		expected.add((Map.entry("horse", 13)));
		expected.add((Map.entry("dog", 11)));
		expected.add((Map.entry("cat", 8)));
		expected.add((Map.entry("tiger", 5)));
		expected.add((Map.entry("bird", 2)));
		List<Entry<String, Integer>> output = test.sortCount(testCase);

		assertEquals(expected, output);

	}

}
