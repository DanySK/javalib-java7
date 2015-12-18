/*******************************************************************************
 * Copyright (C) 2009, 2015, Danilo Pianini and contributors
 * listed in the project's build.gradle or pom.xml file.
 *
 * This file is distributed under the terms of the Apache License, version 2.0
 *******************************************************************************/
package org.danilopianini.lang.util;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * This class wraps java.lang.String and provides faster equals(). Used
 * internally to ensure better performances. The faster comparison is realized
 * by computing a hash internally (currently using Murmur 3), so be aware that
 * collisions may happen.
 * 
 */
public class FasterString implements Cloneable, Serializable, Comparable<FasterString>, CharSequence {

    private static final long serialVersionUID = -3490623928660729120L;
    private static final Charset CHARSET = StandardCharsets.UTF_8;
    private static final HashFunction HASHF = Hashing.murmur3_128();
    private static final byte ENCODING_BASE = 36;

    private String base;
    @SuppressFBWarnings(justification = "This hash is recomputed if needed after de-serialization.")
    private transient HashCode hash;
    private long hash64bit;
    private int hash32bit;
    private final String s;

    /**
     * Clones this object.
     * 
     * @param string
     *            the template for the clone
     */
    public FasterString(final FasterString string) {
        s = string.s;
        hash64bit = string.hash64bit;
        hash32bit = string.hash32bit;
    }

    /**
     * @param string
     *            the String to wrap
     */
    public FasterString(final String string) {
        Objects.requireNonNull(string);
        s = string;
    }

    @Override
    public char charAt(final int index) {
        return s.charAt(index);
    }

    @Override
    public FasterString clone() {
        /*
         * State cannot change, no need to deep copy anything.
         */
        return this;
    }

    @Override
    public int compareTo(final FasterString o) {
        return s.compareTo(o.s);
    }

    private void computeHashes() {
        hash = HASHF.hashBytes(s.getBytes(CHARSET));
        hash32bit = hash.asInt();
        hash64bit = hash.asLong();
    }

    /**
     * Overloaded method.
     * 
     * @param fs
     *            the FasterString to compare to
     * @return true if equals
     */
    public boolean equals(final FasterString fs) {
        return hashCode() == fs.hashCode()
                && s.length() == fs.s.length()
                && hash64bit == fs.hash64bit
                && hash.equals(fs.hash);
    }

    @Override
    public boolean equals(final Object o) {
        return o instanceof FasterString && equals((FasterString) o);
    }

    /**
     * @return a 64bit hash, computed with DJB2
     */
    public long hash64() {
        if (hash == null) {
            computeHashes();
        }
        return hash64bit;
    }

    @Override
    public int hashCode() {
        if (hash == null) {
            computeHashes();
        }
        return hash32bit;
    }

    /**
     * @return A Base64 encoded version of the hash
     */
    public String hashToString() {
        if (base == null) {
            /*
             * If hash32 is negative, it is necessary to sum 1. This is because
             * -Integer.MIN_VALUE is equal to Integer.MIN_VALUE.
             */
            final int h32 = hashCode() > 0 ? hash32bit : -(hash32bit + 1);
            final long h64 = hash64bit > 0 ? hash64bit : -(hash64bit + 1);
            base = Integer.toString(h32, ENCODING_BASE) + Long.toString(h64, ENCODING_BASE);
        }
        return base;
    }

    @Override
    public int length() {
        return s.length();
    }

    @Override
    public CharSequence subSequence(final int start, final int end) {
        return s.subSequence(start, end);
    }

    @Override
    public String toString() {
        return s;
    }

}
