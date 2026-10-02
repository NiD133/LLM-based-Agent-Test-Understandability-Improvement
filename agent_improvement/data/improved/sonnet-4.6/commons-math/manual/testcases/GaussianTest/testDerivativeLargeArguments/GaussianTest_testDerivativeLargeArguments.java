package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.analysis.differentiation.DerivativeStructure;
import org.junit.Assert;
import org.junit.Test;

public class GaussianTest_testDerivativeLargeArguments {

    // Gaussian with an extremely small sigma so that the bell curve is
    // concentrated near zero, making the derivative essentially 0 for any
    // argument that is large relative to sigma.
    private static final double TINY_SIGMA = 1e-50;

    /** Returns the first derivative of f evaluated at x using automatic differentiation. */
    private double firstDerivative(Gaussian f, double x) {
        return f.value(new DerivativeStructure(1, 1, 0, x)).getPartialDerivative(1);
    }

    @Test
    public void testDerivativeLargeArguments() {
        final Gaussian f = new Gaussian(0, TINY_SIGMA);

        // Negative extreme values
        Assert.assertEquals(0, firstDerivative(f, Double.NEGATIVE_INFINITY), 0);
        Assert.assertEquals(0, firstDerivative(f, -Double.MAX_VALUE), 0);
        Assert.assertEquals(0, firstDerivative(f, -1e50), 0);
        Assert.assertEquals(0, firstDerivative(f, -1e2), 0);

        // Positive extreme values
        Assert.assertEquals(0, firstDerivative(f, 1e2), 0);
        Assert.assertEquals(0, firstDerivative(f, 1e50), 0);
        Assert.assertEquals(0, firstDerivative(f, Double.MAX_VALUE), 0);
        Assert.assertEquals(0, firstDerivative(f, Double.POSITIVE_INFINITY), 0);
    }
}
