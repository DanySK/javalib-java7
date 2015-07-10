/*
 * Copyright (C) 2010-2014, Danilo Pianini and contributors
 * listed in the project's pom.xml file.
 * 
 * This file is part of Alchemist, and is distributed under the terms of
 * the GNU General Public License, with a linking exception, as described
 * in the file LICENSE in the Alchemist distribution's top directory.
 */
package org.danilopianini.lang;

import gnu.trove.map.hash.TObjectIntHashMap;

/**
 * A {@link TObjectIntHashMap} whose no_entry_value field is Integer.MIN_VALUE.
 * 
 * @author Danilo Pianini
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
