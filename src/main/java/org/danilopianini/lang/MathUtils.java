package org.danilopianini.lang;

/**
 * Mathematical utilities that can not be found in Java Math or Apache FastMath.
 */
public final class MathUtils {

    /**
     * Relative precision value under which two double values are considered to
     * be equal by fuzzyEquals.
     */
    public static final double DOUBLE_EQUALITY_EPSILON = 10e-12;

    private MathUtils() {
    }

    /**
     * Compares two double values, taking care of computing a relative error
     * tolerance threshold.
     * 
     * @param a
     *            first double
     * @param b
     *            second double
     * @return true if the double are equals with a precision order of
     *         DOUBLE_EQUALITY_EPSILON
     */
    public static boolean fuzzyEquals(final double a, final double b) {
        return Math.abs(a - b) <= DOUBLE_EQUALITY_EPSILON * Math.max(Math.abs(a), Math.abs(b));
    }

    /**
     * Compares two double values, taking care of computing a relative error
     * tolerance threshold.
     * 
     * @param a
     *            first double
     * @param b
     *            second double
     * @return true if a > b, or if fuzzyEquals(a, b).
     */
    public static boolean fuzzyGreaterEquals(final double a, final double b) {
        return a >= b || fuzzyEquals(a, b);
    }

}