package org.apache.commons.math4.legacy.stat;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class FrequencyTest_testModeDoubleNan {

    @Test
    public void testModeDoubleNan() {
        Frequency<Double> frequency = new Frequency<>();

        frequency.addValue(Double.valueOf(Double.NaN));
        frequency.addValue(Double.valueOf(Double.NaN));
        frequency.addValue(Double.valueOf(Double.NaN));
        frequency.addValue(Double.valueOf(Double.NEGATIVE_INFINITY));
        frequency.addValue(Double.valueOf(Double.POSITIVE_INFINITY));
        frequency.addValue(Double.valueOf(Double.NEGATIVE_INFINITY));
        frequency.addValue(Double.valueOf(Double.POSITIVE_INFINITY));
        frequency.addValue(Double.valueOf(Double.NEGATIVE_INFINITY));
        frequency.addValue(Double.valueOf(Double.POSITIVE_INFINITY));

        List<Double> modes = frequency.getMode();

        Assert.assertEquals(3, modes.size());
        Assert.assertEquals(Double.valueOf(Double.NEGATIVE_INFINITY), modes.get(0));
        Assert.assertEquals(Double.valueOf(Double.POSITIVE_INFINITY), modes.get(1));
        Assert.assertEquals(Double.valueOf(Double.NaN), modes.get(2));
    }
}
