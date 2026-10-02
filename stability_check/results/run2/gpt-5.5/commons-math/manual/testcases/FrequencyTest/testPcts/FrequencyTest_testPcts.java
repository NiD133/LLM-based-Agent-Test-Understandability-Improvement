package org.apache.commons.math4.legacy.stat;

import org.junit.Assert;
import org.junit.Test;

public class FrequencyTest_testPcts {

    private static final long ONE_LONG = 1L;
    private static final long TWO_LONG = 2L;
    private static final long THREE_LONG = 3L;
    private static final double TOLERANCE = 10E-15d;

    @Test
    public void testPcts() {
        Frequency<Long> frequency = new Frequency<>();

        frequency.addValue(ONE_LONG);
        frequency.addValue(TWO_LONG);
        frequency.addValue(THREE_LONG);
        frequency.addValue(THREE_LONG);

        Assert.assertEquals("two pct", 0.25, frequency.getPct(Long.valueOf(2)), TOLERANCE);
        Assert.assertEquals("two cum pct", 0.50, frequency.getCumPct(Long.valueOf(2)), TOLERANCE);
        Assert.assertEquals("three cum pct", 1.0, frequency.getCumPct(THREE_LONG), TOLERANCE);
    }
}
