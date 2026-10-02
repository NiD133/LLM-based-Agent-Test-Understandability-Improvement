package org.apache.commons.math4.legacy.analysis.function;

import org.junit.Assert;
import org.junit.Test;

/**
 * Verifies that {@link Gaussian.Parametric#value(double, double[])}, which
 * evaluates a Gaussian from an explicit parameter array, produces exactly the
 * same result as a {@link Gaussian} instance built with those same parameters.
 */
public class GaussianTest_testParametricValue {

    /** Allowed difference between the two evaluations: the results must match exactly. */
    private static final double EXACT = 0;

    /** Gaussian parameters shared by the direct function and the parametric form. */
    private static final double NORM = 2;
    private static final double MEAN = 3;
    private static final double SIGMA = 4;

    @Test
    public void testParametricValue() {
        final Gaussian directFunction = new Gaussian(NORM, MEAN, SIGMA);
        final Gaussian.Parametric parametricFunction = new Gaussian.Parametric();
        final double[] parameters = { NORM, MEAN, SIGMA };

        for (final double x : new double[] { -1, 0, 2 }) {
            Assert.assertEquals(directFunction.value(x),
                                parametricFunction.value(x, parameters),
                                EXACT);
        }
    }
}
