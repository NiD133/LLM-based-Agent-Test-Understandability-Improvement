package org.apache.commons.math4.legacy.stat;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests that a freshly constructed {@link Frequency} table with no data
 * reports zero counts/frequencies and NaN percentages.
 */
public class FrequencyTest_testEmptyTable {

    @Test
    public void testEmptyTable() {
        Frequency<Integer> f = new Frequency<>();

        // An empty table has no observations, so the total count must be zero.
        Assert.assertEquals("freq sum, empty table", 0, f.getSumFreq());

        // Both primitive-int and boxed-Integer overloads should report zero counts.
        Assert.assertEquals("count (int), empty table",     0, f.getCount(0));
        Assert.assertEquals("count (Integer), empty table", 0, f.getCount(Integer.valueOf(0)));

        // Cumulative frequency is also zero when the table is empty.
        Assert.assertEquals("cum freq, empty table", 0, f.getCumFreq(0));

        // Percentages are undefined (NaN) when there are no observations to divide by.
        Assert.assertTrue("pct (int), empty table",        Double.isNaN(f.getPct(0)));
        Assert.assertTrue("pct (Integer), empty table",    Double.isNaN(f.getPct(Integer.valueOf(0))));
        Assert.assertTrue("cum pct (int), empty table",    Double.isNaN(f.getCumPct(0)));
        Assert.assertTrue("cum pct (Integer), empty table", Double.isNaN(f.getCumPct(Integer.valueOf(0))));
    }
}
