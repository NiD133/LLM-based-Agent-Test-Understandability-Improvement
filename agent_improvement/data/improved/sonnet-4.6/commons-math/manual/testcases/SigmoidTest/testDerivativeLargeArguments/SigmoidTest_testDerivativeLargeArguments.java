package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.analysis.differentiation.DerivativeStructure;
import org.junit.Assert;
import org.junit.Test;

public class SigmoidTest_testDerivativeLargeArguments {

    // Sigmoid(lo=1, hi=2): derivative should be zero at extreme arguments
    // because the function is saturated (flat) at both ends.
    private static final Sigmoid SIGMOID = new Sigmoid(1, 2);

    /**
     * Returns the first derivative of SIGMOID evaluated at {@code x},
     * using a one-variable, first-order DerivativeStructure.
     */
    private double derivativeAt(double x) {
        return SIGMOID.value(new DerivativeStructure(1, 1, 0, x)).getPartialDerivative(1);
    }

    @Test
    public void testDerivativeLargeArguments() {
        // At both negative and positive extremes the sigmoid is flat,
        // so its derivative must be exactly zero.
        Assert.assertEquals(0, derivativeAt(Double.NEGATIVE_INFINITY), 0);
        Assert.assertEquals(0, derivativeAt(-Double.MAX_VALUE),        0);
        Assert.assertEquals(0, derivativeAt(-1e50),                    0);
        Assert.assertEquals(0, derivativeAt(-1e3),                     0);
        Assert.assertEquals(0, derivativeAt(1e3),                      0);
        Assert.assertEquals(0, derivativeAt(1e50),                     0);
        Assert.assertEquals(0, derivativeAt(Double.MAX_VALUE),         0);
        Assert.assertEquals(0, derivativeAt(Double.POSITIVE_INFINITY), 0);
    }
}
