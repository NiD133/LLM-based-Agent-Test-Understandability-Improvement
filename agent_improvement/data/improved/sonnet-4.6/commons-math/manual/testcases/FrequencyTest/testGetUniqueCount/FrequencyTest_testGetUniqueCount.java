package org.apache.commons.math4.legacy.stat;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests {@link Frequency#getUniqueCount()}, which returns the number of distinct
 * values that have been added to the frequency distribution.
 */
public class FrequencyTest_testGetUniqueCount {

    @Test
    public void testGetUniqueCount() {
        Frequency<Long> freq = new Frequency<>();

        // Initially no values have been added, so there are no unique values
        Assert.assertEquals(0, freq.getUniqueCount());

        // Adding the first distinct value should increase unique count to 1
        freq.addValue(1L);
        Assert.assertEquals(1, freq.getUniqueCount());

        // Adding the same value again should NOT increase unique count
        freq.addValue(1L);
        Assert.assertEquals(1, freq.getUniqueCount());

        // Adding a second distinct value should increase unique count to 2
        freq.addValue(2L);
        Assert.assertEquals(2, freq.getUniqueCount());
    }
}
