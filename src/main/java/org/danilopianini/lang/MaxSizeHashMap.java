/*******************************************************************************
 * Copyright (C) 2009, 2015, Danilo Pianini and contributors
 * listed in the project's build.gradle or pom.xml file.
 *
 * This file is distributed under the terms of the Apache License, version 2.0
 *******************************************************************************/
package org.danilopianini.lang;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This map will accept up to a maximum number of elements, then it will start
 * to remove the eldest.
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