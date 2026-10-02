package org.apache.commons.math4.legacy.stat;

import org.junit.Assert;
import org.junit.Test;

/**
 * Verifies the percentage calculations of {@link Frequency} for a small,
 * hand-checkable sample of {@code Long} values.
 */
public class FrequencyTest_testPcts {

    /** Sample values added to the frequency table: {1, 2, 3, 3}. */
    private static final long VALUE_1 = 1L;
    private static final long VALUE_2 = 2L;
    private static final long VALUE_3 = 3L;

    /** Total number of observations added below (four values). */
    private static final double SAMPLE_SIZE = 4.0;

    /** Numerical tolerance for comparing floating-point percentages. */
    private static final double TOLERANCE = 10E-15d;

    /**
     * With the sample {1, 2, 3, 3} the counts are: 1 -> once, 2 -> once,
     * 3 -> twice. This test checks the resulting relative and cumulative
     * percentages.
     */
    @Test
    public void testPcts() {
        Frequency<Long> frequency = new Frequency<>();
        frequency.addValue(VALUE_1);
        frequency.addValue(VALUE_2);
        frequency.addValue(VALUE_3);
        frequency.addValue(VALUE_3);

        // 2 occurs once out of four observations: 1/4 = 0.25.
        Assert.assertEquals("two pct",
                1.0 / SAMPLE_SIZE, frequency.getPct(VALUE_2), TOLERANCE);

        // Values <= 2 occur twice out of four: 2/4 = 0.50.
        Assert.assertEquals("two cum pct",
                2.0 / SAMPLE_SIZE, frequency.getCumPct(VALUE_2), TOLERANCE);

        // Values <= 3 cover the whole sample: 4/4 = 1.0.
        Assert.assertEquals("three cum pct",
                SAMPLE_SIZE / SAMPLE_SIZE, frequency.getCumPct(VALUE_3), TOLERANCE);
    }
}
