package org.apache.commons.math4.legacy.stat;

import org.junit.Assert;
import org.junit.Test;

public class FrequencyTest_testEmptyTable {

    /**
     * Verifies the values reported before any observations have been added.
     */
    @Test
    public void testEmptyTable() {
        Frequency<Integer> frequency = new Frequency<>();

        Assert.assertEquals("freq sum, empty table", 0, frequency.getSumFreq());
        Assert.assertEquals("count, empty table", 0, frequency.getCount(0));
        Assert.assertEquals("count, empty table", 0, frequency.getCount(Integer.valueOf(0)));
        Assert.assertEquals("cum freq, empty table", 0, frequency.getCumFreq(0));
        Assert.assertTrue("pct, empty table", Double.isNaN(frequency.getPct(0)));
        Assert.assertTrue("pct, empty table", Double.isNaN(frequency.getPct(Integer.valueOf(0))));
        Assert.assertTrue("cum pct, empty table", Double.isNaN(frequency.getCumPct(0)));
        Assert.assertTrue("cum pct, empty table", Double.isNaN(frequency.getCumPct(Integer.valueOf(0))));
    }
}
