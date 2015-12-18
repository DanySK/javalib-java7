/*******************************************************************************
 * Copyright (C) 2009, 2015, Danilo Pianini and contributors
 * listed in the project's build.gradle or pom.xml file.
 *
 * This file is distributed under the terms of the Apache License, version 2.0
 *******************************************************************************/
package org.danilopianini.lang;

import gnu.trove.map.hash.TObjectIntHashMap;

/**
 * A {@link TObjectIntHashMap} whose no_entry_value field is Integer.MIN_VALUE.
 * 
 * @param <K>
 */
public class ObjectIntHashMap<K> extends TObjectIntHashMap<K> {

    /**
     * 
     */
    public ObjectIntHashMap() {
        super();
        no_entry_value = Integer.MIN_VALUE;
    }

}
