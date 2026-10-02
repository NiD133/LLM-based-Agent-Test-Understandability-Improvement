package org.apache.commons.math4.legacy.analysis.interpolation;

import org.apache.commons.math4.legacy.exception.NoDataException;
import org.junit.Test;

public class LoessInterpolatorTest_testEmptyData {

    @Test(expected = NoDataException.class)
    public void testEmptyData() {
        new LoessInterpolator().smooth(new double[] {}, new double[] {});
    }
}
