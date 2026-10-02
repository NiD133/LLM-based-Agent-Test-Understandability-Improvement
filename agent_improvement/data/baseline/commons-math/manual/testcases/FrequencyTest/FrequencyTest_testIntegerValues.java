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

public class FrequencyTest_testIntegerValues {

    private static final long ONE_LONG = 1L;

    private static final long TWO_LONG = 2L;

    private static final long THREE_LONG = 3L;

    private static final int ONE = 1;

    private static final int TWO = 2;

    private static final int THREE = 3;

    private static final double TOLERANCE = 10E-15d;

    @Test
    public void testIntegerValues() {
        Frequency<Integer> f = new Frequency<>();
        f.addValue(Integer.valueOf(1));
        f.addValue(1);
        f.addValue(2);
        f.addValue(Integer.valueOf(2));
        Assert.assertEquals("Integer 1 count", 2, f.getCount(1));
        Assert.assertEquals("Integer 1 count", 2, f.getCount(Integer.valueOf(1)));
        Assert.assertEquals("Integer 1 cumPct", 0.5, f.getCumPct(1), TOLERANCE);
        Assert.assertEquals("Integer 1 cumPct", 0.5, f.getCumPct(Integer.valueOf(1)), TOLERANCE);
        f.incrementValue(ONE, -2);
        f.incrementValue(THREE, 5);
        Assert.assertEquals("Integer 1 count", 0, f.getCount(1));
        Assert.assertEquals("Integer 3 count", 5, f.getCount(3));
        Iterator<?> it = f.valuesIterator();
        while (it.hasNext()) {
            Assert.assertTrue(it.next() instanceof Integer);
        }
    }
}
