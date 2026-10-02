package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.exception.DimensionMismatchException;
import org.junit.Test;

/**
 * Tests that {@link Sigmoid.Parametric#gradient} throws {@link DimensionMismatchException}
 * when the parameter array has too few elements.
 *
 * The Sigmoid.Parametric function requires exactly two parameters [lo, hi].
 * Passing an array with only one element must be rejected.
 */
public class SigmoidTest_testParametricUsage4 {

    /** Sigmoid.Parametric requires exactly two parameters: [lo, hi]. */
    private static final int REQUIRED_PARAM_COUNT = 2;

    @Test(expected = DimensionMismatchException.class)
    public void gradient_throwsDimensionMismatch_whenParameterArrayHasOnlyOneElement() {
        final Sigmoid.Parametric sigmoidParametric = new Sigmoid.Parametric();

        // Provide only one parameter instead of the required two [lo, hi]
        final double evaluationPoint = 0;
        final double[] tooFewParams = { 0 }; // missing the second parameter

        sigmoidParametric.gradient(evaluationPoint, tooFewParams);
    }
}
