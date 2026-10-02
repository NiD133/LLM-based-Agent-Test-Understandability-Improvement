package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.exception.NotStrictlyPositiveException;
import org.junit.Test;

/**
 * Tests that {@link Gaussian.Parametric#gradient} rejects a non-positive
 * standard deviation, because a Gaussian with sigma <= 0 is undefined.
 */
public class GaussianTest_testParametricUsage6 {

    // Gaussian parameters: [norm, mean, sigma]
    private static final double NORM  = 0;
    private static final double MEAN  = 1;
    private static final double SIGMA = 0; // invalid: sigma must be strictly positive

    @Test(expected = NotStrictlyPositiveException.class)
    public void testParametricGradientThrowsWhenSigmaIsZero() {
        Gaussian.Parametric parametric = new Gaussian.Parametric();

        // sigma = 0 violates the strict-positivity constraint, so gradient()
        // must throw NotStrictlyPositiveException instead of returning a value.
        parametric.gradient(0, new double[] { NORM, MEAN, SIGMA });
    }
}
