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

    // Tolerance equal to one unit in the last place of a double near 1.0
    private final double EPS = Math.ulp(1d);

    // Gaussian parameters: norm=2.0, mean=0.9, sigma=3.0
    private static final double NORM  = 2.0;
    private static final double MEAN  = 0.9;
    private static final double SIGMA = 3.0;

    // Evaluation point for the derivative test
    private static final double EVAL_POINT = 1.1;

    // Expected value and successive partial derivatives of the Gaussian at EVAL_POINT
    private static final double EXPECTED_VALUE       =  1.9955604901712128349;
    private static final double EXPECTED_DERIV_1     = -0.044345788670471396332;
    private static final double EXPECTED_DERIV_2     = -0.22074348138190206174;
    private static final double EXPECTED_DERIV_3     =  0.014760030401924800557;
    private static final double EXPECTED_DERIV_4     =  0.073253159785035691678;

    /**
     * Verifies that the Gaussian function correctly computes its value and its
     * first four derivatives at a given point using automatic differentiation
     * (DerivativeStructure with 1 variable, differentiation order 4).
     */
    @Test
    public void testDerivatives() {
        final UnivariateDifferentiableFunction gaussian = new Gaussian(NORM, MEAN, SIGMA);

        // Create a DerivativeStructure representing the input variable x = EVAL_POINT,
        // tracking derivatives up to order 4 (1 free variable, variable index 0).
        final DerivativeStructure dsX = new DerivativeStructure(1, 4, 0, EVAL_POINT);
        final DerivativeStructure dsY = gaussian.value(dsX);

        Assert.assertEquals(EXPECTED_VALUE,   dsY.getValue(),                EPS);
        Assert.assertEquals(EXPECTED_DERIV_1, dsY.getPartialDerivative(1),   EPS);
        Assert.assertEquals(EXPECTED_DERIV_2, dsY.getPartialDerivative(2),   EPS);
        Assert.assertEquals(EXPECTED_DERIV_3, dsY.getPartialDerivative(3),   EPS);
        Assert.assertEquals(EXPECTED_DERIV_4, dsY.getPartialDerivative(4),   EPS);
    }
}
