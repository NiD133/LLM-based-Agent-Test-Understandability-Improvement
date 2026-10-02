package org.apache.commons.math4.legacy.stat.descriptive;

import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests that overriding the variance implementation with a custom class does not
 * cause the standard deviation to become NaN (regression for JIRA MATH-691).
 */
public class SummaryStatisticsTest_testOverrideVarianceWithMathClass {

    @Test
    public void testOverrideVarianceWithMathClass() {
        double[] scores = { 1, 2, 3, 4 };

        SummaryStatistics stats = new SummaryStatistics();
        stats.setVarianceImpl(new SumStat());
        for (double value : scores) {
            stats.addValue(value);
        }

        double expectedVariance = new SumStat().evaluate(scores);
        double expectedStdDev = JdkMath.sqrt(expectedVariance);

        Assert.assertEquals(expectedVariance, stats.getVariance(), 0);
        Assert.assertEquals(expectedStdDev, stats.getStandardDeviation(), 0);
    }
}
