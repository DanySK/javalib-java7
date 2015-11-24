/*******************************************************************************
 * Copyright (C) 2009, 2015, Danilo Pianini and contributors
 * listed in the project's build.gradle or pom.xml file.
 *
 * This file is distributed under the terms of the Apache License, version 2.0
 *******************************************************************************/
package org.danilopianini.lang;

import java.io.Serializable;

/**
 */
public class RangedInteger implements Serializable {

    private static final long serialVersionUID = -3744683307445983378L;
    private final int max;
    private final int min;
    private int val;

    /**
     * @param minimum
     *            the minimum
     * @param maximum
     *            the maximum
     */
    public RangedInteger(final int minimum, final int maximum) {
        min = minimum;
        max = maximum;
        val = minimum;
    }

    /**
     * @param minimum
     *            the minimum
     * @param maximum
     *            the maximum
     * @param current
     *            the current
     */
    public RangedInteger(final int minimum, final int maximum, final int current) {
        min = minimum;
        max = maximum;
        setVal(current);
    }

    /**
     * @return the maximum
     */
    public int getMax() {
        return max;
    }

    /**
     * @return the minimum
     */
    public int getMin() {
        return min;
    }

    /**
     * @return the current value
     */
    public int getVal() {
        return val;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setVal(final int value) {
        this.val = Math.min(Math.max(value, min), max);
    }
}
