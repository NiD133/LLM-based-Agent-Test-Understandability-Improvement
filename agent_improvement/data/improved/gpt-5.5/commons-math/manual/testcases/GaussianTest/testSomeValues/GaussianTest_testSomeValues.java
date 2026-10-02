package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.analysis.UnivariateFunction;
import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Assert;
import org.junit.Test;

public class GaussianTest_testSomeValues {

    private static final double ASSERTION_TOLERANCE = Math.ulp(1d);

    @Test
    public void testSomeValues() {
        final UnivariateFunction gaussian = new Gaussian();
        final double standardGaussianAtMean = 1 / JdkMath.sqrt(2 * Math.PI);

        Assert.assertEquals(standardGaussianAtMean, gaussian.value(0), ASSERTION_TOLERANCE);
    }
}
