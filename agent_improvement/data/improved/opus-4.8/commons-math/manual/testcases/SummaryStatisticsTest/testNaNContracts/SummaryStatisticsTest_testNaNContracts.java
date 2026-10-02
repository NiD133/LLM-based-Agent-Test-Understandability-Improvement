package org.apache.commons.math4.legacy.stat.descriptive;

import org.junit.Assert;
import org.junit.Test;

/**
 * Verifies the {@link SummaryStatistics} "NaN contracts": when no values have
 * been added yet, every statistic must report {@code NaN}, and once values are
 * present the statistics must report meaningful numbers instead.
 */
public class SummaryStatisticsTest_testNaNContracts {

    /** Exact comparison tolerance for values that should match precisely. */
    private static final double EXACT_TOLERANCE = Double.MIN_VALUE;

    protected SummaryStatistics createSummaryStatistics() {
        return new SummaryStatistics();
    }

    @Test
    public void testNaNContracts() {
        SummaryStatistics stats = createSummaryStatistics();

        // With no values added, every statistic is undefined and must be NaN.
        Assert.assertTrue("sum should be NaN before any value is added",
                Double.isNaN(stats.getSum()));
        Assert.assertTrue("mean should be NaN before any value is added",
                Double.isNaN(stats.getMean()));
        Assert.assertTrue("standard deviation should be NaN before any value is added",
                Double.isNaN(stats.getStandardDeviation()));
        Assert.assertTrue("variance should be NaN before any value is added",
                Double.isNaN(stats.getVariance()));
        Assert.assertTrue("max should be NaN before any value is added",
                Double.isNaN(stats.getMax()));
        Assert.assertTrue("min should be NaN before any value is added",
                Double.isNaN(stats.getMin()));

        // After adding a single value the statistics become well defined.
        stats.addValue(1.0);
        Assert.assertEquals("mean of a single value should equal that value",
                1.0, stats.getMean(), EXACT_TOLERANCE);
        Assert.assertEquals("variance of a single value should be zero",
                0.0, stats.getVariance(), EXACT_TOLERANCE);
    }
}
