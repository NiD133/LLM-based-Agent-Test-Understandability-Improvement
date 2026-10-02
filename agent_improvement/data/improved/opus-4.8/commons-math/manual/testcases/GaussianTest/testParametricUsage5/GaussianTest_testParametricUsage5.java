package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.exception.DimensionMismatchException;
import org.junit.Test;

/**
 * Verifies that {@link Gaussian.Parametric#gradient(double, double[])} rejects a
 * parameter array of the wrong length.
 *
 * <p>The parametric Gaussian is defined by exactly three parameters
 * (normalization, mean and standard deviation), so calling {@code gradient}
 * with a single-element array must fail with a {@link DimensionMismatchException}.
 */
public class GaussianTest_testParametricUsage5 {

    @Test(expected = DimensionMismatchException.class)
    public void gradientRejectsWrongLengthParameterArray() {
        final Gaussian.Parametric gaussian = new Gaussian.Parametric();

        // Only one parameter is supplied instead of the required three.
        final double[] tooFewParameters = { 0 };

        gaussian.gradient(0, tooFewParameters);
    }
}
