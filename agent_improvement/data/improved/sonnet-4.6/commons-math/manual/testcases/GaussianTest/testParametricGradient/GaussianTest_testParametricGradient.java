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

public class GaussianTest_testParametricGradient {

    private final double EPS = Math.ulp(1d);

    @Test
    public void testParametricGradient() {
        final double norm = 2;
        final double mean = 3;
        final double sigma = 4;
        final Gaussian.Parametric f = new Gaussian.Parametric();
        final double x = 1;
        final double[] grad = f.gradient(x, new double[] { norm, mean, sigma });

        // Compute the expected gradient values analytically.
        // The Gaussian is: g(x) = norm * exp(-(x - mean)^2 / (2 * sigma^2))
        final double distFromMean = x - mean;
        final double expFactor = JdkMath.exp(-distFromMean * distFromMean / (2 * sigma * sigma));

        // d/d(norm): expFactor
        final double expectedGradNorm = expFactor;
        Assert.assertEquals(expectedGradNorm, grad[0], EPS);

        // d/d(mean): norm * expFactor * (x - mean) / sigma^2
        final double expectedGradMean = norm * expFactor * distFromMean / (sigma * sigma);
        Assert.assertEquals(expectedGradMean, grad[1], EPS);

        // d/d(sigma): norm * expFactor * (x - mean)^2 / sigma^3
        final double expectedGradSigma = expectedGradMean * distFromMean / sigma;
        Assert.assertEquals(expectedGradSigma, grad[2], EPS);
    }
}
