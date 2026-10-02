package org.apache.commons.math4.legacy.stat;

import java.util.Comparator;
import org.junit.Assert;
import org.junit.Test;

public class FrequencyTest_testEqualsIgnoresComparator {

    /**
     * Two {@link Frequency} instances that hold the same values are considered
     * equal even when they use different comparators. The comparator only
     * affects ordering (e.g. cumulative frequency and mode), not equality.
     * See MATH-1689.
     */
    @Test
    public void testEqualsIgnoresComparator() {
        // Same values, but opposite iteration orders.
        Frequency<Integer> ascending = new Frequency<>();
        Frequency<Integer> descending = new Frequency<Integer>(Comparator.reverseOrder());
        ascending.addValue(1);
        ascending.addValue(2);
        descending.addValue(1);
        descending.addValue(2);

        // The comparator changes order-dependent results...
        Assert.assertEquals("ascending: only value 1 is <= 1", 1, ascending.getCumFreq(1));
        Assert.assertEquals("descending: both values are >= 1", 2, descending.getCumFreq(1));
        Assert.assertEquals("[1, 2]", ascending.getMode().toString());
        Assert.assertEquals("[2, 1]", descending.getMode().toString());

        // ...but equality ignores the comparator: same contents means equal.
        Assert.assertEquals(ascending, descending);
    }
}
