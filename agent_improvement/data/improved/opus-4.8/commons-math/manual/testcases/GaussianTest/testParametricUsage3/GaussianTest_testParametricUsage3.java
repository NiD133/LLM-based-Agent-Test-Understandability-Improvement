package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.exception.NotStrictlyPositiveException;
import org.junit.Test;

/**
 * Verifies that {@link Gaussian.Parametric#value(double, double[])} rejects an
 * invalid standard deviation.
 *
 * <p>The parameter array follows the order {norm, mean, sigma}. A Gaussian's
 * standard deviation (sigma) must be strictly positive, so supplying sigma = 0
 * is expected to be rejected with a {@link NotStrictlyPositiveException}.
 */
public class GaussianTest_testParametricUsage3 {

    @Test(expected = NotStrictlyPositiveException.class)
    public void valueRejectsNonPositiveStandardDeviation() {
        final Gaussian.Parametric gaussian = new Gaussian.Parametric();

        final double norm = 0;
        final double mean = 1;
        final double sigma = 0; // invalid: standard deviation must be > 0
        final double[] parameters = { norm, mean, sigma };

        // Evaluating at x = 0 with sigma = 0 must throw NotStrictlyPositiveException.
        gaussian.value(0, parameters);
    }
}
