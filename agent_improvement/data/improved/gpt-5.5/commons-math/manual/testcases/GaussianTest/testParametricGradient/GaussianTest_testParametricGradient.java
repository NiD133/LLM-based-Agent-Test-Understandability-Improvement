package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Assert;
import org.junit.Test;

public class GaussianTest_testParametricGradient {

    private static final double EPS = Math.ulp(1d);

    @Test
    public void testParametricGradient() {
        final double norm = 2;
        final double mean = 3;
        final double sigma = 4;
        final double x = 1;
        final double[] parameters = { norm, mean, sigma };

        final Gaussian.Parametric gaussian = new Gaussian.Parametric();
        final double[] gradient = gaussian.gradient(x, parameters);

        final double xMinusMean = x - mean;
        final double exponentialPart = JdkMath.exp(-xMinusMean * xMinusMean / (2 * sigma * sigma));
        Assert.assertEquals(exponentialPart, gradient[0], EPS);

        final double meanDerivative = norm * exponentialPart * xMinusMean / (sigma * sigma);
        Assert.assertEquals(meanDerivative, gradient[1], EPS);

        final double sigmaDerivative = meanDerivative * xMinusMean / sigma;
        Assert.assertEquals(sigmaDerivative, gradient[2], EPS);
    }
}
