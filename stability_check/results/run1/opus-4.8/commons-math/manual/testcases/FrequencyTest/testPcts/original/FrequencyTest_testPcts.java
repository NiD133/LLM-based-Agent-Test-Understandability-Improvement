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

public class FrequencyTest_testPcts {

    private static final long ONE_LONG = 1L;

    private static final long TWO_LONG = 2L;

    private static final long THREE_LONG = 3L;

    private static final int ONE = 1;

    private static final int TWO = 2;

    private static final int THREE = 3;

    private static final double TOLERANCE = 10E-15d;

    /**
     * test pcts
     */
    @Test
    public void testPcts() {
        Frequency<Long> f = new Frequency<>();
        f.addValue(ONE_LONG);
        f.addValue(TWO_LONG);
        f.addValue(THREE_LONG);
        f.addValue(THREE_LONG);
        Assert.assertEquals("two pct", 0.25, f.getPct(Long.valueOf(2)), TOLERANCE);
        Assert.assertEquals("two cum pct", 0.50, f.getCumPct(Long.valueOf(2)), TOLERANCE);
        Assert.assertEquals("three cum pct", 1.0, f.getCumPct(THREE_LONG), TOLERANCE);
    }
}
