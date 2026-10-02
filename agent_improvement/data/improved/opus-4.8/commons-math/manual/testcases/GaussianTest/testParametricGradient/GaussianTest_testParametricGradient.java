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

    /** Tolerance for comparing floating-point results (one unit in the last place). */
    private final double TOLERANCE = Math.ulp(1d);

    /**
     * Verifies that {@link Gaussian.Parametric#gradient} returns the partial derivatives
     * of the Gaussian with respect to its three parameters (norm, mean, sigma).
     *
     * <p>For a Gaussian {@code f(x) = norm * exp(-(x - mean)^2 / (2 * sigma^2))} the
     * partial derivatives at a point {@code x} are:</p>
     * <ul>
     *   <li>d/d(norm)  = exp(-(x - mean)^2 / (2 * sigma^2))</li>
     *   <li>d/d(mean)  = norm * exp(...) * (x - mean) / sigma^2</li>
     *   <li>d/d(sigma) = norm * exp(...) * (x - mean)^2 / sigma^3</li>
     * </ul>
     */
    @Test
    public void testParametricGradient() {
        final double norm = 2;
        final double mean = 3;
        final double sigma = 4;
        final double x = 1;

        final Gaussian.Parametric f = new Gaussian.Parametric();
        final double[] gradient = f.gradient(x, new double[] { norm, mean, sigma });

        final double xMinusMean = x - mean;
        final double variance = sigma * sigma;

        final double expectedDNorm = JdkMath.exp(-xMinusMean * xMinusMean / (2 * variance));
        Assert.assertEquals(expectedDNorm, gradient[0], TOLERANCE);

        final double expectedDMean = norm * expectedDNorm * xMinusMean / variance;
        Assert.assertEquals(expectedDMean, gradient[1], TOLERANCE);

        final double expectedDSigma = expectedDMean * xMinusMean / sigma;
        Assert.assertEquals(expectedDSigma, gradient[2], TOLERANCE);
    }
}
