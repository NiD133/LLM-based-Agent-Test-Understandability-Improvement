package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.exception.DimensionMismatchException;
import org.junit.Test;

public class SigmoidTest_testParametricUsage2 {

    /**
     * Sigmoid.Parametric.value() requires exactly 2 parameters (lo and hi).
     * Passing only 1 parameter must throw DimensionMismatchException.
     */
    @Test(expected = DimensionMismatchException.class)
    public void testParametricUsage2() {
        final Sigmoid.Parametric g = new Sigmoid.Parametric();
        g.value(0, new double[] { 0 }); // only 1 param supplied instead of 2
    }
}
