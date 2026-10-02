package org.apache.commons.math4.legacy.analysis.interpolation;

import org.apache.commons.math4.legacy.exception.NonMonotonicSequenceException;
import org.junit.Test;

public class LoessInterpolatorTest_testNonStrictlyIncreasing1 {

    @Test(expected = NonMonotonicSequenceException.class)
    public void testNonStrictlyIncreasing1() {
        double[] nonIncreasingAbscissae = { 4, 3, 1, 2 };
        double[] ordinates = { 3, 4, 5, 6 };

        new LoessInterpolator().smooth(nonIncreasingAbscissae, ordinates);
    }
}
