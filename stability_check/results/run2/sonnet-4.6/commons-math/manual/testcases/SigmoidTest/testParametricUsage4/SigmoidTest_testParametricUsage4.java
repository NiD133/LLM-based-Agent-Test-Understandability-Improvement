package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.exception.DimensionMismatchException;
import org.junit.Test;

public class SigmoidTest_testParametricUsage4 {

    // Sigmoid.Parametric requires exactly 2 parameters: [lo, hi].
    // Passing only 1 parameter must throw DimensionMismatchException.
    @Test(expected = DimensionMismatchException.class)
    public void testParametricUsage4() {
        final Sigmoid.Parametric sigmoidParametric = new Sigmoid.Parametric();
        sigmoidParametric.gradient(0, new double[] { 0 });
    }
}
