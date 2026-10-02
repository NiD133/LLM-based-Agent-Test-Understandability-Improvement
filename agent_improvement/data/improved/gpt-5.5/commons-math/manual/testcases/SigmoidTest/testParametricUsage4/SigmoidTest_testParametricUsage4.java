package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.exception.DimensionMismatchException;
import org.junit.Test;

public class SigmoidTest_testParametricUsage4 {

    @Test(expected = DimensionMismatchException.class)
    public void gradientWithTooFewParametersThrowsDimensionMismatchException() {
        final Sigmoid.Parametric sigmoid = new Sigmoid.Parametric();

        sigmoid.gradient(0, new double[] { 0 });
    }
}
