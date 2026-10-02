package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.exception.DimensionMismatchException;
import org.junit.Test;

public class GaussianTest_testParametricUsage2 {

    /**
     * Gaussian.Parametric.value() requires exactly 3 parameters: norm, mean, and sigma.
     * Passing an array with only one element must throw DimensionMismatchException.
     */
    @Test(expected = DimensionMismatchException.class)
    public void testParametricUsage2() {
        final Gaussian.Parametric g = new Gaussian.Parametric();
        g.value(0, new double[] { 0 });
    }
}
