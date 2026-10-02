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

public class GaussianTest_testParametricValue {

    private final double EPS = Math.ulp(1d);

    @Test
    public void testParametricValue() {
        final double norm = 2;
        final double mean = 3;
        final double sigma = 4;
        final Gaussian f = new Gaussian(norm, mean, sigma);
        final Gaussian.Parametric g = new Gaussian.Parametric();
        Assert.assertEquals(f.value(-1), g.value(-1, new double[] { norm, mean, sigma }), 0);
        Assert.assertEquals(f.value(0), g.value(0, new double[] { norm, mean, sigma }), 0);
        Assert.assertEquals(f.value(2), g.value(2, new double[] { norm, mean, sigma }), 0);
    }
}
