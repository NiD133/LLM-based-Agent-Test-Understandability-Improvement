package org.apache.commons.math4.legacy.stat;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Verifies that {@link Frequency#incrementValue(Comparable, long)} adjusts the
 * recorded count for a value by the given (possibly negative) amount.
 */
public class FrequencyTest_testIncrement {

    /** The single value whose count is tracked throughout the test. */
    private static final long VALUE = 1L;

    @Test
    public void testIncrement() {
        Frequency<Long> frequency = new Frequency<>();

        // A new Frequency starts empty.
        assertEquals(0, frequency.getUniqueCount());

        // Incrementing by 1 establishes a count of 1.
        frequency.incrementValue(VALUE, 1);
        assertEquals(1, frequency.getCount(VALUE));

        // Incrementing by 4 more raises the count to 5.
        frequency.incrementValue(VALUE, 4);
        assertEquals(5, frequency.getCount(VALUE));

        // Incrementing by -5 brings the count back down to 0.
        frequency.incrementValue(VALUE, -5);
        assertEquals(0, frequency.getCount(VALUE));
    }
}
