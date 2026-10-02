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

public class GaussianTest_testDerivatives {

    private final double EPS = Math.ulp(1d);

    @Test
    public void testDerivatives() {
        final UnivariateDifferentiableFunction gaussian = new Gaussian(2.0, 0.9, 3.0);
        final DerivativeStructure dsX = new DerivativeStructure(1, 4, 0, 1.1);
        final DerivativeStructure dsY = gaussian.value(dsX);
        Assert.assertEquals(1.9955604901712128349, dsY.getValue(), EPS);
        Assert.assertEquals(-0.044345788670471396332, dsY.getPartialDerivative(1), EPS);
        Assert.assertEquals(-0.22074348138190206174, dsY.getPartialDerivative(2), EPS);
        Assert.assertEquals(0.014760030401924800557, dsY.getPartialDerivative(3), EPS);
        Assert.assertEquals(0.073253159785035691678, dsY.getPartialDerivative(4), EPS);
    }
}
