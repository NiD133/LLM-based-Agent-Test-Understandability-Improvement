package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.exception.DimensionMismatchException;
import org.junit.Test;

public class GaussianTest_testParametricUsage5 {

    @Test(expected = DimensionMismatchException.class)
    public void testParametricUsage5() {
        final Gaussian.Parametric gaussian = new Gaussian.Parametric();
        final double[] missingMeanAndSigmaParameters = { 0 };

        gaussian.gradient(0, missingMeanAndSigmaParameters);
    }
}
