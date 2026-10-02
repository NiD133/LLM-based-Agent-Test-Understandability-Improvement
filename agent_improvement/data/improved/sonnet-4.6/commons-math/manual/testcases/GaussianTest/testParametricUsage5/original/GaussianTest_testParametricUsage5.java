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

public class GaussianTest_testParametricUsage5 {

    private final double EPS = Math.ulp(1d);

    @Test(expected = DimensionMismatchException.class)
    public void testParametricUsage5() {
        final Gaussian.Parametric g = new Gaussian.Parametric();
        g.gradient(0, new double[] { 0 });
    }
}
