package org.apache.commons.math4.legacy.stat.descriptive;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests the NaN contract for SummaryStatistics: all statistics must return NaN
 * when no data has been added, and return correct values once data is present.
 */
public class SummaryStatisticsTest_testNaNContracts {

    protected SummaryStatistics createSummaryStatistics() {
        return new SummaryStatistics();
    }

    @Test
    public void testNaNContracts() {
        // Given: a newly created SummaryStatistics with no data added
        SummaryStatistics stats = createSummaryStatistics();

        // Then: all statistics should return NaN for an empty dataset
        Assert.assertTrue("sum should be NaN when no data added", Double.isNaN(stats.getSum()));
        Assert.assertTrue("mean should be NaN when no data added", Double.isNaN(stats.getMean()));
        Assert.assertTrue("standard deviation should be NaN when no data added", Double.isNaN(stats.getStandardDeviation()));
        Assert.assertTrue("variance should be NaN when no data added", Double.isNaN(stats.getVariance()));
        Assert.assertTrue("max should be NaN when no data added", Double.isNaN(stats.getMax()));
        Assert.assertTrue("min should be NaN when no data added", Double.isNaN(stats.getMin()));

        // When: a single value is added
        stats.addValue(1.0);

        // Then: mean should equal that value, and variance should be zero (only one data point)
        Assert.assertEquals("mean should equal the single added value", 1.0, stats.getMean(), Double.MIN_VALUE);
        Assert.assertEquals("variance should be zero with only one data point", 0.0, stats.getVariance(), Double.MIN_VALUE);
        // FIXME: test all other NaN contract specs
    }
}
