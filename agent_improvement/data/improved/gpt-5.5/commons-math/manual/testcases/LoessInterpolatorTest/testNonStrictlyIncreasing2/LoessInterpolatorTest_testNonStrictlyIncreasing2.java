package org.apache.commons.math4.legacy.analysis.interpolation;

import org.apache.commons.math4.legacy.exception.NonMonotonicSequenceException;
import org.junit.Test;

public class LoessInterpolatorTest_testNonStrictlyIncreasing2 {

    @Test(expected = NonMonotonicSequenceException.class)
    public void testNonStrictlyIncreasing2() {
        double[] xValuesWithDuplicateEntry = { 1, 2, 2, 3 };
        double[] yValues = { 3, 4, 5, 6 };

        new LoessInterpolator().smooth(xValuesWithDuplicateEntry, yValues);
    }
}
