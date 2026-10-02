package org.apache.commons.math4.legacy.stat.descriptive;

import org.junit.Assert;
import org.junit.Test;

public class SummaryStatisticsTest_testN0andN1Conditions {

    private static final double FIRST_VALUE = 1.0;
    private static final float SECOND_VALUE = 2.0f;

    protected SummaryStatistics createSummaryStatistics() {
        return new SummaryStatistics();
    }

    @Test
    public void testN0andN1Conditions() {
        SummaryStatistics statistics = createSummaryStatistics();

        Assert.assertTrue(
                "Mean of n = 0 set should be NaN",
                Double.isNaN(statistics.getMean()));
        Assert.assertTrue(
                "Standard Deviation of n = 0 set should be NaN",
                Double.isNaN(statistics.getStandardDeviation()));
        Assert.assertTrue(
                "Variance of n = 0 set should be NaN",
                Double.isNaN(statistics.getVariance()));

        statistics.addValue(FIRST_VALUE);

        Assert.assertEquals(
                "mean should be one (n = 1)",
                FIRST_VALUE,
                statistics.getMean(),
                0.0);
        Assert.assertEquals(
                "Std should be zero (n = 1)",
                0.0,
                statistics.getStandardDeviation(),
                0.0);
        Assert.assertEquals(
                "variance should be zero (n = 1)",
                0.0,
                statistics.getVariance(),
                0.0);

        statistics.addValue(SECOND_VALUE);

        Assert.assertTrue(
                "Std should not be zero (n = 2)",
                statistics.getStandardDeviation() != 0.0);
        Assert.assertTrue(
                "variance should not be zero (n = 2)",
                statistics.getVariance() != 0.0);
    }
}
