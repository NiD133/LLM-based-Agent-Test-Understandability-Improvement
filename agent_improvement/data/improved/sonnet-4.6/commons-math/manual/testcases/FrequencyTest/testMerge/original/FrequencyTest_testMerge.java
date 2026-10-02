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

public class FrequencyTest_testMerge {

    private static final long ONE_LONG = 1L;

    private static final long TWO_LONG = 2L;

    private static final long THREE_LONG = 3L;

    private static final int ONE = 1;

    private static final int TWO = 2;

    private static final int THREE = 3;

    private static final double TOLERANCE = 10E-15d;

    @Test
    public void testMerge() {
        Frequency<Long> f = new Frequency<>();
        Assert.assertEquals(0, f.getUniqueCount());
        f.addValue(ONE_LONG);
        f.addValue(TWO_LONG);
        f.addValue(ONE_LONG);
        f.addValue(TWO_LONG);
        Assert.assertEquals(2, f.getUniqueCount());
        Assert.assertEquals(2, f.getCount(ONE_LONG));
        Assert.assertEquals(2, f.getCount(TWO_LONG));
        Frequency<Long> g = new Frequency<>();
        g.addValue(ONE_LONG);
        g.addValue(THREE_LONG);
        g.addValue(THREE_LONG);
        Assert.assertEquals(2, g.getUniqueCount());
        Assert.assertEquals(1, g.getCount(ONE_LONG));
        Assert.assertEquals(2, g.getCount(THREE_LONG));
        f.merge(g);
        Assert.assertEquals(3, f.getUniqueCount());
        Assert.assertEquals(3, f.getCount(ONE_LONG));
        Assert.assertEquals(2, f.getCount(TWO_LONG));
        Assert.assertEquals(2, f.getCount(THREE_LONG));
    }
}
