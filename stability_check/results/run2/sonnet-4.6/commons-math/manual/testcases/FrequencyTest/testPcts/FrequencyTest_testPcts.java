package org.apache.commons.math4.legacy.stat;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests that {@link Frequency#getPct} and {@link Frequency#getCumPct} return
 * correct percentage values for a known distribution.
 */
public class FrequencyTest_testPcts {

    private static final double TOLERANCE = 10E-15d;

    /**
     * Builds a distribution of [1, 2, 3, 3] (four observations) and verifies:
     * <ul>
     *   <li>2 appears once → 25 % of observations</li>
     *   <li>values ≤ 2 account for two of four observations → 50 % cumulative</li>
     *   <li>values ≤ 3 account for all four observations → 100 % cumulative</li>
     * </ul>
     */
    @Test
    public void testPcts() {
        Frequency<Long> frequency = new Frequency<>();
        frequency.addValue(1L);
        frequency.addValue(2L);
        frequency.addValue(3L);
        frequency.addValue(3L);

        // 2 appears 1 out of 4 times
        Assert.assertEquals("pct of 2",
                0.25, frequency.getPct(Long.valueOf(2)), TOLERANCE);

        // values 1 and 2 together cover 2 of 4 observations
        Assert.assertEquals("cumulative pct up to 2",
                0.50, frequency.getCumPct(Long.valueOf(2)), TOLERANCE);

        // all four observations are ≤ 3
        Assert.assertEquals("cumulative pct up to 3",
                1.0, frequency.getCumPct(3L), TOLERANCE);
    }
}
