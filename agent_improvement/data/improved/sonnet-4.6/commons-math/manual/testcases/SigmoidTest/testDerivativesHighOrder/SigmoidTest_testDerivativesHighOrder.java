package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.analysis.differentiation.DerivativeStructure;
import org.junit.Assert;
import org.junit.Test;

public class SigmoidTest_testDerivativesHighOrder {

    // Sigmoid scaled to the range [lo, hi], evaluated as a DerivativeStructure
    // so we can retrieve value and higher-order derivatives in one pass.
    private static final double SIGMOID_LO = 1.0;
    private static final double SIGMOID_HI = 3.0;

    // The input point at which we evaluate the sigmoid and its derivatives.
    private static final double INPUT_X = 1.2;

    // Number of free variables in the DerivativeStructure (only x here).
    private static final int NUM_FREE_VARS = 1;

    // Maximum derivative order we want to compute (0th through 5th).
    private static final int MAX_DERIVATIVE_ORDER = 5;

    // Index of the free variable x within the DerivativeStructure parameter list.
    private static final int X_VAR_INDEX = 0;

    /**
     * Expected values for the sigmoid and its first five derivatives at x = 1.2,
     * for sigmoid(x; lo=1, hi=3).  Index i holds the expected value of d^i/dx^i.
     *
     * Entry [0]: function value          s(1.2)
     * Entry [1]: first derivative        s'(1.2)
     * Entry [2]: second derivative       s''(1.2)
     * Entry [3]: third derivative        s'''(1.2)
     * Entry [4]: fourth derivative       s''''(1.2)
     * Entry [5]: fifth derivative        s'''''(1.2)
     */
    private static final double[] EXPECTED_DERIVATIVES = {
         2.5370495669980352859,   // s(1.2)
         0.35578888129361140441,  // s'(1.2)
        -0.19107626464144938116,  // s''(1.2)
        -0.02396830286286711696,  // s'''(1.2)
         0.21682059798981049049,  // s''''(1.2)
        -0.19186320234632658055   // s'''''(1.2)
    };

    /**
     * Tolerances for each derivative order — tighter at low orders where
     * the values are larger, somewhat looser for the higher-order terms.
     */
    private static final double[] TOLERANCES = {
        5.0e-16,  // tolerance for s(1.2)
        6.0e-17,  // tolerance for s'(1.2)
        6.0e-17,  // tolerance for s''(1.2)
        4.0e-17,  // tolerance for s'''(1.2)
        3.0e-17,  // tolerance for s''''(1.2)
        2.0e-16   // tolerance for s'''''(1.2)
    };

    @Test
    public void testDerivativesHighOrder() {
        // Wrap the input value in a DerivativeStructure so that the Sigmoid
        // implementation propagates the chain rule up to MAX_DERIVATIVE_ORDER.
        DerivativeStructure xDS = new DerivativeStructure(
                NUM_FREE_VARS, MAX_DERIVATIVE_ORDER, X_VAR_INDEX, INPUT_X);

        // Evaluate the scaled sigmoid s(x) = lo + (hi - lo) * sigmoid(x).
        DerivativeStructure sigmoidResult =
                new Sigmoid(SIGMOID_LO, SIGMOID_HI).value(xDS);

        // Verify the function value and each derivative order independently
        // so a failure message clearly identifies which order is wrong.
        for (int order = 0; order <= MAX_DERIVATIVE_ORDER; order++) {
            Assert.assertEquals(
                    "Mismatch at derivative order " + order,
                    EXPECTED_DERIVATIVES[order],
                    sigmoidResult.getPartialDerivative(order),
                    TOLERANCES[order]);
        }
    }
}
