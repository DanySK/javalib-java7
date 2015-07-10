/*
 * Copyright (C) 2010-2014, Danilo Pianini and contributors
 * listed in the project's pom.xml file.
 * 
 * This file is part of Alchemist, and is distributed under the terms of
 * the GNU General Public License, with a linking exception, as described
 * in the file LICENSE in the Alchemist distribution's top directory.
 */
package org.danilopianini.lang;

/**
 * @author Danilo Pianini
 * 
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
