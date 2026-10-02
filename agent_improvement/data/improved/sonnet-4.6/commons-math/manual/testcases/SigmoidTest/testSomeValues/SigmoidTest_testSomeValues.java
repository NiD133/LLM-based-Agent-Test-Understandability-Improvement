package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.analysis.UnivariateFunction;
import org.junit.Assert;
import org.junit.Test;

public class SigmoidTest_testSomeValues {

    // Tolerance for floating-point comparisons: one ULP of 1.0 (~2.22e-16)
    private static final double TOLERANCE = Math.ulp(1d);

    @Test
    public void testSomeValues() {
        final UnivariateFunction sigmoid = new Sigmoid();

        // sigmoid(0) = 0.5 by symmetry of the logistic curve
        Assert.assertEquals(0.5, sigmoid.value(0), TOLERANCE);

        // As x → -∞, sigmoid approaches 0 (lower asymptote)
        Assert.assertEquals(0, sigmoid.value(Double.NEGATIVE_INFINITY), TOLERANCE);

        // As x → +∞, sigmoid approaches 1 (upper asymptote)
        Assert.assertEquals(1, sigmoid.value(Double.POSITIVE_INFINITY), TOLERANCE);
    }
}
