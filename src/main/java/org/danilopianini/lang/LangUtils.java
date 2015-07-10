/*
 * Copyright (C) 2010-2014, Danilo Pianini and contributors
 * listed in the project's pom.xml file.
 * 
 * This file is part of Alchemist, and is distributed under the terms of
 * the GNU General Public License, with a linking exception, as described
 * in the file LICENSE in the Alchemist distribution's top directory.
 */
package org.danilopianini.lang;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Objects;

/**
 * @author Danilo Pianini
 *
 */
public final class LangUtils {

	private LangUtils() {
	}
	
	/**
	 * Calls {@link Objects#requireNonNull(Object)} for each passed object.
	 * 
	 * @param objs the objects
	 */
	public static void requireNonNull(final Object... objs) {
		for (final Object o: objs) {
			Objects.requireNonNull(o);
		}
	}
	
	/**
	 * Converts a {@link Throwable}'s stacktrace to a Java {@link String}.
	 * @param e the {@link Throwable}
	 * @return its stacktrace in {@link String} format
	 */
	public static String stackTraceToString(final Throwable e) {
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		e.printStackTrace(printWriter);
		return result.toString();
	}

}
