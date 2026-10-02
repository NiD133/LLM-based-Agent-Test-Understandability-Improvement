package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.analysis.differentiation.DerivativeStructure;
import org.junit.Assert;
import org.junit.Test;

public class SigmoidTest_testDerivative {

    /**
     * The sigmoid derivative at x=0 is sigmoid(0) * (1 - sigmoid(0)) = 0.5 * 0.5 = 0.25.
     * DerivativeStructure(params, order, index, value):
     *   params=1  — one free variable
     *   order=1   — compute up to first-order derivatives
     *   index=0   — this is variable #0
     *   value=0.0 — evaluate at x = 0
     */
    @Test
    public void testDerivative() {
        final Sigmoid sigmoid = new Sigmoid();

        final DerivativeStructure inputAtZero = new DerivativeStructure(1, 1, 0, 0.0);
        final DerivativeStructure resultAtZero = sigmoid.value(inputAtZero);

        final double expectedFirstDerivativeAtZero = 0.25;
        Assert.assertEquals(expectedFirstDerivativeAtZero, resultAtZero.getPartialDerivative(1), 0);
    }
}
