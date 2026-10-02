package org.apache.commons.math4.legacy.stat;

import org.junit.Assert;
import org.junit.Test;

/**
 * Verifies that a freshly created (empty) {@link Frequency} table reports
 * sensible "no data" results for every query method.
 */
public class FrequencyTest_testEmptyTable {

    /** Value queried throughout the test; it is never added to the table. */
    private static final int ABSENT_VALUE = 0;

    @Test
    public void testEmptyTable() {
        Frequency<Integer> emptyTable = new Frequency<>();

        // Counts are zero because nothing has been recorded yet.
        Assert.assertEquals("freq sum, empty table", 0, emptyTable.getSumFreq());
        Assert.assertEquals("count, empty table", 0, emptyTable.getCount(ABSENT_VALUE));
        Assert.assertEquals("count, empty table", 0, emptyTable.getCount(Integer.valueOf(ABSENT_VALUE)));
        Assert.assertEquals("cum freq, empty table", 0, emptyTable.getCumFreq(ABSENT_VALUE));

        // Percentages are undefined (NaN) because there is no total to divide by.
        Assert.assertTrue("pct, empty table", Double.isNaN(emptyTable.getPct(ABSENT_VALUE)));
        Assert.assertTrue("pct, empty table", Double.isNaN(emptyTable.getPct(Integer.valueOf(ABSENT_VALUE))));
        Assert.assertTrue("cum pct, empty table", Double.isNaN(emptyTable.getCumPct(ABSENT_VALUE)));
        Assert.assertTrue("cum pct, empty table", Double.isNaN(emptyTable.getCumPct(Integer.valueOf(ABSENT_VALUE))));
    }
}
