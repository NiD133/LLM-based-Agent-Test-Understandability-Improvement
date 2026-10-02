package org.apache.commons.math4.legacy.analysis.interpolation;

import org.apache.commons.math4.legacy.exception.NoDataException;
import org.junit.Test;

/**
 * Verifies that {@link LoessInterpolator} rejects empty input.
 */
public class LoessInterpolatorTest_testEmptyData {

    /**
     * Smoothing empty x/y arrays leaves the interpolator with no data points,
     * so it must fail fast with a {@link NoDataException}.
     */
    @Test(expected = NoDataException.class)
    public void testEmptyData() {
        final double[] emptyXValues = new double[] {};
        final double[] emptyYValues = new double[] {};

        new LoessInterpolator().smooth(emptyXValues, emptyYValues);
    }
}
