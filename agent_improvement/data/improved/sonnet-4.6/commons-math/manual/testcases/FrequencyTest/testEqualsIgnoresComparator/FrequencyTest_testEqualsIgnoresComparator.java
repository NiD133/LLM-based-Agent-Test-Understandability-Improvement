package org.apache.commons.math4.legacy.stat;

import java.util.Comparator;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests that {@link Frequency#equals} considers two Frequency objects equal
 * when they contain the same data, regardless of the comparator used for
 * internal ordering. Covers MATH-1689.
 */
public class FrequencyTest_testEqualsIgnoresComparator {

    @Test
    public void testEqualsIgnoresComparator() {
        // Arrange: two Frequency tables with identical data but opposite sort orders
        Frequency<Integer> ascending = new Frequency<>();
        Frequency<Integer> descending = new Frequency<Integer>(Comparator.reverseOrder());

        ascending.addValue(1);
        ascending.addValue(2);
        descending.addValue(1);
        descending.addValue(2);

        // The comparator affects ordering-sensitive operations:
        // ascending keeps [1, 2] order, so cumFreq(1) counts only value 1 → 1
        Assert.assertEquals(1, ascending.getCumFreq(1));
        // descending keeps [2, 1] order, so cumFreq(1) counts both values up to 1 → 2
        Assert.assertEquals(2, descending.getCumFreq(1));

        // Mode reflects the internal ordering of each table
        Assert.assertEquals("[1, 2]", ascending.getMode().toString());
        Assert.assertEquals("[2, 1]", descending.getMode().toString());

        // Despite the different orderings, equals() must treat both as the same distribution
        Assert.assertEquals(ascending, descending);
    }
}
