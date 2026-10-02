package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.exception.DimensionMismatchException;
import org.junit.Test;

/**
 * Verifies that {@link Sigmoid.Parametric#gradient(double, double...)} rejects a
 * parameter array whose length does not match the number of parameters the
 * Sigmoid function expects.
 *
 * <p>{@code Sigmoid.Parametric} is defined by two parameters (the lower and
 * upper asymptotes), so it requires a parameter array of length 2. Supplying an
 * array of length 1 is a dimension mismatch and must raise a
 * {@link DimensionMismatchException}.</p>
 */
public class SigmoidTest_testParametricUsage4 {

    @Test(expected = DimensionMismatchException.class)
    public void gradientRejectsParameterArrayOfWrongLength() {
        final Sigmoid.Parametric sigmoid = new Sigmoid.Parametric();

        // Only one parameter is provided, but two are required -> mismatch.
        final double[] tooFewParameters = { 0 };
        sigmoid.gradient(0, tooFewParameters);
    }
}
