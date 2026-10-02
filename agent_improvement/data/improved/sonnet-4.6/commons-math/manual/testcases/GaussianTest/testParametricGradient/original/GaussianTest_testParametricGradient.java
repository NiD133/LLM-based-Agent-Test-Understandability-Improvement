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
        final double[] grad = f.gradient(1, new double[] { norm, mean, sigma });
        final double diff = x - mean;
        final double n = JdkMath.exp(-diff * diff / (2 * sigma * sigma));
        Assert.assertEquals(n, grad[0], EPS);
        final double m = norm * n * diff / (sigma * sigma);
        Assert.assertEquals(m, grad[1], EPS);
        final double s = m * diff / sigma;
        Assert.assertEquals(s, grad[2], EPS);
    }
}
