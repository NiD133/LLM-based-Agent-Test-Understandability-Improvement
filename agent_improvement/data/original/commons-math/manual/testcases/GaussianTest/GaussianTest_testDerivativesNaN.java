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

public class GaussianTest_testDerivativesNaN {

    private final double EPS = Math.ulp(1d);

    @Test
    public void testDerivativesNaN() {
        final Gaussian f = new Gaussian(0, 1e-50);
        final DerivativeStructure fx = f.value(new DerivativeStructure(1, 5, 0, Double.NaN));
        for (int i = 0; i <= fx.getOrder(); ++i) {
            Assert.assertTrue(Double.isNaN(fx.getPartialDerivative(i)));
        }
    }
}
