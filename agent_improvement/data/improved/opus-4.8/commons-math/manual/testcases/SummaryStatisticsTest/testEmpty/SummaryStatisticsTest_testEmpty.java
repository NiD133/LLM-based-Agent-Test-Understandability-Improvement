package org.apache.commons.math4.legacy.stat.descriptive;

import org.apache.commons.math4.legacy.stat.StatUtils;
import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

/**
 * Verifies that a freshly created {@link SummaryStatistics} (one that has not
 * received any values) reports the same statistics as {@link StatUtils} computed
 * over an empty array. For an empty data set every statistic is expected to be
 * {@code NaN}, so this test pins down that "no data" behaviour.
 */
public class SummaryStatisticsTest_testEmpty {

    /** Creates the statistics instance under test (no values added). */
    protected SummaryStatistics createSummaryStatistics() {
        return new SummaryStatistics();
    }

    @Test
    public void testEmpty() {
        final SummaryStatistics emptyStats = createSummaryStatistics();
        final double[] emptyData = {};

        // Each statistic of the empty SummaryStatistics must match the
        // StatUtils result for an empty array (all of which are NaN).
        Assertions.assertEquals(StatUtils.sum(emptyData), emptyStats.getSum());
        Assertions.assertEquals(StatUtils.mean(emptyData), emptyStats.getMean());

        final double expectedVariance = StatUtils.variance(emptyData);
        Assertions.assertEquals(JdkMath.sqrt(expectedVariance), emptyStats.getStandardDeviation());
        Assertions.assertEquals(expectedVariance, emptyStats.getVariance());

        Assertions.assertEquals(StatUtils.max(emptyData), emptyStats.getMax());
        Assertions.assertEquals(StatUtils.min(emptyData), emptyStats.getMin());
    }
}
