package org.apache.commons.math4.legacy.analysis.function;

import org.junit.Assert;
import org.junit.Test;

public class GaussianTest_testParametricValue {

    private static final double NORM = 2;
    private static final double MEAN = 3;
    private static final double SIGMA = 4;
    private static final double EXACT_TOLERANCE = 0;

    @Test
    public void testParametricValue() {
        final Gaussian gaussian = new Gaussian(NORM, MEAN, SIGMA);
        final Gaussian.Parametric parametricGaussian = new Gaussian.Parametric();

        assertParametricValueMatchesGaussian(gaussian, parametricGaussian, -1);
        assertParametricValueMatchesGaussian(gaussian, parametricGaussian, 0);
        assertParametricValueMatchesGaussian(gaussian, parametricGaussian, 2);
    }

    private static void assertParametricValueMatchesGaussian(final Gaussian gaussian,
                                                            final Gaussian.Parametric parametricGaussian,
                                                            final double x) {
        Assert.assertEquals(gaussian.value(x),
                            parametricGaussian.value(x, new double[] { NORM, MEAN, SIGMA }),
                            EXACT_TOLERANCE);
    }
}
