/*******************************************************************************
 * Copyright (C) 2009, 2015, Danilo Pianini and contributors
 * listed in the project's build.gradle or pom.xml file.
 *
 * This file is distributed under the terms of the Apache License, version 2.0
 *******************************************************************************/
package org.danilopianini.concurrency;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * This class provides an id generator local to threads.
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
