package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.exception.DimensionMismatchException;
import org.junit.Test;

public class GaussianTest_testParametricUsage2 {

    @Test(expected = DimensionMismatchException.class)
    public void testParametricUsage2() {
        final Gaussian.Parametric gaussian = new Gaussian.Parametric();
        final double x = 0;
        final double[] missingStandardDeviation = { 0 };

        gaussian.value(x, missingStandardDeviation);
    }
}
