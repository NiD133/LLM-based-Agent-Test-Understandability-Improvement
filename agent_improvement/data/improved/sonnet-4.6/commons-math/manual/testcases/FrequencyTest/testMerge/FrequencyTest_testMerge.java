package org.apache.commons.math4.legacy.stat;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests that merging two Frequency tables combines their counts correctly.
 *
 * Scenario: frequency table f tracks occurrences of 1 and 2; frequency table g
 * tracks occurrences of 1 and 3. After merging g into f, f should reflect the
 * combined counts: 1 appears 3 times, 2 appears 2 times, 3 appears 2 times.
 */
public class FrequencyTest_testMerge {

    private static final long ONE   = 1L;
    private static final long TWO   = 2L;
    private static final long THREE = 3L;

    @Test
    public void testMerge() {
        // Build the first frequency table with values: 1, 2, 1, 2
        Frequency<Long> f = new Frequency<>();
        Assert.assertEquals("Empty table should have no unique values", 0, f.getUniqueCount());

        f.addValue(ONE);
        f.addValue(TWO);
        f.addValue(ONE);
        f.addValue(TWO);

        Assert.assertEquals("f should have 2 distinct values (1 and 2)", 2, f.getUniqueCount());
        Assert.assertEquals("1 was added twice to f", 2, f.getCount(ONE));
        Assert.assertEquals("2 was added twice to f", 2, f.getCount(TWO));

        // Build the second frequency table with values: 1, 3, 3
        Frequency<Long> g = new Frequency<>();
        g.addValue(ONE);
        g.addValue(THREE);
        g.addValue(THREE);

        Assert.assertEquals("g should have 2 distinct values (1 and 3)", 2, g.getUniqueCount());
        Assert.assertEquals("1 was added once to g", 1, g.getCount(ONE));
        Assert.assertEquals("3 was added twice to g", 2, g.getCount(THREE));

        // Merge g into f; counts for each value should be summed
        f.merge(g);

        Assert.assertEquals("After merge, f should contain 3 distinct values (1, 2, 3)", 3, f.getUniqueCount());
        Assert.assertEquals("1: 2 (from f) + 1 (from g) = 3", 3, f.getCount(ONE));
        Assert.assertEquals("2: 2 (from f) + 0 (from g) = 2", 2, f.getCount(TWO));
        Assert.assertEquals("3: 0 (from f) + 2 (from g) = 2", 2, f.getCount(THREE));
    }
}
