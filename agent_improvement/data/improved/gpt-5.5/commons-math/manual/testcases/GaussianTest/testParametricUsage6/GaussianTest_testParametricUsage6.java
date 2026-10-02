package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.exception.NotStrictlyPositiveException;
import org.junit.Test;

public class GaussianTest_testParametricUsage6 {

    private static final double EVALUATION_POINT = 0.0;
    private static final double NORM = 0.0;
    private static final double MEAN = 1.0;
    private static final double ZERO_SIGMA = 0.0;

    @Test(expected = NotStrictlyPositiveException.class)
    public void testParametricUsage6() {
        final Gaussian.Parametric gaussian = new Gaussian.Parametric();
        gaussian.gradient(EVALUATION_POINT, new double[] { NORM, MEAN, ZERO_SIGMA });
    }
}
