package org.apache.commons.math4.legacy.stat;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.math4.legacy.TestUtils;
import org.junit.Assert;
import org.junit.Test;

public class FrequencyTest_testEmptyTable {

    private static final long ONE_LONG = 1L;

    private static final long TWO_LONG = 2L;

    private static final long THREE_LONG = 3L;

    private static final int ONE = 1;

    private static final int TWO = 2;

    private static final int THREE = 3;

    private static final double TOLERANCE = 10E-15d;

    /**
     * test empty table
     */
    @Test
    public void testEmptyTable() {
        Frequency<Integer> f = new Frequency<>();
        Assert.assertEquals("freq sum, empty table", 0, f.getSumFreq());
        Assert.assertEquals("count, empty table", 0, f.getCount(0));
        Assert.assertEquals("count, empty table", 0, f.getCount(Integer.valueOf(0)));
        Assert.assertEquals("cum freq, empty table", 0, f.getCumFreq(0));
        Assert.assertTrue("pct, empty table", Double.isNaN(f.getPct(0)));
        Assert.assertTrue("pct, empty table", Double.isNaN(f.getPct(Integer.valueOf(0))));
        Assert.assertTrue("cum pct, empty table", Double.isNaN(f.getCumPct(0)));
        Assert.assertTrue("cum pct, empty table", Double.isNaN(f.getCumPct(Integer.valueOf(0))));
    }
}
