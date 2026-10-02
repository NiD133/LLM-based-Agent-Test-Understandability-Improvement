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

public class FrequencyTest_testAdd {

    private static final long ONE_LONG = 1L;

    private static final long TWO_LONG = 2L;

    private static final long THREE_LONG = 3L;

    private static final int ONE = 1;

    private static final int TWO = 2;

    private static final int THREE = 3;

    private static final double TOLERANCE = 10E-15d;

    /**
     * test adding incomparable values
     */
    @Test
    public void testAdd() {
        Frequency<Character> f = new Frequency<>();
        char aChar = 'a';
        char bChar = 'b';
        f.addValue(aChar);
        f.addValue(bChar);
        Assert.assertEquals("a pct", 0.5, f.getPct(aChar), TOLERANCE);
        Assert.assertEquals("b cum pct", 1.0, f.getCumPct(bChar), TOLERANCE);
    }
}
