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

public class FrequencyTest_testModeFloatNan {

    private static final long ONE_LONG = 1L;

    private static final long TWO_LONG = 2L;

    private static final long THREE_LONG = 3L;

    private static final int ONE = 1;

    private static final int TWO = 2;

    private static final int THREE = 3;

    private static final double TOLERANCE = 10E-15d;

    @Test
    public void testModeFloatNan() {
        Frequency<Float> f = new Frequency<>();
        List<Float> mode;
        f.addValue(Float.valueOf(Float.NaN));
        f.addValue(Float.valueOf(Float.NaN));
        f.addValue(Float.valueOf(Float.NaN));
        f.addValue(Float.valueOf(Float.NEGATIVE_INFINITY));
        f.addValue(Float.valueOf(Float.POSITIVE_INFINITY));
        f.addValue(Float.valueOf(Float.NEGATIVE_INFINITY));
        f.addValue(Float.valueOf(Float.POSITIVE_INFINITY));
        f.addValue(Float.valueOf(Float.NEGATIVE_INFINITY));
        f.addValue(Float.valueOf(Float.POSITIVE_INFINITY));
        mode = f.getMode();
        Assert.assertEquals(3, mode.size());
        Assert.assertEquals(Float.valueOf(Float.NEGATIVE_INFINITY), mode.get(0));
        Assert.assertEquals(Float.valueOf(Float.POSITIVE_INFINITY), mode.get(1));
        Assert.assertEquals(Float.valueOf(Float.NaN), mode.get(2));
    }
}
