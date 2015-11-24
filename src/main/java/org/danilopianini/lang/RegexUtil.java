/*******************************************************************************
 * Copyright (C) 2009, 2015, Danilo Pianini and contributors
 * listed in the project's build.gradle or pom.xml file.
 *
 * This file is distributed under the terms of the Apache License, version 2.0
 *******************************************************************************/
package org.danilopianini.lang;

import java.util.regex.Pattern;

/**
 * A set of pre-compiled {@link Pattern}s commonly used.
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
