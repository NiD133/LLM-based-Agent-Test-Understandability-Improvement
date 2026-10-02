package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.analysis.differentiation.DerivativeStructure;
import org.junit.Assert;
import org.junit.Test;

public class SigmoidTest_testDerivativesHighOrder {

    private static final int FREE_PARAMETERS = 1;
    private static final int MAX_DERIVATIVE_ORDER = 5;
    private static final int VARIABLE_INDEX = 0;
    private static final double VARIABLE_VALUE = 1.2;

    @Test
    public void testDerivativesHighOrder() {
        DerivativeStructure input =
                new DerivativeStructure(FREE_PARAMETERS, MAX_DERIVATIVE_ORDER, VARIABLE_INDEX, VARIABLE_VALUE);
        DerivativeStructure sigmoid = new Sigmoid(1, 3).value(input);

        assertPartialDerivative(sigmoid, 0, 2.5370495669980352859, 5.0e-16);
        assertPartialDerivative(sigmoid, 1, 0.35578888129361140441, 6.0e-17);
        assertPartialDerivative(sigmoid, 2, -0.19107626464144938116, 6.0e-17);
        assertPartialDerivative(sigmoid, 3, -0.02396830286286711696, 4.0e-17);
        assertPartialDerivative(sigmoid, 4, 0.21682059798981049049, 3.0e-17);
        assertPartialDerivative(sigmoid, 5, -0.19186320234632658055, 2.0e-16);
    }

    private void assertPartialDerivative(DerivativeStructure sigmoid,
                                         int derivativeOrder,
                                         double expectedValue,
                                         double tolerance) {
        Assert.assertEquals(expectedValue, sigmoid.getPartialDerivative(derivativeOrder), tolerance);
    }
}
