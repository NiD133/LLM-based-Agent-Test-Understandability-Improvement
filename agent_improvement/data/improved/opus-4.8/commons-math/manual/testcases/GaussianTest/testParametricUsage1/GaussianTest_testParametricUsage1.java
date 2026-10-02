package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.exception.NullArgumentException;
import org.junit.Test;

/**
 * Verifies that {@link Gaussian.Parametric#value(double, double[])} rejects a
 * {@code null} parameter array.
 *
 * <p>The parameter array is expected to hold the Gaussian's {@code norm},
 * {@code mean} and {@code standardDeviation}. Passing {@code null} instead of
 * such an array must fail fast with a {@link NullArgumentException}.</p>
 */
public class GaussianTest_testParametricUsage1 {

    @Test(expected = NullArgumentException.class)
    public void valueRejectsNullParameterArray() {
        final Gaussian.Parametric gaussian = new Gaussian.Parametric();

        final double anyAbscissa = 0;
        final double[] nullParameters = null;

        gaussian.value(anyAbscissa, nullParameters);
    }
}
