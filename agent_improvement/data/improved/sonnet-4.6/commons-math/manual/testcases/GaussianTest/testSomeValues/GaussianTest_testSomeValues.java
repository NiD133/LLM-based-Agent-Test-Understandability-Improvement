package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.analysis.UnivariateFunction;
import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Assert;
import org.junit.Test;

public class GaussianTest_testSomeValues {

    // Machine epsilon: smallest representable difference from 1.0 (used as tolerance)
    private final double EPS = Math.ulp(1d);

    // The default Gaussian N(0,1) has peak value 1/sqrt(2*pi) at x=0
    private static final double STANDARD_GAUSSIAN_PEAK = 1 / JdkMath.sqrt(2 * Math.PI);

    @Test
    public void testSomeValues() {
        final UnivariateFunction f = new Gaussian();

        Assert.assertEquals(
            "Default Gaussian evaluated at x=0 should equal the standard normal peak 1/sqrt(2*pi)",
            STANDARD_GAUSSIAN_PEAK,
            f.value(0),
            EPS
        );
    }
}
