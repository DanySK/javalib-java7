/*
 * Copyright (C) 2010-2014, Danilo Pianini and contributors
 * listed in the project's pom.xml file.
 * 
 * This file is part of Alchemist, and is distributed under the terms of
 * the GNU General Public License, with a linking exception, as described
 * in the file LICENSE in the Alchemist distribution's top directory.
 */
package org.danilopianini.io;

import java.io.File;

/**
 * This abstract class represents a Runnable able to recursively scan the
 * filesystem. For each File and Directory found, an abstract method is called.
 * Implement it to give the FileScanner the behavior you want.
 * 
 * @author Danilo Pianini
 * @version 20111021
 * 
 */
public abstract class AbstractFileScanner implements Runnable {

	private final File file;
	private final boolean followH;

	/**
	 * @param f
	 *            the file or directory to start with
	 */
	public AbstractFileScanner(final File f) {
		this(f, true);
	}

	/**
	 * @param f
	 *            the file or directory to start with
	 * @param followHidden
	 *            true if the scanner should walk through hidden folders
	 */
	public AbstractFileScanner(final File f, final boolean followHidden) {
		this.file = f;
		this.followH = followHidden;
	}

	/**
	 * This method is called as soon as the scanning ends.
	 */
	protected abstract void finish();

	/**
	 * This method is called each time a directory is found.
	 * 
	 * @param directory the directory which has been found
	 */
	protected abstract void foundDirectory(File directory);

	/**
	 * This method is called each time a new file is found.
	 * 
	 * @param f the file which has been found
	 */
	protected abstract void foundFile(File f);

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		setup();
		scan(file);
		finish();
	}

	/**
	 * This recursive method scans the filesystem.
	 * 
	 * @param f
	 *            The directory or file to start with
	 */
	protected void scan(final File f) {
		if (f.isDirectory() && (followH || !f.isHidden())) {
			foundDirectory(f);
			final File[] list = f.listFiles();
			for (final File s : list) {
				scan(s);
			}
			exitDirectory(f);
		} else {
			foundFile(f);
		}
	}

	/**
	 * Called every time the scanner exits a directory.
	 * 
	 * @param directory the directory which has been scanned
	 */
	protected abstract void exitDirectory(File directory);

	/**
	 * This method is called before starting the scanning.
	 */
	protected abstract void setup();

}
