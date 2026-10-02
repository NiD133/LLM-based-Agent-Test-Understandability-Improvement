package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.analysis.UnivariateFunction;
import org.apache.commons.math4.legacy.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math4.legacy.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math4.legacy.exception.DimensionMismatchException;
import org.apache.commons.math4.legacy.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.legacy.exception.NullArgumentException;
import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Assert;
import org.junit.Test;

/**
 * Verifies that {@link Gaussian.Parametric#value(double, double[])} produces
 * the same output as {@link Gaussian#value(double)} when given the same
 * norm, mean, and sigma parameters.
 */
public class GaussianTest_testParametricValue {

    private final double EPS = Math.ulp(1d);

    @Test
    public void testParametricValue() {
        final double norm = 2;
        final double mean = 3;
        final double sigma = 4;

        // Gaussian instance with fixed parameters for direct evaluation
        final Gaussian fixedGaussian = new Gaussian(norm, mean, sigma);

        // Parametric form that accepts norm/mean/sigma as a runtime array
        final Gaussian.Parametric parametricGaussian = new Gaussian.Parametric();
        final double[] params = new double[] { norm, mean, sigma };

        // Both forms must agree at several representative x values
        Assert.assertEquals(
            "Parametric value should match fixed Gaussian at x=-1",
            fixedGaussian.value(-1),
            parametricGaussian.value(-1, params),
            0);

        Assert.assertEquals(
            "Parametric value should match fixed Gaussian at x=0",
            fixedGaussian.value(0),
            parametricGaussian.value(0, params),
            0);

        Assert.assertEquals(
            "Parametric value should match fixed Gaussian at x=2",
            fixedGaussian.value(2),
            parametricGaussian.value(2, params),
            0);
    }
}
