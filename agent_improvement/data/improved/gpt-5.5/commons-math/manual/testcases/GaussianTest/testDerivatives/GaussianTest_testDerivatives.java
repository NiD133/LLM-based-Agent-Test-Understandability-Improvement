package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math4.legacy.analysis.differentiation.UnivariateDifferentiableFunction;
import org.junit.Assert;
import org.junit.Test;

public class GaussianTest_testDerivatives {

    private static final double EPS = Math.ulp(1d);

    private static final double NORMALIZATION = 2.0;
    private static final double MEAN = 0.9;
    private static final double STANDARD_DEVIATION = 3.0;
    private static final double EVALUATION_POINT = 1.1;
    private static final int DERIVATIVE_ORDER = 4;

    @Test
    public void testDerivatives() {
        final UnivariateDifferentiableFunction gaussian =
                new Gaussian(NORMALIZATION, MEAN, STANDARD_DEVIATION);
        final DerivativeStructure x =
                new DerivativeStructure(1, DERIVATIVE_ORDER, 0, EVALUATION_POINT);

        final DerivativeStructure y = gaussian.value(x);

        assertValueAndDerivatives(y);
    }

    private static void assertValueAndDerivatives(final DerivativeStructure y) {
        Assert.assertEquals(1.9955604901712128349, y.getValue(), EPS);
        Assert.assertEquals(-0.044345788670471396332, y.getPartialDerivative(1), EPS);
        Assert.assertEquals(-0.22074348138190206174, y.getPartialDerivative(2), EPS);
        Assert.assertEquals(0.014760030401924800557, y.getPartialDerivative(3), EPS);
        Assert.assertEquals(0.073253159785035691678, y.getPartialDerivative(4), EPS);
    }
}
