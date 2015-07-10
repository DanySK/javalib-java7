/*
 * Copyright (C) 2010-2014, Danilo Pianini and contributors
 * listed in the project's pom.xml file.
 * 
 * This file is part of Alchemist, and is distributed under the terms of
 * the GNU General Public License, with a linking exception, as described
 * in the file LICENSE in the Alchemist distribution's top directory.
 */
package org.danilopianini.lang;

import java.io.Serializable;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

/**
 * This class provides a cache for a generic enum, mapping each value to an int.
 * 
 * @author Danilo Pianini
 *
 * @param <V>
 */
public class ReverseEnumMap<V extends Enum<V>> implements Serializable {
	private static final long serialVersionUID = 660169408203724941L;
	private final TIntObjectMap<V> map = new TIntObjectHashMap<>();
    
    /**
     * @param valueType the class to build the cache upon
     */
    public ReverseEnumMap(final Class<V> valueType) {
        for (final V v : valueType.getEnumConstants()) {
            map.put(v.ordinal(), v);
        }
    }

    /**
     * Converts an integer to the corresponding enum value.
     * 
     * @param num the index of the enum
     * @return the enum value
     */
    public V get(final int num) {
        return map.get(num);
    }
}