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

public class FrequencyTest_testToString {

    private static final long ONE_LONG = 1L;

    private static final long TWO_LONG = 2L;

    private static final long THREE_LONG = 3L;

    private static final int ONE = 1;

    private static final int TWO = 2;

    private static final int THREE = 3;

    private static final double TOLERANCE = 10E-15d;

    /**
     * Tests toString()
     */
    @Test
    public void testToString() throws Exception {
        Frequency<Long> f = new Frequency<>();
        f.addValue(ONE_LONG);
        f.addValue(TWO_LONG);
        String s = f.toString();
        //System.out.println(s);
        Assert.assertNotNull(s);
        BufferedReader reader = new BufferedReader(new StringReader(s));
        // header line
        String line = reader.readLine();
        Assert.assertNotNull(line);
        // one's or two's line
        line = reader.readLine();
        Assert.assertNotNull(line);
    }
}
