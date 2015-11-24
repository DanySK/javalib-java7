/*******************************************************************************
 * Copyright (C) 2009, 2015, Danilo Pianini and contributors
 * listed in the project's build.gradle or pom.xml file.
 *
 * This file is distributed under the terms of the Apache License, version 2.0
 *******************************************************************************/
package org.danilopianini.lang;

import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * @param <E>
 */
public interface CollectionWithCurrentElement<E> extends Collection<E> {

    /**
     * @return the current active element
     */
    E getCurrent();

    /**
     * @param e the new active element
     * 
     * @throws NoSuchElementException if the Collection does not contain the new active element
     */
    void setCurrent(E e);

}
