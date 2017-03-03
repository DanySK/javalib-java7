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
import java.util.Arrays;
import java.util.Objects;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * This class wraps {@link String} and provides faster equals() at the expense
 * of possible failures. Internally, it computes a high quality
 * non-cryptographic hash (currently using Murmur 3 128bit), so be aware that
 * collisions may happen.
 * 
 */
public class FasterString implements Cloneable, Serializable, Comparable<FasterString>, CharSequence {

    private static final long serialVersionUID = -3490623928660729120L;
    private static final Charset CHARSET = StandardCharsets.UTF_8;
    private static final HashFunction HASHF = Hashing.murmur3_128();
    private static final byte ENCODING_BASE = 36;

    private String base;
    private byte[] hash;
    private int hash32;
    private final String s;

    /**
     * Clones this object.
     * 
     * @param string
     *            the template for the clone
     */
    public FasterString(final FasterString string) {
        s = string.s;
        hash = string.hash;
        hash32 = string.hash32;
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

    @SuppressFBWarnings("CN_IDIOM_NO_SUPER_CALL")
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
        final HashCode hashCode = HASHF.hashBytes(s.getBytes(CHARSET));
        hash32 = hashCode.asInt();
        hash = hashCode.asBytes();
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
                && Arrays.equals(hash, fs.hash);
    }

    @Override
    public boolean equals(final Object o) {
        return o instanceof FasterString && equals((FasterString) o);
    }

    @Override
    public int hashCode() {
        if (hash == null) {
            computeHashes();
        }
        return hash32;
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
            final int h32 = hashCode() > 0 ? hash32 : -(hash32 + 1);
            base = Integer.toString(h32, ENCODING_BASE);
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
