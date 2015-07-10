/*
 * Copyright (C) 2010-2014, Danilo Pianini and contributors
 * listed in the project's pom.xml file.
 * 
 * This file is part of Alchemist, and is distributed under the terms of
 * the GNU General Public License, with a linking exception, as described
 * in the file LICENSE in the Alchemist distribution's top directory.
 */
package org.danilopianini.lang;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This map will accept up to a maximum number of elements, then it will start
 * to remove the eldest.
 * 
 * @author Danilo Pianini
 * 
 * @param <K>
 * @param <V>
 */
public class MaxSizeHashMap<K, V> extends LinkedHashMap<K, V> {
	private static final long serialVersionUID = -3498451801390527996L;
	private final int maxSize;

	/**
	 * @param size maximum size for the map
	 */
	public MaxSizeHashMap(final int size) {
		super();
		this.maxSize = size;
	}

	@Override
	protected boolean removeEldestEntry(final Map.Entry<K, V> eldest) {
		return size() > maxSize;
	}
}