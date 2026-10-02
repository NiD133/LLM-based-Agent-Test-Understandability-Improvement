package org.apache.commons.math4.legacy.analysis.interpolation;

import org.apache.commons.math4.legacy.exception.DimensionMismatchException;
import org.junit.Test;

public class LoessInterpolatorTest_testUnequalSizeArguments {

    @Test(expected = DimensionMismatchException.class)
    public void testUnequalSizeArguments() {
        double[] xValues = { 1, 2, 3 };
        double[] yValues = { 1, 2, 3, 4 };

        new LoessInterpolator().smooth(xValues, yValues);
    }
}
