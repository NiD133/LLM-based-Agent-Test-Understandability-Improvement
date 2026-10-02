package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.exception.DimensionMismatchException;
import org.junit.Test;

/**
 * Verifies the parameter validation performed by {@link Sigmoid.Parametric}.
 */
public class SigmoidTest_testParametricUsage2 {

    /**
     * {@link Sigmoid.Parametric} is defined by two parameters (lower asymptote
     * and higher asymptote). Supplying a parameter array of the wrong length
     * must be rejected with a {@link DimensionMismatchException}.
     */
    @Test(expected = DimensionMismatchException.class)
    public void valueRejectsParameterArrayOfWrongLength() {
        final Sigmoid.Parametric sigmoid = new Sigmoid.Parametric();

        // Only one parameter is provided where two are required.
        final double[] tooFewParameters = { 0 };
        sigmoid.value(0, tooFewParameters);
    }
}
