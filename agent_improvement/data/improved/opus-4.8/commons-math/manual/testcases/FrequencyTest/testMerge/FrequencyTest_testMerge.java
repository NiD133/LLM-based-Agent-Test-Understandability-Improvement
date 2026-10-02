package org.apache.commons.math4.legacy.stat;

import org.junit.Assert;
import org.junit.Test;

public class FrequencyTest_testMerge {

    private static final long VALUE_1 = 1L;

    private static final long VALUE_2 = 2L;

    private static final long VALUE_3 = 3L;

    /**
     * Verifies that {@link Frequency#merge(Frequency)} combines the counts of two
     * frequency tables, summing the counts of values that appear in both.
     */
    @Test
    public void testMerge() {
        // Table 'first' records: value 1 twice, value 2 twice.
        Frequency<Long> first = new Frequency<>();
        Assert.assertEquals(0, first.getUniqueCount());
        first.addValue(VALUE_1);
        first.addValue(VALUE_2);
        first.addValue(VALUE_1);
        first.addValue(VALUE_2);
        Assert.assertEquals(2, first.getUniqueCount());
        Assert.assertEquals(2, first.getCount(VALUE_1));
        Assert.assertEquals(2, first.getCount(VALUE_2));

        // Table 'second' records: value 1 once, value 3 twice.
        Frequency<Long> second = new Frequency<>();
        second.addValue(VALUE_1);
        second.addValue(VALUE_3);
        second.addValue(VALUE_3);
        Assert.assertEquals(2, second.getUniqueCount());
        Assert.assertEquals(1, second.getCount(VALUE_1));
        Assert.assertEquals(2, second.getCount(VALUE_3));

        // Merging 'second' into 'first' adds the counts together.
        first.merge(second);
        Assert.assertEquals(3, first.getUniqueCount());
        Assert.assertEquals(3, first.getCount(VALUE_1)); // 2 + 1
        Assert.assertEquals(2, first.getCount(VALUE_2)); // 2 + 0
        Assert.assertEquals(2, first.getCount(VALUE_3)); // 0 + 2
    }
}
