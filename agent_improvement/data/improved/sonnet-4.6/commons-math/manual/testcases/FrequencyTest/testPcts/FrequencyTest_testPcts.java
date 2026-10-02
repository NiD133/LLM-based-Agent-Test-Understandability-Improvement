package org.apache.commons.math4.legacy.stat;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the percentage and cumulative percentage calculations of {@link Frequency}.
 *
 * <p>A {@code Frequency<Long>} is populated with four values: 1, 2, 3, 3.
 * The expected distribution is:
 * <ul>
 *   <li>1 → 1 occurrence (25 %)</li>
 *   <li>2 → 1 occurrence (25 %)</li>
 *   <li>3 → 2 occurrences (50 %)</li>
 * </ul>
 * Cumulative percentages follow the natural ascending order of the values.
 */
public class FrequencyTest_testPcts {

    private static final double TOLERANCE = 10E-15d;

    private Frequency<Long> frequency;

    @Before
    public void setUp() {
        frequency = new Frequency<>();
        frequency.addValue(1L);
        frequency.addValue(2L);
        frequency.addValue(3L);
        frequency.addValue(3L);
    }

    /**
     * Value 2 appears once out of four observations, so its relative frequency is 25 %.
     */
    @Test
    public void testGetPct_value2_is25Percent() {
        Assert.assertEquals(0.25, frequency.getPct(Long.valueOf(2)), TOLERANCE);
    }

    /**
     * Values ≤ 2 account for two of four observations (1 and 2), giving a cumulative
     * percentage of 50 %.
     */
    @Test
    public void testGetCumPct_upToValue2_is50Percent() {
        Assert.assertEquals(0.50, frequency.getCumPct(Long.valueOf(2)), TOLERANCE);
    }

    /**
     * The cumulative percentage up to the largest observed value (3) must be 100 %.
     */
    @Test
    public void testGetCumPct_upToValue3_is100Percent() {
        Assert.assertEquals(1.0, frequency.getCumPct(3L), TOLERANCE);
    }
}
