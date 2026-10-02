package org.apache.commons.math4.legacy.stat.descriptive;

import org.apache.commons.math4.legacy.stat.StatUtils;
import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

/**
 * Verifies that a SummaryStatistics instance with no values added reports the
 * same statistics as StatUtils would compute for an empty double array.
 */
public class SummaryStatisticsTest_testEmpty {

    protected SummaryStatistics createSummaryStatistics() {
        return new SummaryStatistics();
    }

    @Test
    public void testEmpty() {
        // An empty stats accumulator — no values have been added yet
        final SummaryStatistics stats = createSummaryStatistics();

        // Reference values: what StatUtils computes for an empty dataset
        final double[] emptyData = {};
        final double expectedVariance = StatUtils.variance(emptyData);

        Assertions.assertEquals(StatUtils.sum(emptyData),          stats.getSum());
        Assertions.assertEquals(StatUtils.mean(emptyData),         stats.getMean());
        Assertions.assertEquals(expectedVariance,                   stats.getVariance());
        Assertions.assertEquals(JdkMath.sqrt(expectedVariance),     stats.getStandardDeviation());
        Assertions.assertEquals(StatUtils.max(emptyData),           stats.getMax());
        Assertions.assertEquals(StatUtils.min(emptyData),           stats.getMin());
    }
}
