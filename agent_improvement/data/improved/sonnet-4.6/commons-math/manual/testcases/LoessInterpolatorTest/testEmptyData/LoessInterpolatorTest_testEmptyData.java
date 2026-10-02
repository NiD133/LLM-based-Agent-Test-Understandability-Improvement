package org.apache.commons.math4.legacy.analysis.interpolation;

import org.apache.commons.math4.legacy.exception.NoDataException;
import org.junit.Test;

/**
 * Tests that LoessInterpolator rejects empty input arrays.
 */
public class LoessInterpolatorTest_testEmptyData {

    @Test(expected = NoDataException.class)
    public void testEmptyData() {
        // Smoothing with no data points should throw NoDataException
        new LoessInterpolator().smooth(new double[] {}, new double[] {});
    }
}
