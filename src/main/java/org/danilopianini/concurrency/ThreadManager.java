/*
 * Copyright (C) 2010-2014, Danilo Pianini and contributors
 * listed in the project's pom.xml file.
 * 
 * This file is part of Alchemist, and is distributed under the terms of
 * the GNU General Public License, with a linking exception, as described
 * in the file LICENSE in the Alchemist distribution's top directory.
 */
package org.danilopianini.concurrency;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * A class to easy manage multithreaded applications. It contains two
 * schedulers, one of them is meant to execute those Threads which run a task
 * that will eventually return. Execution of these tasks is demanded to a
 * FixedThreadPool whose dimension is the number of logic cores of the system.
 * The other is meant to keep alive a bunch of services, and use a Chached
 * thread pool.
 * 
 * @author Danilo Pianini
 * @version 20111021
 * 
 */
public class ThreadManager {

	/**
	 * The cached executor for services.
	 */
	private final ExecutorService cached = Executors.newCachedThreadPool();
	/**
	 * The fixed executor for tasks.
	 */
	private final ExecutorService fixed;

	/**
	 * Dfault constructor. Tries to detect the number of threads to use for
	 * tasks automatically (counting the number of logic cores).
	 */
	public ThreadManager() {
		fixed = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	}

	/**
	 * @param cores
	 *            the number of threads for the tasks executor.
	 */
	public ThreadManager(final int cores) {
		fixed = Executors.newFixedThreadPool(cores);
	}

	/**
	 * @param thread
	 *            The service to add to the CachedThreadPool
	 * @return a Future representing pending completion of the task
	 */
	public Future<?> addService(final AbstractService thread) {
		return cached.submit(thread);
	}

	/**
	 * Shuts down all the services and tasks added. It waits for a maximum of
	 * timeout milliseconds before returning.
	 * 
	 * @param timeout
	 *            maximum time for this operation, in milliseconds.
	 */
	public void closeAndWait(final long timeout) {
		final List<Runnable> services = cached.shutdownNow();
		for (final Runnable service : services) {
			((AbstractService) service).stopService();
		}
		fixed.shutdown();
		try {
			long elapsed = System.currentTimeMillis();
			fixed.awaitTermination(timeout, TimeUnit.MILLISECONDS);
			elapsed -= System.currentTimeMillis();
			cached.awaitTermination(timeout + elapsed, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace(); // NOPMD by danysk on 12/4/13 4:43 PM
		}
	}

	/**
	 * @param thread
	 *            The Callable to schedule
	 * @param <K>
	 *            parameter type of the Future
	 * @return a Future representing pending completion of the task
	 */
	public <K> Future<K> execute(final Callable<K> thread) {
		return fixed.submit(thread);
	}

	/**
	 * This method provides a facility to run methods in separate threads.
	 * 
	 * @param obj
	 *            the object within which invoke the method
	 * @param method
	 *            the method to invoke
	 * @param args
	 *            arguments for the method
	 * @throws NoSuchMethodException
	 *             if the method does not exist
	 */
	public void execute(final Object obj, final String method, final Object[] args) throws NoSuchMethodException {
		Class<?>[] classes = new Class<?>[args.length];
		for (int i = 0; i < args.length; i++) {
			classes[i] = args[i].getClass();
		}
		final Method m = obj.getClass().getMethod(method, classes);
		fixed.submit(new Runnable() {
			public void run() {
				try {
					m.invoke(obj, args);
				} catch (InvocationTargetException | IllegalAccessException | IllegalArgumentException e) {
					e.printStackTrace(); // NOPMD by danysk on 12/4/13 4:43 PM
				}
			}
		});
	}

	/**
	 * This method will schedule a task for the execution in the
	 * FixedThreadPool.
	 * 
	 * @param thread
	 *            The thread to schedule
	 * @return a Future representing pending completion of the task
	 */
	public Future<?> execute(final Runnable thread) {
		return fixed.submit(thread);
	}

}
