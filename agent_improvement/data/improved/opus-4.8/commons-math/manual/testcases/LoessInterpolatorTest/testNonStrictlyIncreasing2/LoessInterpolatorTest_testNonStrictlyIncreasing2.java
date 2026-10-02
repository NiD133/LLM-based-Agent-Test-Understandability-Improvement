package org.apache.commons.math4.legacy.analysis.interpolation;

import org.apache.commons.math4.legacy.exception.NonMonotonicSequenceException;
import org.junit.Test;

public class LoessInterpolatorTest_testNonStrictlyIncreasing2 {

    /**
     * The abscissae passed to {@link LoessInterpolator#smooth} must be strictly
     * increasing. Here the third value (2) repeats the second value (2), so the
     * sequence is only non-strictly increasing and smoothing must be rejected
     * with a {@link NonMonotonicSequenceException}.
     */
    @Test(expected = NonMonotonicSequenceException.class)
    public void testNonStrictlyIncreasing2() {
        final double[] xValuesWithDuplicate = { 1, 2, 2, 3 };
        final double[] yValues = { 3, 4, 5, 6 };

        new LoessInterpolator().smooth(xValuesWithDuplicate, yValues);
    }
}
