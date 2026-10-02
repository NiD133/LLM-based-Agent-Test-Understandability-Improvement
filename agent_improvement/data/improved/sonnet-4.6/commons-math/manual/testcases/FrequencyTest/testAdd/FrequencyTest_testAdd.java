package org.apache.commons.math4.legacy.stat;

import org.junit.Assert;
import org.junit.Test;

public class FrequencyTest_testAdd {

    private static final double TOLERANCE = 10E-15d;

    /**
     * Tests that addValue correctly tracks frequencies for char values.
     * With exactly two distinct values added once each, each should have
     * a relative frequency of 50% and the last value should have a
     * cumulative percentage of 100%.
     */
    @Test
    public void testAdd() {
        Frequency<Character> f = new Frequency<>();
        char aChar = 'a';
        char bChar = 'b';

        f.addValue(aChar);
        f.addValue(bChar);

        Assert.assertEquals("a pct", 0.5, f.getPct(aChar), TOLERANCE);
        Assert.assertEquals("b cum pct", 1.0, f.getCumPct(bChar), TOLERANCE);
    }
}
