package org.apache.commons.math4.legacy.stat;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests {@link Frequency#addValue} together with the percentage queries
 * {@link Frequency#getPct} and {@link Frequency#getCumPct} for character values.
 */
public class FrequencyTest_testAdd {

    /** Allowed floating-point error when comparing percentages. */
    private static final double TOLERANCE = 10E-15d;

    /**
     * Adds two distinct characters once each and verifies their (cumulative)
     * percentages. With two values recorded, each accounts for half of the
     * sample, and the larger character covers 100% cumulatively.
     */
    @Test
    public void testAdd() {
        final Frequency<Character> frequency = new Frequency<>();

        final char first = 'a';
        final char second = 'b';

        frequency.addValue(first);
        frequency.addValue(second);

        // 'a' is one of two recorded values -> 50% of the sample.
        Assert.assertEquals("percentage of 'a'", 0.5, frequency.getPct(first), TOLERANCE);
        // 'b' is the largest value -> 100% cumulatively.
        Assert.assertEquals("cumulative percentage of 'b'", 1.0, frequency.getCumPct(second), TOLERANCE);
    }
}
