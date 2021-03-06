/*******************************************************************************
 * Copyright (C) 2009, 2015, Danilo Pianini and contributors
 * listed in the project's build.gradle or pom.xml file.
 *
 * This file is distributed under the terms of the Apache License, version 2.0
 *******************************************************************************/
package org.danilopianini.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.danilopianini.concurrency.ThreadManager;

/**
 * This class extends the FileSystemWatcher, implementing a multithreaded
 * recursive watcher. All the folders created inside the watched folder will be
 * watched.
 * 
 */
public abstract class AbstractRecursiveFileSystemWatcher extends AbstractFileSystemWatcher {

    private static final String SLASH = System.getProperty("file.separator");

    private final Map<Path, AbstractRecursiveFileSystemWatcher> map = new ConcurrentHashMap<>();
    private final ThreadManager tm;

    /**
     * Builds a new RecursiveFileSystemWatcher.
     * 
     * @param dir
     *            the directory to watch
     * @param threadManager
     *            the ThreadManager where to schedule the sub-watchers
     * @throws IOException
     *             in case of I/O Error
     */
    public AbstractRecursiveFileSystemWatcher(final String dir, final ThreadManager threadManager) throws IOException {
        super(dir);
        this.tm = threadManager;
        final File[] files = getPath().toFile().listFiles();
        if (files != null) {
            for (final File f : files) {
                if (f.isDirectory()) {
                    final Path p = FileSystems.getDefault().getPath(f.toString());
                    final AbstractRecursiveFileSystemWatcher w = build(f);
                    threadManager.addService(w);
                    map.put(p, w);
                }
            }
        }
    }

    /**
     * This method must be implemented by subclasses, and should basically provide a
     * constructor. If your subclass is "Subclass", a possible implementation is
     * 
     * return new Subclass();
     * 
     * @param file the file to watch
     * @return a freshly build RecursiveFileSystemWatcher
     */
    public abstract AbstractRecursiveFileSystemWatcher build(File file);

    @Override
    protected final void created(final Path file) {
        final File realfile = new File(getPath() + SLASH + file.toString());
        if (realfile.isDirectory()) {
            final AbstractRecursiveFileSystemWatcher w = build(realfile);
            tm.addService(w);
            map.put(file, w);
        }
        createdFile(file);
    }

    /**
     * This method substitutes the created(Path).
     * 
     * @param p the new file created
     */
    protected abstract void createdFile(final Path p);

    @Override
    protected void deleted(final Path file) {
        if (map.containsKey(file)) {
            map.remove(file).stopService();
        }
        deletedFile(file);
    }

    /**
     * This method substitutes the deleted(Path).
     * 
     * @param p the file which has been deleted
     */
    protected abstract void deletedFile(final Path p);

    /**
     * This method substitutes the stopService().
     */
    protected abstract void finalizeStop();

    /**
     * @return the ThreadManager
     */
    public ThreadManager getThreadManager() {
        return tm;
    }

    @Override
    public final void stopService() {
        super.stopService();
        for (final Entry<Path, AbstractRecursiveFileSystemWatcher> p : map.entrySet()) {
            p.getValue().stopService();
        }
        finalizeStop();
    }

}
