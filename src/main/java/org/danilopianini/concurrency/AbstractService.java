/*******************************************************************************
 * Copyright (C) 2009, 2015, Danilo Pianini and contributors
 * listed in the project's build.gradle or pom.xml file.
 *
 * This file is distributed under the terms of the Apache License, version 2.0
 *******************************************************************************/
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
