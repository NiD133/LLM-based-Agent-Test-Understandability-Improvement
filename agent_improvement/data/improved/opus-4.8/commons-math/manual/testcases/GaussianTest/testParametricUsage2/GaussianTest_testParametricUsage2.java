package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.exception.DimensionMismatchException;
import org.junit.Test;

/**
 * Verifies the argument validation performed by {@link Gaussian.Parametric#value(double, double[])}.
 *
 * <p>The Gaussian parametric form is defined by exactly three parameters
 * (normalization, mean and standard deviation). Supplying a parameter array of any
 * other length must be rejected with a {@link DimensionMismatchException}.</p>
 */
public class GaussianTest_testParametricUsage2 {

    /** Number of parameters supplied below (1), which is fewer than the required 3. */
    @Test(expected = DimensionMismatchException.class)
    public void valueRejectsParameterArrayOfWrongLength() {
        final Gaussian.Parametric gaussian = new Gaussian.Parametric();

        final double evaluationPoint = 0;
        final double[] tooFewParameters = { 0 };

        // Only one parameter is provided, so evaluation must fail.
        gaussian.value(evaluationPoint, tooFewParameters);
    }
}
