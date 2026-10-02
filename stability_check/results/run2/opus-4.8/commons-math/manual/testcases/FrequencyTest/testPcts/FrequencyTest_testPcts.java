package org.apache.commons.math4.legacy.stat;

import org.junit.Assert;
import org.junit.Test;

/**
 * Verifies the percentage calculations of {@link Frequency}:
 * {@code getPct} (relative frequency of a single value) and
 * {@code getCumPct} (cumulative relative frequency up to a value).
 */
public class FrequencyTest_testPcts {

    /** Acceptable floating-point error when comparing percentages. */
    private static final double TOLERANCE = 10E-15d;

    @Test
    public void testPcts() {
        // Sample of four observations: 1, 2, 3, 3.
        // This gives the following counts: 1 -> 1, 2 -> 1, 3 -> 2 (total = 4).
        Frequency<Long> frequency = new Frequency<>();
        frequency.addValue(1L);
        frequency.addValue(2L);
        frequency.addValue(3L);
        frequency.addValue(3L);

        // Relative frequency of the value 2: one occurrence out of four -> 0.25.
        Assert.assertEquals("two pct", 0.25, frequency.getPct(2L), TOLERANCE);

        // Cumulative frequency up to and including 2: two of four values (1 and 2) -> 0.50.
        Assert.assertEquals("two cum pct", 0.50, frequency.getCumPct(2L), TOLERANCE);

        // Cumulative frequency up to and including 3: all four values -> 1.0.
        Assert.assertEquals("three cum pct", 1.0, frequency.getCumPct(3L), TOLERANCE);
    }
}
