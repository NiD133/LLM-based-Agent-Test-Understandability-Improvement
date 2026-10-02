package org.apache.commons.math4.legacy.stat;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class FrequencyTest_testModeFloatNan {

    @Test
    public void testModeFloatNan() {
        Frequency<Float> frequency = new Frequency<>();

        frequency.addValue(Float.valueOf(Float.NaN));
        frequency.addValue(Float.valueOf(Float.NaN));
        frequency.addValue(Float.valueOf(Float.NaN));
        frequency.addValue(Float.valueOf(Float.NEGATIVE_INFINITY));
        frequency.addValue(Float.valueOf(Float.POSITIVE_INFINITY));
        frequency.addValue(Float.valueOf(Float.NEGATIVE_INFINITY));
        frequency.addValue(Float.valueOf(Float.POSITIVE_INFINITY));
        frequency.addValue(Float.valueOf(Float.NEGATIVE_INFINITY));
        frequency.addValue(Float.valueOf(Float.POSITIVE_INFINITY));

        List<Float> mode = frequency.getMode();

        Assert.assertEquals(3, mode.size());
        Assert.assertEquals(Float.valueOf(Float.NEGATIVE_INFINITY), mode.get(0));
        Assert.assertEquals(Float.valueOf(Float.POSITIVE_INFINITY), mode.get(1));
        Assert.assertEquals(Float.valueOf(Float.NaN), mode.get(2));
    }
}
