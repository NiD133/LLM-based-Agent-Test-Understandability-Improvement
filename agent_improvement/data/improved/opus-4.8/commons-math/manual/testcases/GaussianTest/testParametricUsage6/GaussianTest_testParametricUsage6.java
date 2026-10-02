package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.exception.NotStrictlyPositiveException;
import org.junit.Test;

/**
 * Verifies that {@link Gaussian.Parametric#gradient(double, double[])} rejects an
 * invalid standard deviation.
 *
 * <p>The parameter array passed to a Gaussian's parametric methods holds three values
 * in order: {@code [normalization, mean, sigma]}. The standard deviation {@code sigma}
 * must be strictly positive; supplying {@code 0} for it is illegal.</p>
 */
public class GaussianTest_testParametricUsage6 {

    /**
     * A {@code sigma} of zero must cause {@code gradient} to fail, because the standard
     * deviation is required to be strictly positive.
     */
    @Test(expected = NotStrictlyPositiveException.class)
    public void gradientRejectsNonPositiveSigma() {
        final Gaussian.Parametric gaussian = new Gaussian.Parametric();

        final double x = 0;
        final double normalization = 0;
        final double mean = 1;
        final double sigma = 0; // invalid: sigma must be strictly positive
        final double[] parameters = { normalization, mean, sigma };

        gaussian.gradient(x, parameters);
    }
}
