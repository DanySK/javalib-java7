/*
 * Copyright (C) 2010-2014, Danilo Pianini and contributors
 * listed in the project's pom.xml file.
 * 
 * This file is part of Alchemist, and is distributed under the terms of
 * the GNU General Public License, with a linking exception, as described
 * in the file LICENSE in the Alchemist distribution's top directory.
 */
package org.danilopianini.io;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import org.danilopianini.concurrency.AbstractService;

/**
 * The aim of this class is to provide a skeleton for a file system watcher.
 * Some abstract methods are exposed in order to ease the subclassing
 * 
 * @author Danilo Pianini
 * 
 */
public abstract class AbstractFileSystemWatcher extends AbstractService {

	private final Path path;
	private final WatchService watcher;

	/**
	 * Builds a file system watcher for a single directory.
	 * 
	 * @param dir
	 *            the directory to register to
	 * @throws IOException
	 *             in case of I/O errors
	 */
	public AbstractFileSystemWatcher(final String dir) throws IOException {
		super();
		watcher = FileSystems.getDefault().newWatchService();
		path = FileSystems.getDefault().getPath(dir);
		path.register(watcher, StandardWatchEventKinds.ENTRY_CREATE,
				StandardWatchEventKinds.ENTRY_MODIFY,
				StandardWatchEventKinds.ENTRY_DELETE);
	}

	/**
	 * This method is invoked whenever a new file is created.
	 * 
	 * @param filename
	 *            the file which has been created
	 */
	protected abstract void created(Path filename);

	/**
	 * This method is invoked whenever a file is deleted.
	 * 
	 * @param filename
	 *            the file which has been deleted
	 */
	protected abstract void deleted(Path filename);

	/**
	 * @return the currently observed path
	 */
	public Path getPath() {
		return path;
	}

	/**
	 * This method is invoked whenever a file is modified.
	 * 
	 * @param filename
	 *            the file which has been modified
	 */
	protected abstract void modified(Path filename);

	/**
	 * This method is invoked whenever an overflow event occurs (see
	 * java.nio.file.StandardWatchEventKinds.OVERFLOW).
	 */
	public abstract void overflow();

	@Override
	public void run() {
		while (isAlive()) {
			try {
				final WatchKey k = watcher.take();
				for (final WatchEvent<?> ev : k.pollEvents()) {
					final Kind<?> kind = ev.kind();
					if (kind == StandardWatchEventKinds.OVERFLOW) {
						overflow();
					}
					@SuppressWarnings("unchecked")
					final WatchEvent<Path> event = (WatchEvent<Path>) ev;
					final Path filename = event.context();
					if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
						created(filename);
					} else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
						deleted(filename);
					} else if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
						modified(filename);
					} else {
						unknown(event);
					}
				}
				if (!k.reset()) {
					stopService();
				}
			} catch (InterruptedException e) {
				e.printStackTrace(); // NOPMD by danysk on 12/4/13 4:42 PM
			}
		}
	}

	@Override
	public void stopService() {
		setAlive(false);
		this.interrupt();
	}

	/**
	 * Something nasty happened, and the system received an unexpected event.
	 * 
	 * @param event
	 *            the bad event
	 */
	protected abstract void unknown(WatchEvent<Path> event);

}
