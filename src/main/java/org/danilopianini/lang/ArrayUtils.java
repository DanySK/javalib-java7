/*
 * Copyright (C) 2010-2014, Danilo Pianini and contributors
 * listed in the project's pom.xml file.
 * 
 * This file is part of Alchemist, and is distributed under the terms of
 * the GNU General Public License, with a linking exception, as described
 * in the file LICENSE in the Alchemist distribution's top directory.
 */
package org.danilopianini.lang;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.danilopianini.lang.Constants.DOUBLE_SIZE;

/**
 * A class containing stati methods to manipulate arrays efficiently.
 * 
 * @author Danilo Pianini
 * @version 20110129
 * 
 */
public final class ArrayUtils {

	/**
	 * 
	 */
	private ArrayUtils() {
	}

	/**
	 * This function allows to clone an existing list and returns the new List.
	 * Current implementation returns an ArrayList.
	 * 
	 * @param toClone
	 *            the list to clone
	 * @param <T>
	 *            the generic type of the list to be cloned (same as the
	 *            resulting)
	 * @return the cloned list
	 */
	public static <T> List<T> cloneList(final List<T> toClone) {
		final List<T> res = new ArrayList<T>(toClone.size());
		for (final T element : toClone) {
			res.add(element);
		}
		return res;
	}

	/**
	 * Given two lists a and b, this function returns a new list containing all
	 * the elements of a not present in b and all the elements of b not present
	 * in a. Lists a and b will be not modified by this call.
	 * 
	 * @param a
	 *            the first list
	 * @param b
	 *            the second list
	 * @param <T>
	 *            the generic type of the lists
	 * @return the list of the differences
	 */
	public static <T> List<T> differences(final List<T> a, final List<T> b) { 
		final List<T> shorter = a.size() > b.size() ? b : a;
		final List<T> longest = a.size() > b.size() ? a : b;
		/*
		 * This clone is used both to keep the shorter list unmodified and to
		 * allow usage of iterators without any concurrent modification.
		 */
		final List<T> clone = cloneList(shorter); 
		final List<T> res = new ArrayList<T>(shorter.size());
		for (final T o : shorter) {
			/*
			 * If there are elements in common between a and b, remove them from
			 * the clone. Else, add them to the result.
			 */
			if (b.contains(o)) {
				clone.remove(o);
			} else {
				res.add(o);
			}
		}
		for (final T o : longest) {
			/*
			 * If there are elments left which are not yet in the clone, they
			 * must be added to it.
			 */
			if (!clone.contains(o)) {
				res.add(o);
			}
		}
		return res;
	}

	/**
	 * Allows to print arrays in a better fashion than a pointer.
	 * 
	 * @param a
	 *            the array to print
	 * @param separator
	 *            the symbols to use to separate array elements
	 * @return a String representing the array content
	 */
	public static String arrayToString(final Object[] a, final String separator) { 
		/*
		 * String buffers are much faster than normal strings when you need to
		 * append things.
		 */
		final StringBuilder result = new StringBuilder("[");
		if (a.length > 0) {
			/*
			 * This is to avoid the separator to be printed before the first
			 * element
			 */
			result.append(a[0]);
			for (int i = 1; i < a.length; i++) {
				result.append(separator);
				result.append(a[i]);
			}
		}
		result.append(']');
		return result.toString();
	}

	/**
	 * This method checks if an array contains an element. It uses the equals()
	 * method of the passed Object.
	 * 
	 * @param list
	 *            the array to check
	 * @param element
	 *            the element to compare the array elements to
	 * @param <T>
	 *            the generic type of the array
	 * @return true if the array contains the element
	 */
	public static <T> boolean contains(final T[] list, final T element) {
		for (final T el : list) {
			if (element.equals(el)) {
				return true; 
			}
		}
		return false;
	}
	
	/**
	 * Converts a double into a byte array.
	 * 
	 * @param value
	 *            the double to convert
	 * @return a byte array representation of the double value
	 */
	public static byte[] toByteArray(final double value) {
		final byte[] bytes = new byte[DOUBLE_SIZE];
		ByteBuffer.wrap(bytes).putDouble(value);
		return bytes;
	}

}
