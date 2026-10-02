package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.analysis.UnivariateFunction;
import org.junit.Assert;
import org.junit.Test;

/**
 * Verifies the standard logistic sigmoid at its three characteristic points:
 * the midpoint (x = 0) and the two horizontal asymptotes (x = -inf, x = +inf).
 */
public class SigmoidTest_testSomeValues {

    /** Tolerance of one unit-in-the-last-place, i.e. the tightest possible bound around 1.0. */
    private static final double TOLERANCE = Math.ulp(1d);

    @Test
    public void testSomeValues() {
        final UnivariateFunction sigmoid = new Sigmoid();

        // At x = 0 the sigmoid sits exactly halfway between its lower and upper asymptotes.
        Assert.assertEquals(0.5, sigmoid.value(0), TOLERANCE);

        // As x -> -inf the sigmoid approaches its lower asymptote (0).
        Assert.assertEquals(0, sigmoid.value(Double.NEGATIVE_INFINITY), TOLERANCE);

        // As x -> +inf the sigmoid approaches its upper asymptote (1).
        Assert.assertEquals(1, sigmoid.value(Double.POSITIVE_INFINITY), TOLERANCE);
    }
}
