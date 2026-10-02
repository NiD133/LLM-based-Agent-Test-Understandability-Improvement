package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.analysis.differentiation.DerivativeStructure;
import org.junit.Assert;
import org.junit.Test;

public class GaussianTest_testDerivativeLargeArguments {

    @Test
    public void testDerivativeLargeArguments() {
        final Gaussian gaussian = new Gaussian(0, 1e-50);
        final double[] largeArguments = {
            Double.NEGATIVE_INFINITY,
            -Double.MAX_VALUE,
            -1e50,
            -1e2,
            1e2,
            1e50,
            Double.MAX_VALUE,
            Double.POSITIVE_INFINITY
        };

        for (double argument : largeArguments) {
            Assert.assertEquals(0, firstDerivativeAt(gaussian, argument), 0);
        }
    }

    private double firstDerivativeAt(Gaussian gaussian, double argument) {
        return gaussian.value(new DerivativeStructure(1, 1, 0, argument)).getPartialDerivative(1);
    }
}
