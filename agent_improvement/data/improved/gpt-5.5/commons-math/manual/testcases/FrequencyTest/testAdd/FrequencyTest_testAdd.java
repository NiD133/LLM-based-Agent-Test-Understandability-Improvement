package org.apache.commons.math4.legacy.stat;

import org.junit.Assert;
import org.junit.Test;

public class FrequencyTest_testAdd {

    private static final double TOLERANCE = 10E-15d;

    /**
     * Test adding comparable character values.
     */
    @Test
    public void testAdd() {
        Frequency<Character> f = new Frequency<>();
        final char aChar = 'a';
        final char bChar = 'b';

        f.addValue(aChar);
        f.addValue(bChar);

        Assert.assertEquals("a pct", 0.5, f.getPct(aChar), TOLERANCE);
        Assert.assertEquals("b cum pct", 1.0, f.getCumPct(bChar), TOLERANCE);
    }
}
