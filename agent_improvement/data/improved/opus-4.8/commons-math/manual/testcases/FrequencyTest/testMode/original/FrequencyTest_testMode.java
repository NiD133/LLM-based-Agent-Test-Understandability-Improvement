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

public class FrequencyTest_testMode {

    private static final long ONE_LONG = 1L;

    private static final long TWO_LONG = 2L;

    private static final long THREE_LONG = 3L;

    private static final int ONE = 1;

    private static final int TWO = 2;

    private static final int THREE = 3;

    private static final double TOLERANCE = 10E-15d;

    @Test
    public void testMode() {
        Frequency<String> f = new Frequency<>();
        List<String> mode;
        mode = f.getMode();
        Assert.assertEquals(0, mode.size());
        f.addValue("3");
        mode = f.getMode();
        Assert.assertEquals(1, mode.size());
        Assert.assertEquals("3", mode.get(0));
        f.addValue("2");
        mode = f.getMode();
        Assert.assertEquals(2, mode.size());
        Assert.assertEquals("2", mode.get(0));
        Assert.assertEquals("3", mode.get(1));
        f.addValue("2");
        mode = f.getMode();
        Assert.assertEquals(1, mode.size());
        Assert.assertEquals("2", mode.get(0));
        Assert.assertFalse(mode.contains("1"));
        Assert.assertTrue(mode.contains("2"));
    }
}
