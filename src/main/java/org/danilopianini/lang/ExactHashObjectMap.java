/*
 * Copyright (C) 2010-2014, Danilo Pianini and contributors
 * listed in the project's pom.xml file.
 * 
 * This file is part of Alchemist, and is distributed under the terms of
 * the GNU General Public License, with a linking exception, as described
 * in the file LICENSE in the Alchemist distribution's top directory.
 */
package org.danilopianini.lang;

import gnu.trove.impl.hash.THash;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

/**
 * A map which stores objects using hash codes for indexing. Note: if two
 * different objects have the same hashCode, this map WILL NOT WORK PROPERLY.
 * You must be sure that for the stored object the generated hash codes are
 * univocal.
 * 
 * @author Danilo Pianini
 * 
 * @param <V>
 */
public class ExactHashObjectMap<V> extends TIntObjectHashMap<V> {

	/**
	 * Builds a new, emtpy map.
	 */
	public ExactHashObjectMap() {
		super();
	}

	/**
	 * Builds a new map containing all the elements of the passed map.
	 * 
	 * @param map
	 *            the map to copy
	 */
	public ExactHashObjectMap(final TIntObjectMap<? extends V> map) {
		super(map);
	}

	/**
	 * Builds a new map, which will not resize until the number of stored
	 * objects will be less or equal to expectedSize.
	 * 
	 * @param expectedSize
	 *            the expected size
	 */
	public ExactHashObjectMap(final int expectedSize) {
		/*
		 * If the initial capacity is greater than the maximum number of entries
		 * divided by the load factor, no rehash operations will ever occur.
		 */
		super((int) (expectedSize / THash.DEFAULT_LOAD_FACTOR) + 1);
	}

	/**
	 * Returns <tt>true</tt> if this map contains a mapping for the specified
	 * key. More formally, returns <tt>true</tt> if and only if this map
	 * contains a mapping for a key <tt>k</tt> such that <tt>key.equals(k)</tt>.
	 * (There can be at most one such mapping.)
	 * 
	 * @param key
	 *            key whose presence in this map is to be tested
	 * @return <tt>true</tt> if this map contains a mapping for the specified
	 *         key
	 */
	public boolean containsKey(final Object key) {
		return super.containsKey(key.hashCode());
	}

	/**
	 * Returns the value to which the specified key is mapped, or {@code null}
	 * if this map contains no mapping for the key.
	 * 
	 * <p>
	 * More formally, if this map contains a mapping from a key {@code k} to a
	 * value {@code v} such that {@code (key==null ? k==null :
	 * key.equals(k))}, then this method returns {@code v}; otherwise it returns
	 * {@code null}. (There can be at most one such mapping.)
	 * 
	 * <p>
	 * If this map permits null values, then a return value of {@code null} does
	 * not <i>necessarily</i> indicate that the map contains no mapping for the
	 * key; it's also possible that the map explicitly maps the key to
	 * {@code null}. The {@link #containsKey containsKey} operation may be used
	 * to distinguish these two cases.
	 * 
	 * @param key
	 *            the key whose associated value is to be returned
	 * @return the <tt>int</tt> value to which the specified key is mapped, or
	 *         {@code null} if this map contains no mapping for the key
	 */
	public V get(final Object key) {
		return super.get(key.hashCode());
	}

	/**
	 * Associates the specified value with the specified key in this map
	 * (optional operation). If the map previously contained a mapping for the
	 * key, the old value is replaced by the specified value. (A map <tt>m</tt>
	 * is said to contain a mapping for a key <tt>k</tt> if and only if
	 * {@link #containsKey(int) m.containsKey(k)} would return <tt>true</tt>.)
	 * 
	 * @param key
	 *            key with which the specified value is to be associated
	 * @param value
	 *            an <tt>int</tt> value value to be associated with the
	 *            specified key
	 * @return the previous value associated with <tt>key</tt>, or
	 *         <tt>no_entry_value</tt> if there was no mapping for <tt>key</tt>.
	 *         (A <tt>no_entry_value</tt> return can also indicate that the map
	 *         previously associated <tt>null</tt> with <tt>key</tt>, if the
	 *         implementation supports <tt>null</tt> values.)
	 * @see #getNoEntryKey()
	 */
	public V put(final Object key, final V value) {
		return super.put(key.hashCode(), value);
	}

	/**
	 * Removes the mapping for a key from this map if it is present (optional
	 * operation). More formally, if this map contains a mapping from key
	 * <tt>k</tt> to value <tt>v</tt> such that <code>key.equals(k)</code>, that
	 * mapping is removed. (The map can contain at most one such mapping.)
	 * 
	 * <p>
	 * Returns the value to which this map previously associated the key, or
	 * <tt>null</tt> if the map contained no mapping for the key.
	 * 
	 * <p>
	 * If this map permits null values, then a return value of <tt>null</tt>
	 * does not <i>necessarily</i> indicate that the map contained no mapping
	 * for the key; it's also possible that the map explicitly mapped the key to
	 * <tt>null</tt>.
	 * 
	 * <p>
	 * The map will not contain a mapping for the specified key once the call
	 * returns.
	 * 
	 * @param key
	 *            key whose mapping is to be removed from the map
	 * @return the previous <tt>int</tt> value associated with <tt>key</tt>, or
	 *         <tt>null</tt> if there was no mapping for <tt>key</tt>.
	 */
	public V remove(final Object key) {
		return super.remove(key.hashCode());
	}

}
