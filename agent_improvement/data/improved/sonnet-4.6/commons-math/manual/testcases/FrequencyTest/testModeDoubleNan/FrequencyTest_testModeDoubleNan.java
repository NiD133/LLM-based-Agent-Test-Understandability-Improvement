package org.apache.commons.math4.legacy.stat;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class FrequencyTest_testModeDoubleNan {

    @Test
    public void testModeDoubleNan() {
        // NaN appears 3 times; NEGATIVE_INFINITY and POSITIVE_INFINITY each appear 3 times.
        // getMode() should return the three tied-most-frequent values in natural order:
        // NEGATIVE_INFINITY, POSITIVE_INFINITY, NaN (NaN sorts last by Double's comparator).
        Frequency<Double> frequency = new Frequency<>();

        frequency.addValue(Double.NaN);
        frequency.addValue(Double.NaN);
        frequency.addValue(Double.NaN);
        frequency.addValue(Double.NEGATIVE_INFINITY);
        frequency.addValue(Double.POSITIVE_INFINITY);
        frequency.addValue(Double.NEGATIVE_INFINITY);
        frequency.addValue(Double.POSITIVE_INFINITY);
        frequency.addValue(Double.NEGATIVE_INFINITY);
        frequency.addValue(Double.POSITIVE_INFINITY);

        List<Double> mode = frequency.getMode();

        Assert.assertEquals(3, mode.size());
        Assert.assertEquals(Double.valueOf(Double.NEGATIVE_INFINITY), mode.get(0));
        Assert.assertEquals(Double.valueOf(Double.POSITIVE_INFINITY), mode.get(1));
        Assert.assertEquals(Double.valueOf(Double.NaN), mode.get(2));
    }
}
