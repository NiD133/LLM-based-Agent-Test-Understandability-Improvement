package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.analysis.differentiation.DerivativeStructure;
import org.junit.Assert;
import org.junit.Test;

public class GaussianTest_testDerivativesNaN {

    private static final int NUMBER_OF_VARIABLES = 1;
    private static final int MAX_DERIVATIVE_ORDER = 5;
    private static final int X_VARIABLE_INDEX = 0;
    private static final double MEAN = 0;
    private static final double STANDARD_DEVIATION = 1e-50;

    @Test
    public void testDerivativesNaN() {
        final Gaussian gaussian = new Gaussian(MEAN, STANDARD_DEVIATION);
        final DerivativeStructure x = new DerivativeStructure(
                NUMBER_OF_VARIABLES,
                MAX_DERIVATIVE_ORDER,
                X_VARIABLE_INDEX,
                Double.NaN);

        final DerivativeStructure gaussianAtNaN = gaussian.value(x);
        for (int derivativeOrder = 0; derivativeOrder <= gaussianAtNaN.getOrder(); ++derivativeOrder) {
            Assert.assertTrue(Double.isNaN(gaussianAtNaN.getPartialDerivative(derivativeOrder)));
        }
    }
}
