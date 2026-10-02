package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.analysis.UnivariateFunction;
import org.apache.commons.math4.legacy.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math4.legacy.exception.DimensionMismatchException;
import org.apache.commons.math4.legacy.exception.NullArgumentException;
import org.junit.Assert;
import org.junit.Test;

public class SigmoidTest_testDerivativesHighOrder {

    /**
     * Verifies that {@link Sigmoid} computes derivatives correctly up to the
     * 5th order via {@link DerivativeStructure}.
     *
     * <p>The sigmoid is configured with a lower asymptote of 1 and an upper
     * asymptote of 3, and is evaluated at x = 1.2. The expected values for the
     * function and its first five derivatives are compared against
     * pre-computed reference values, each with its own absolute tolerance.</p>
     */
    @Test
    public void testDerivativesHighOrder() {
        // Sigmoid bounded between lower asymptote 1 and upper asymptote 3.
        final double lowerAsymptote = 1;
        final double upperAsymptote = 3;
        final Sigmoid sigmoid = new Sigmoid(lowerAsymptote, upperAsymptote);

        // Evaluation point as a DerivativeStructure that tracks derivatives
        // up to order 5 for a single variable (index 0) at x = 1.2.
        final int variableCount = 1;
        final int maxDerivativeOrder = 5;
        final int variableIndex = 0;
        final double evaluationPoint = 1.2;
        final DerivativeStructure x =
                new DerivativeStructure(variableCount, maxDerivativeOrder, variableIndex, evaluationPoint);

        final DerivativeStructure result = sigmoid.value(x);

        // Each derivative order is checked against its own reference value and tolerance.
        Assert.assertEquals(2.5370495669980352859,    result.getPartialDerivative(0), 5.0e-16);
        Assert.assertEquals(0.35578888129361140441,   result.getPartialDerivative(1), 6.0e-17);
        Assert.assertEquals(-0.19107626464144938116,  result.getPartialDerivative(2), 6.0e-17);
        Assert.assertEquals(-0.02396830286286711696,  result.getPartialDerivative(3), 4.0e-17);
        Assert.assertEquals(0.21682059798981049049,   result.getPartialDerivative(4), 3.0e-17);
        Assert.assertEquals(-0.19186320234632658055,  result.getPartialDerivative(5), 2.0e-16);
    }
}
