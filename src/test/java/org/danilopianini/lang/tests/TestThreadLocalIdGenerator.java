package org.danilopianini.lang.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

import org.danilopianini.concurrency.ThreadLocalIdGenerator;
import org.junit.Test;

/**
 * @author Danilo Pianini
 *
 */
public class TestThreadLocalIdGenerator {

	private static final int TESTS = 100000;
	private static final int THREADS = 100;
	
	/**
	 * 
	 */
	@Test
	public void test() {
		final ThreadLocalIdGenerator idgen = new ThreadLocalIdGenerator();
		final Semaphore starter = new Semaphore(0);
		final CountDownLatch cdl = new CountDownLatch(THREADS);
		final Runnable run = new Runnable() {
			@Override
			public void run() {
				starter.acquireUninterruptibly();
				for (int i = 0; i < TESTS; i++) {
					idgen.genId();
				}
			 	assertEquals(ThreadLocalIdGenerator.class + " generates broken IDs!", TESTS, idgen.genId());
			 	cdl.countDown();
			}	
		};
		for (int i = 0; i < THREADS; i++) {
			new Thread(run).start();
		}
		starter.release(THREADS);
		try {
			cdl.await();
		} catch (InterruptedException e) {
			fail();
		}
	}

}
