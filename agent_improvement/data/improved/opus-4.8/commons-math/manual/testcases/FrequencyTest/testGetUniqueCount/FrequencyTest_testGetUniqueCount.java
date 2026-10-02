package org.apache.commons.math4.legacy.stat;

import org.junit.Assert;
import org.junit.Test;

/**
 * Verifies that {@link Frequency#getUniqueCount()} reports the number of
 * distinct values that have been added, ignoring duplicates.
 */
public class FrequencyTest_testGetUniqueCount {

    /** Two distinct sample values used to exercise the unique-count logic. */
    private static final long FIRST_VALUE = 1L;
    private static final long SECOND_VALUE = 2L;

    @Test
    public void testGetUniqueCount() {
        Frequency<Long> frequency = new Frequency<>();

        // An empty frequency table contains no unique values.
        Assert.assertEquals(0, frequency.getUniqueCount());

        // Adding a value for the first time increases the unique count.
        frequency.addValue(FIRST_VALUE);
        Assert.assertEquals(1, frequency.getUniqueCount());

        // Re-adding the same value does not change the unique count.
        frequency.addValue(FIRST_VALUE);
        Assert.assertEquals(1, frequency.getUniqueCount());

        // Adding a different value increases the unique count again.
        frequency.addValue(SECOND_VALUE);
        Assert.assertEquals(2, frequency.getUniqueCount());
    }
}
