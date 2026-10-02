package org.apache.commons.math4.legacy.stat.descriptive;

import org.apache.commons.math4.legacy.TestUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * Verifies that {@link SummaryStatistics#getSummary()} always returns a
 * {@link StatisticalSummary} snapshot whose values agree with the live
 * statistics object it was taken from, both before and after values are added.
 */
public class SummaryStatisticsTest_testGetSummary {

    /** Maximum allowed difference when comparing floating-point statistics. */
    private static final double TOLERANCE = 10E-15;

    protected SummaryStatistics createSummaryStatistics() {
        return new SummaryStatistics();
    }

    /**
     * Asserts that the summary snapshot reports exactly the same statistics as
     * the live {@link SummaryStatistics} instance it was taken from.
     */
    private void assertSummaryMatches(SummaryStatistics live, StatisticalSummary snapshot) {
        Assert.assertEquals("N", snapshot.getN(), live.getN());
        TestUtils.assertEquals("sum", snapshot.getSum(), live.getSum(), TOLERANCE);
        TestUtils.assertEquals("var", snapshot.getVariance(), live.getVariance(), TOLERANCE);
        TestUtils.assertEquals("std", snapshot.getStandardDeviation(), live.getStandardDeviation(), TOLERANCE);
        TestUtils.assertEquals("mean", snapshot.getMean(), live.getMean(), TOLERANCE);
        TestUtils.assertEquals("min", snapshot.getMin(), live.getMin(), TOLERANCE);
        TestUtils.assertEquals("max", snapshot.getMax(), live.getMax(), TOLERANCE);
    }

    @Test
    public void testGetSummary() {
        SummaryStatistics stats = createSummaryStatistics();

        // The snapshot must stay consistent with the live object as data accumulates,
        // so re-check it on the empty object and after each value is added.
        assertSummaryMatches(stats, stats.getSummary());

        stats.addValue(1d);
        assertSummaryMatches(stats, stats.getSummary());

        stats.addValue(2d);
        assertSummaryMatches(stats, stats.getSummary());

        stats.addValue(2d);
        assertSummaryMatches(stats, stats.getSummary());
    }
}
