/*******************************************************************************
 * Copyright (C) 2009, 2015, Danilo Pianini and contributors
 * listed in the project's build.gradle or pom.xml file.
 *
 * This file is distributed under the terms of the Apache License, version 2.0
 *******************************************************************************/
package org.danilopianini.lang;

/**
 */
public final class Constants {

    /**
     * DJB2 constants.
     */
    public static final byte DJB2_MAGIC = 33, DJB2_SHIFT = 5, BIT_PER_BYTE = 8;

    /**
     * DJB2 start value.
     */
    public static final int DJB2_START = 5381;

    /**
     * Number of bytes composing a double.
     */
    public static final byte DOUBLE_SIZE = 8;

    private Constants() {
    }

}
