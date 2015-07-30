/*******************************************************************************
 * Copyright (C) 2009, 2015, Danilo Pianini and contributors
 * listed in the project's build.gradle or pom.xml file.
 *
 * This file is distributed under the terms of the Apache License, version 2.0
 *******************************************************************************/
package org.danilopianini.lang.tests;

import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.danilopianini.lang.util.FasterString;
import org.junit.Test;

/**
 * @author Danilo Pianini
 *
 */
public class TestFasterString {

	private static final byte MAX_LEN = 3;
	private static final char MIN_CHAR = ' ';
	private static final char MAX_CHAR = '~';
	
	/**
	 * Ensures no collisions for strings three characters long.
	 */
	@Test
	public void testEqualsFasterString() {
		final Map<FasterString, FasterString> set = new HashMap<>();
		for (byte len = 1; len <= MAX_LEN; len++) {
			final char[] cur = new char[len];
			Arrays.fill(cur, MIN_CHAR);
			int pos = 0;
			while (pos < len) {
				final FasterString fs = new FasterString(new String(cur));
				final FasterString existing = set.get(fs);
				if (existing != null) {
					fail(fs + " collides with " + existing);
				} else {
					set.put(fs, fs);
				}
				cur[0]++;
				if (pos < len && cur[pos] > MAX_CHAR) {
					while (pos < len && cur[pos] >= MAX_CHAR) {
						cur[pos++] = MIN_CHAR;
					}
					if (pos < len) {
						cur[pos]++;
						pos = 0;
					}
				}
			}
		}
	}

}
