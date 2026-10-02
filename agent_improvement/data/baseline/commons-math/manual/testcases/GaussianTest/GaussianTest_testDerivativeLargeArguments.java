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

public class GaussianTest_testDerivativeLargeArguments {

    private final double EPS = Math.ulp(1d);

    @Test
    public void testDerivativeLargeArguments() {
        final Gaussian f = new Gaussian(0, 1e-50);
        Assert.assertEquals(0, f.value(new DerivativeStructure(1, 1, 0, Double.NEGATIVE_INFINITY)).getPartialDerivative(1), 0);
        Assert.assertEquals(0, f.value(new DerivativeStructure(1, 1, 0, -Double.MAX_VALUE)).getPartialDerivative(1), 0);
        Assert.assertEquals(0, f.value(new DerivativeStructure(1, 1, 0, -1e50)).getPartialDerivative(1), 0);
        Assert.assertEquals(0, f.value(new DerivativeStructure(1, 1, 0, -1e2)).getPartialDerivative(1), 0);
        Assert.assertEquals(0, f.value(new DerivativeStructure(1, 1, 0, 1e2)).getPartialDerivative(1), 0);
        Assert.assertEquals(0, f.value(new DerivativeStructure(1, 1, 0, 1e50)).getPartialDerivative(1), 0);
        Assert.assertEquals(0, f.value(new DerivativeStructure(1, 1, 0, Double.MAX_VALUE)).getPartialDerivative(1), 0);
        Assert.assertEquals(0, f.value(new DerivativeStructure(1, 1, 0, Double.POSITIVE_INFINITY)).getPartialDerivative(1), 0);
    }
}
