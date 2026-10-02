package org.apache.commons.math4.legacy.stat.descriptive;

import org.junit.Assert;
import org.junit.Test;

/**
 * Verifies how {@link SummaryStatistics} behaves for very small sample sizes,
 * where mean, variance and standard deviation are mathematically special:
 * <ul>
 *   <li>n = 0 : no data, so all of these statistics are undefined (NaN).</li>
 *   <li>n = 1 : a single value, so the mean equals that value and there is
 *       no spread, hence variance and standard deviation are exactly zero.</li>
 *   <li>n = 2 : two distinct values, so there is real spread and both
 *       variance and standard deviation must be non-zero.</li>
 * </ul>
 */
public class SummaryStatisticsTest_testN0andN1Conditions {

    /** Exact-match tolerance: these statistics are expected to be precise. */
    private static final double EXACT = 0.0;

    @Test
    public void testN0andN1Conditions() {
        SummaryStatistics stats = new SummaryStatistics();

        // n = 0: with no observations every statistic is undefined.
        Assert.assertTrue("Mean of n = 0 set should be NaN",
                Double.isNaN(stats.getMean()));
        Assert.assertTrue("Standard Deviation of n = 0 set should be NaN",
                Double.isNaN(stats.getStandardDeviation()));
        Assert.assertTrue("Variance of n = 0 set should be NaN",
                Double.isNaN(stats.getVariance()));

        // n = 1: a single value of 1.0 -> mean is that value, no spread.
        final double singleValue = 1.0;
        stats.addValue(singleValue);
        Assert.assertEquals("mean should be one (n = 1)",
                singleValue, stats.getMean(), EXACT);
        Assert.assertEquals("Std should be zero (n = 1)",
                0.0, stats.getStandardDeviation(), EXACT);
        Assert.assertEquals("variance should be zero (n = 1)",
                0.0, stats.getVariance(), EXACT);

        // n = 2: adding a different value (2.0) introduces real spread.
        final float secondValue = 2.0F;
        stats.addValue(secondValue);
        Assert.assertTrue("Std should not be zero (n = 2)",
                stats.getStandardDeviation() != 0.0);
        Assert.assertTrue("variance should not be zero (n = 2)",
                stats.getVariance() != 0.0);
    }
}
