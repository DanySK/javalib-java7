/*******************************************************************************
 * Copyright (C) 2009, 2015, Danilo Pianini and contributors
 * listed in the project's build.gradle or pom.xml file.
 *
 * This file is distributed under the terms of the Apache License, version 2.0
 *******************************************************************************/
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