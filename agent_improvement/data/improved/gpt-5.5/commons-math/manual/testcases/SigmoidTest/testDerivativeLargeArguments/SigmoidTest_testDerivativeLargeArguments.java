package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.analysis.differentiation.DerivativeStructure;
import org.junit.Assert;
import org.junit.Test;

public class SigmoidTest_testDerivativeLargeArguments {

    @Test
    public void testDerivativeLargeArguments() {
        final Sigmoid sigmoid = new Sigmoid(1, 2);

        assertZeroFirstDerivative(sigmoid, Double.NEGATIVE_INFINITY);
        assertZeroFirstDerivative(sigmoid, -Double.MAX_VALUE);
        assertZeroFirstDerivative(sigmoid, -1e50);
        assertZeroFirstDerivative(sigmoid, -1e3);
        assertZeroFirstDerivative(sigmoid, 1e3);
        assertZeroFirstDerivative(sigmoid, 1e50);
        assertZeroFirstDerivative(sigmoid, Double.MAX_VALUE);
        assertZeroFirstDerivative(sigmoid, Double.POSITIVE_INFINITY);
    }

    private void assertZeroFirstDerivative(final Sigmoid sigmoid, final double argument) {
        final DerivativeStructure derivativeArgument = new DerivativeStructure(1, 1, 0, argument);
        Assert.assertEquals(0, sigmoid.value(derivativeArgument).getPartialDerivative(1), 0);
    }
}
