package org.apache.commons.math4.legacy.stat;

import java.util.Iterator;
import org.junit.Assert;
import org.junit.Test;

public class FrequencyTest_testIntegerValues {

    /** The integer values exercised by this test. */
    private static final int VALUE_ONE = 1;
    private static final int VALUE_THREE = 3;

    /** Tolerance for comparing cumulative-percentage (floating point) results. */
    private static final double TOLERANCE = 10E-15d;

    @Test
    public void testIntegerValues() {
        // Build a frequency table holding two 1s and two 2s, mixing the
        // primitive int and boxed Integer overloads to show they are equivalent.
        Frequency<Integer> frequency = new Frequency<>();
        frequency.addValue(Integer.valueOf(1));
        frequency.addValue(1);
        frequency.addValue(2);
        frequency.addValue(Integer.valueOf(2));

        // The value 1 was added twice; both overloads of getCount agree.
        Assert.assertEquals("Integer 1 count", 2, frequency.getCount(1));
        Assert.assertEquals("Integer 1 count", 2, frequency.getCount(Integer.valueOf(1)));

        // 1 accounts for half of the four entries, so its cumulative percentage is 0.5.
        Assert.assertEquals("Integer 1 cumPct", 0.5, frequency.getCumPct(1), TOLERANCE);
        Assert.assertEquals("Integer 1 cumPct", 0.5, frequency.getCumPct(Integer.valueOf(1)), TOLERANCE);

        // Adjust the counts: remove both 1s and add five 3s.
        frequency.incrementValue(VALUE_ONE, -2);
        frequency.incrementValue(VALUE_THREE, 5);
        Assert.assertEquals("Integer 1 count", 0, frequency.getCount(1));
        Assert.assertEquals("Integer 3 count", 5, frequency.getCount(3));

        // Every value stored in the table must be an Integer.
        Iterator<?> values = frequency.valuesIterator();
        while (values.hasNext()) {
            Assert.assertTrue(values.next() instanceof Integer);
        }
    }
}
