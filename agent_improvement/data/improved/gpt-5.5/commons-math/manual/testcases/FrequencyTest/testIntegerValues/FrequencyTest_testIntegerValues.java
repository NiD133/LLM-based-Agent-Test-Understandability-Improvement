package org.apache.commons.math4.legacy.stat;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

public class FrequencyTest_testIntegerValues {

    private static final int ONE = 1;
    private static final int THREE = 3;
    private static final double TOLERANCE = 10E-15d;

    @Test
    public void testIntegerValues() {
        Frequency<Integer> frequency = new Frequency<>();

        frequency.addValue(Integer.valueOf(1));
        frequency.addValue(1);
        frequency.addValue(2);
        frequency.addValue(Integer.valueOf(2));

        Assert.assertEquals("Integer 1 count", 2, frequency.getCount(1));
        Assert.assertEquals("Integer 1 count", 2, frequency.getCount(Integer.valueOf(1)));
        Assert.assertEquals("Integer 1 cumPct", 0.5, frequency.getCumPct(1), TOLERANCE);
        Assert.assertEquals("Integer 1 cumPct", 0.5, frequency.getCumPct(Integer.valueOf(1)), TOLERANCE);

        frequency.incrementValue(ONE, -2);
        frequency.incrementValue(THREE, 5);

        Assert.assertEquals("Integer 1 count", 0, frequency.getCount(1));
        Assert.assertEquals("Integer 3 count", 5, frequency.getCount(3));

        Iterator<?> values = frequency.valuesIterator();
        while (values.hasNext()) {
            Assert.assertTrue(values.next() instanceof Integer);
        }
    }
}
