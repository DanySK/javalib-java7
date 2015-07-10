/*
 * Copyright (C) 2010-2014, Danilo Pianini and contributors
 * listed in the project's pom.xml file.
 * 
 * This file is part of Alchemist, and is distributed under the terms of
 * the GNU General Public License, with a linking exception, as described
 * in the file LICENSE in the Alchemist distribution's top directory.
 */
package org.danilopianini.concurrency;

import java.util.concurrent.Semaphore;

/**
 * This class implements a non-reentrant ReadWriteLock.
 * 
 * @author Danilo Pianini
 * 
 */
public class FastReadWriteLock {

	private final Semaphore lock = new Semaphore(Integer.MAX_VALUE);

	/**
	 * Acquire a read lock. Blocks until a read lock is available
	 */
	public void read() {
		lock.acquireUninterruptibly();
	}

	/**
	 * Acquire a write lock. Blocks until a write lock is available
	 */
	public void write() {
		lock.acquireUninterruptibly(Integer.MAX_VALUE);
	}

	/**
	 * Signals that the operation is done.
	 */
	public void release() {
		if (lock.availablePermits() == 0) {
			lock.release(Integer.MAX_VALUE);
		} else {
			lock.release();
		}
	}
}
