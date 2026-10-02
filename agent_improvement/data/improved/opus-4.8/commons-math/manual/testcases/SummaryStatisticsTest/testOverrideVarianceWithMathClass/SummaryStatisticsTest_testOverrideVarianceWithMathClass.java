package org.apache.commons.math4.legacy.stat.descriptive;

import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Assert;
import org.junit.Test;

public class SummaryStatisticsTest_testOverrideVarianceWithMathClass {

    /**
     * JIRA: MATH-691.
     *
     * <p>Regression test: when the variance implementation is overridden with a
     * custom statistic, the standard deviation must stay consistent with it
     * (i.e. {@code sqrt(variance)}) instead of becoming {@code NaN}.</p>
     */
    @Test
    public void testOverrideVarianceWithMathClass() {
        final double[] scores = { 1, 2, 3, 4 };

        // Override the variance implementation with the SumStat helper statistic.
        final SummaryStatistics stats = new SummaryStatistics();
        stats.setVarianceImpl(new SummaryStatisticsTest.SumStat());
        for (double score : scores) {
            stats.addValue(score);
        }

        // The reported variance must match the overriding statistic, and the
        // standard deviation must remain its square root (not NaN).
        final double expectedVariance = new SummaryStatisticsTest.SumStat().evaluate(scores);
        Assert.assertEquals(expectedVariance, stats.getVariance(), 0);
        Assert.assertEquals(JdkMath.sqrt(expectedVariance), stats.getStandardDeviation(), 0);
    }
}
