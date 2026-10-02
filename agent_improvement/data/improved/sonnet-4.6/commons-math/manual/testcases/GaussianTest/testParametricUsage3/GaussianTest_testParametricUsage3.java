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

public class GaussianTest_testParametricUsage3 {

    private final double EPS = Math.ulp(1d);

    // Gaussian.Parametric.value() expects parameters in order: [norm, mean, sigma]
    // sigma must be strictly positive; sigma = 0 is invalid and must trigger NotStrictlyPositiveException.
    @Test(expected = NotStrictlyPositiveException.class)
    public void testParametricUsage3() {
        final Gaussian.Parametric g = new Gaussian.Parametric();

        final double x = 0;
        final double norm = 0;
        final double mean = 1;
        final double sigmaZero = 0; // invalid: sigma must be > 0

        g.value(x, new double[] { norm, mean, sigmaZero });
    }
}
