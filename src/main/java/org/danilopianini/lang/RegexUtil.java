package org.danilopianini.lang;

import java.util.regex.Pattern;

/**
 * A set of pre-compiled {@link Pattern}s commonly used.
 * 
 * @author Danilo Pianini
 *
 */
public final class RegexUtil {
	
	/**
	 * If compiled, this {@link String} pattern matches any float or double BUT
	 * {@link Double#NaN}, {@link Double#POSITIVE_INFINITY} and
	 * {@value Double#NEGATIVE_INFINITY}.
	 */
	public static final String FLOAT = "[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?";

	/**
	 * This {@link Pattern} matches any float or double BUT {@link Double#NaN},
	 * {@link Double#POSITIVE_INFINITY} and {@value Double#NEGATIVE_INFINITY}.
	 */
	public static final Pattern FLOAT_PATTERN = Pattern.compile(FLOAT);

	private RegexUtil() {
	}

}
