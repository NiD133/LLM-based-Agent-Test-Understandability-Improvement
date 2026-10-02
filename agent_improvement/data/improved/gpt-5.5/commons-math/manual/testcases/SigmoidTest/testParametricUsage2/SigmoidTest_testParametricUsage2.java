package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.exception.DimensionMismatchException;
import org.junit.Test;

public class SigmoidTest_testParametricUsage2 {

    @Test(expected = DimensionMismatchException.class)
    public void testParametricUsage2() {
        final Sigmoid.Parametric sigmoid = new Sigmoid.Parametric();

        sigmoid.value(0, new double[] { 0 });
    }
}
