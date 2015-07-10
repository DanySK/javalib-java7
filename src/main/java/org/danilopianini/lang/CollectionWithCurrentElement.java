package org.danilopianini.lang;

import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * @author Danilo Pianini
 *
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
