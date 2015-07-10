package org.danilopianini.lang.tests;

import static org.danilopianini.lang.RegexUtil.FLOAT_PATTERN;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Danilo Pianini
 *
 */
public class TestRegex {
	
	/**
	 * 
	 */
	@Test
	public void testFloatMatcher() {
		final ConcurrentMap<String, Boolean> tests = new ConcurrentHashMap<>();
		tests.put("1.0000000000000002", true);
		tests.put("-1.0000000000000002", true);
		tests.put("0", true);
		tests.put("NaN", false);
		tests.put("Infinity", false);
		tests.put("-Infinity", false);
		tests.put("-1.256e-9", true);
		tests.put("+1.256e+9", true);
		for (Entry<String, Boolean> e : tests.entrySet()) {
			if (e.getValue()) {
				Assert.assertTrue(matchFloat(e.getKey()));
			} else {
				Assert.assertFalse(matchFloat(e.getKey()));
			}
		}
	}

	private static boolean matchFloat(final String input) {
		return FLOAT_PATTERN.matcher(input).matches();
	}
	
	
}
