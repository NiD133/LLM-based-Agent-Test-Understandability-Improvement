package org.apache.commons.math4.legacy.stat.descriptive;

import org.junit.Assert;
import org.junit.Test;

public class SummaryStatisticsTest_testN0andN1Conditions {

    protected SummaryStatistics createSummaryStatistics() {
        return new SummaryStatistics();
    }

    @Test
    public void testN0andN1Conditions() {
        SummaryStatistics u = createSummaryStatistics();

        // n=0: empty dataset — all statistics are undefined (NaN)
        Assert.assertTrue("Mean of n = 0 set should be NaN", Double.isNaN(u.getMean()));
        Assert.assertTrue("Standard Deviation of n = 0 set should be NaN", Double.isNaN(u.getStandardDeviation()));
        Assert.assertTrue("Variance of n = 0 set should be NaN", Double.isNaN(u.getVariance()));

        // n=1: single value — mean equals that value, spread is zero (no variability possible)
        final double firstValue = 1;
        u.addValue(firstValue);
        Assert.assertEquals("mean should be one (n = 1)", firstValue, u.getMean(), 0.0);
        Assert.assertEquals("Std should be zero (n = 1)", 0.0, u.getStandardDeviation(), 0.0);
        Assert.assertEquals("variance should be zero (n = 1)", 0.0, u.getVariance(), 0.0);

        // n=2: two distinct values — spread becomes non-zero
        final float secondValue = 2;
        u.addValue(secondValue);
        Assert.assertTrue("Std should not be zero (n = 2)", u.getStandardDeviation() != 0.0);
        Assert.assertTrue("variance should not be zero (n = 2)", u.getVariance() != 0.0);
    }
}
