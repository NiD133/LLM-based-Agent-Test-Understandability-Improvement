package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.analysis.UnivariateFunction;
import org.junit.Assert;
import org.junit.Test;

public class GaussianTest_testLargeArguments {

    private static final double ZERO_TOLERANCE = 0d;

    @Test
    public void testLargeArguments() {
        final UnivariateFunction gaussian = new Gaussian();

        assertEvaluatesToZero(gaussian, Double.NEGATIVE_INFINITY);
        assertEvaluatesToZero(gaussian, -Double.MAX_VALUE);
        assertEvaluatesToZero(gaussian, -1e2);
        assertEvaluatesToZero(gaussian, 1e2);
        assertEvaluatesToZero(gaussian, Double.MAX_VALUE);
        assertEvaluatesToZero(gaussian, Double.POSITIVE_INFINITY);
    }

    private void assertEvaluatesToZero(final UnivariateFunction function, final double argument) {
        Assert.assertEquals(0, function.value(argument), ZERO_TOLERANCE);
    }
}
