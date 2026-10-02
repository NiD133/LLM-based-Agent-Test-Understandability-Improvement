package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.exception.DimensionMismatchException;
import org.junit.Test;

public class SigmoidTest_testParametricUsage4 {

    /**
     * Sigmoid.Parametric.gradient() requires exactly two parameters (lower and upper asymptote).
     * Passing only one parameter should throw DimensionMismatchException.
     */
    @Test(expected = DimensionMismatchException.class)
    public void testGradientThrowsWhenParameterArrayHasWrongLength() {
        final Sigmoid.Parametric sigmoidParametric = new Sigmoid.Parametric();
        double[] tooFewParameters = new double[] { 0 };

        sigmoidParametric.gradient(0, tooFewParameters);
    }
}
