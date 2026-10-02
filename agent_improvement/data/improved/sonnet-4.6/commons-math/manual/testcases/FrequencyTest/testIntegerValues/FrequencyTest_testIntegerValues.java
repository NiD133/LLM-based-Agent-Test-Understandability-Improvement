package org.apache.commons.math4.legacy.stat;

import java.util.Iterator;
import org.junit.Assert;
import org.junit.Test;

public class FrequencyTest_testIntegerValues {

    private static final int ONE   = 1;
    private static final int THREE = 3;

    private static final double TOLERANCE = 10E-15d;

    @Test
    public void testIntegerValues() {
        Frequency<Integer> frequency = new Frequency<>();

        // Phase 1: add two counts of 1 (once via autoboxing, once via Integer.valueOf)
        //          and two counts of 2, so the distribution is {1->2, 2->2}.
        frequency.addValue(Integer.valueOf(1));
        frequency.addValue(1);
        frequency.addValue(2);
        frequency.addValue(Integer.valueOf(2));

        // Both lookup forms (primitive int and Integer) must return the same count.
        Assert.assertEquals("count of 1 via primitive int",  2, frequency.getCount(1));
        Assert.assertEquals("count of 1 via Integer.valueOf", 2, frequency.getCount(Integer.valueOf(1)));

        // 1 is the lowest value, so its cumulative percentage covers exactly half the observations.
        Assert.assertEquals("cumPct of 1 via primitive int",   0.5, frequency.getCumPct(1),                TOLERANCE);
        Assert.assertEquals("cumPct of 1 via Integer.valueOf", 0.5, frequency.getCumPct(Integer.valueOf(1)), TOLERANCE);

        // Phase 2: adjust counts — remove both counts of 1 and add 5 counts of 3,
        //          leaving the distribution as {2->2, 3->5}.
        frequency.incrementValue(ONE,   -2);
        frequency.incrementValue(THREE,  5);

        Assert.assertEquals("count of 1 after decrement to zero", 0, frequency.getCount(1));
        Assert.assertEquals("count of 3 after increment by five", 5, frequency.getCount(3));

        // Phase 3: confirm that every key stored in the frequency table is an Integer.
        Iterator<?> it = frequency.valuesIterator();
        while (it.hasNext()) {
            Assert.assertTrue("value returned by valuesIterator should be an Integer",
                              it.next() instanceof Integer);
        }
    }
}
