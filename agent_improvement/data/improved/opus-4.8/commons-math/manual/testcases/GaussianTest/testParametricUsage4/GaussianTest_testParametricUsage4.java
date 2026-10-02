package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.exception.NullArgumentException;
import org.junit.Test;

/**
 * Tests that {@link Gaussian.Parametric#gradient(double, double...)} rejects a
 * {@code null} parameter array by throwing a {@link NullArgumentException}.
 */
public class GaussianTest_testParametricUsage4 {

    @Test(expected = NullArgumentException.class)
    public void gradientWithNullParametersThrowsNullArgumentException() {
        final Gaussian.Parametric gaussian = new Gaussian.Parametric();

        final double anyPoint = 0;
        final double[] nullParameters = null;
        gaussian.gradient(anyPoint, nullParameters);
    }
}
