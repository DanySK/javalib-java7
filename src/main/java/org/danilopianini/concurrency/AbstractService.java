/*
 * Copyright (C) 2010-2014, Danilo Pianini and contributors
 * listed in the project's pom.xml file.
 * 
 * This file is part of Alchemist, and is distributed under the terms of
 * the GNU General Public License, with a linking exception, as described
 * in the file LICENSE in the Alchemist distribution's top directory.
 */
package org.danilopianini.concurrency;

/**
 * Interface for a background service.
 * 
 * @author Danilo Pianini
 * @version 20111021
 * 
 */
public abstract class AbstractService extends Thread {

	/**
	 * 
	 */
	private boolean isAlive = true;

	/**
	 * @see java.lang.Thread#run()
	 */
	public abstract void run();

	/**
	 * If called, the service must stop.
	 */
	public abstract void stopService();

	/**
	 * @return true if the service is alive
	 */
	public final boolean isServiceAlive() {
		return isAlive;
	}

	/**
	 * @param alive
	 *            if false, the service will stop.
	 */
	public final void setAlive(final boolean alive) {
		this.isAlive = alive;
	}

}
