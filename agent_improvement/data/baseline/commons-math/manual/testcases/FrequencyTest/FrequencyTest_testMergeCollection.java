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

public class FrequencyTest_testMergeCollection {

    private static final long ONE_LONG = 1L;

    private static final long TWO_LONG = 2L;

    private static final long THREE_LONG = 3L;

    private static final int ONE = 1;

    private static final int TWO = 2;

    private static final int THREE = 3;

    private static final double TOLERANCE = 10E-15d;

    @Test
    public void testMergeCollection() {
        Frequency<Long> f = new Frequency<>();
        Assert.assertEquals(0, f.getUniqueCount());
        f.addValue(ONE_LONG);
        Assert.assertEquals(1, f.getUniqueCount());
        Assert.assertEquals(1, f.getCount(ONE_LONG));
        Assert.assertEquals(0, f.getCount(TWO_LONG));
        Frequency<Long> g = new Frequency<Long>();
        g.addValue(TWO_LONG);
        Frequency<Long> h = new Frequency<Long>();
        h.addValue(THREE_LONG);
        List<Frequency<Long>> coll = new ArrayList<>();
        coll.add(g);
        coll.add(h);
        f.merge(coll);
        Assert.assertEquals(3, f.getUniqueCount());
        Assert.assertEquals(1, f.getCount(ONE_LONG));
        Assert.assertEquals(1, f.getCount(TWO_LONG));
        Assert.assertEquals(1, f.getCount(THREE_LONG));
    }
}
