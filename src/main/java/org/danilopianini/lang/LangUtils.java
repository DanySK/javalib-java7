/*******************************************************************************
 * Copyright (C) 2009, 2015, Danilo Pianini and contributors
 * listed in the project's build.gradle or pom.xml file.
 *
 * This file is distributed under the terms of the Apache License, version 2.0
 *******************************************************************************/
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
