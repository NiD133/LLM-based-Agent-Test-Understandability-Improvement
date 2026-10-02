package org.apache.commons.math4.legacy.stat;

import org.junit.Assert;
import org.junit.Test;

/**
 * Verifies {@link Frequency#getPct} and {@link Frequency#getCumPct}.
 */
public class FrequencyTest_testPcts {

    /** Allowed error when comparing percentages. */
    private static final double TOLERANCE = 10E-15d;

    /**
     * Builds the distribution 1, 2, 3, 3 (so value 3 appears twice) and checks
     * that the individual and cumulative percentages are computed correctly.
     */
    @Test
    public void testPcts() {
        final Frequency<Long> distribution = new Frequency<>();
        distribution.addValue(1L);
        distribution.addValue(2L);
        distribution.addValue(3L);
        distribution.addValue(3L);

        // Value 2 occurs once out of four observations -> 1/4 = 0.25.
        Assert.assertEquals("two pct", 0.25,
                distribution.getPct(Long.valueOf(2)), TOLERANCE);
        // Values <= 2 (i.e. 1 and 2) occur twice -> 2/4 = 0.50.
        Assert.assertEquals("two cum pct", 0.50,
                distribution.getCumPct(Long.valueOf(2)), TOLERANCE);
        // Value 3 is the largest, so all four observations are <= 3 -> 1.0.
        Assert.assertEquals("three cum pct", 1.0,
                distribution.getCumPct(3L), TOLERANCE);
    }
}
