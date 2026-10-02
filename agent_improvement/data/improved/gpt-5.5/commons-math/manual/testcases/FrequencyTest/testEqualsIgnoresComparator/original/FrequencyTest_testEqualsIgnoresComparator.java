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

public class FrequencyTest_testEqualsIgnoresComparator {

    private static final long ONE_LONG = 1L;

    private static final long TWO_LONG = 2L;

    private static final long THREE_LONG = 3L;

    private static final int ONE = 1;

    private static final int TWO = 2;

    private static final int THREE = 3;

    private static final double TOLERANCE = 10E-15d;

    /**
     * The Frequency class ignores the comparator for equality.
     * See MATH-1689.
     */
    @Test
    public void testEqualsIgnoresComparator() {
        Frequency<Integer> ascending = new Frequency<>();
        Frequency<Integer> descending = new Frequency<Integer>(Comparator.reverseOrder());
        ascending.addValue(1);
        ascending.addValue(2);
        descending.addValue(1);
        descending.addValue(2);
        Assert.assertEquals(1, ascending.getCumFreq(1));
        Assert.assertEquals(2, descending.getCumFreq(1));
        Assert.assertEquals("[1, 2]", ascending.getMode().toString());
        Assert.assertEquals("[2, 1]", descending.getMode().toString());
        Assert.assertEquals(ascending, descending);
    }
}
