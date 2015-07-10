/*
 * Copyright (C) 2010-2014, Danilo Pianini and contributors
 * listed in the project's pom.xml file.
 * 
 * This file is part of Alchemist, and is distributed under the terms of
 * the GNU General Public License, with a linking exception, as described
 * in the file LICENSE in the Alchemist distribution's top directory.
 */
package org.danilopianini.concurrency;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * This class provides an id generator local to threads.
 * 
 * @author Danilo Pianini
 * 
 */
public final class ThreadLocalIdGenerator implements Serializable {

	private static final long serialVersionUID = 4130571136157694474L;
	private static final Singleton SINGLETON = new Singleton();

	private static final class Singleton extends ThreadLocal<AtomicInteger> implements Serializable {
		private static final long serialVersionUID = -4638200973190990225L;

		@Override
		protected AtomicInteger initialValue() {
			return new AtomicInteger(0);
		}
	}

	/**
	 * @return a freshly generated id. Thread-local.
	 */
	public int genId() {
		return SINGLETON.get().getAndIncrement();
	}

}
