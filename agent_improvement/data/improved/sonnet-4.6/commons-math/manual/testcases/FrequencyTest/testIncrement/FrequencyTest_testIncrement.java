package org.apache.commons.math4.legacy.stat;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests that {@link Frequency#incrementValue} correctly adds to the count
 * of a given value, including positive increments, large positive increments,
 * and negative increments that bring the count back to zero.
 */
public class FrequencyTest_testIncrement {

    private static final long VALUE = 1L;

    @Test
    public void testIncrement() {
        Frequency<Long> frequency = new Frequency<>();

        // Initially no values have been recorded
        Assert.assertEquals(0, frequency.getUniqueCount());

        // Adding 1 occurrence brings the count for VALUE to 1
        frequency.incrementValue(VALUE, 1);
        Assert.assertEquals(1, frequency.getCount(VALUE));

        // Adding 4 more occurrences brings the count to 5
        frequency.incrementValue(VALUE, 4);
        Assert.assertEquals(5, frequency.getCount(VALUE));

        // Subtracting 5 occurrences brings the count back to 0
        frequency.incrementValue(VALUE, -5);
        Assert.assertEquals(0, frequency.getCount(VALUE));
    }
}
