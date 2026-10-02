package org.apache.commons.math4.legacy.stat.descriptive;

import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.apache.commons.math4.legacy.stat.StatUtils;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class SummaryStatisticsTest_testEmpty {

    protected SummaryStatistics createSummaryStatistics() {
        return new SummaryStatistics();
    }

    @Test
    public void testEmpty() {
        final SummaryStatistics stats = createSummaryStatistics();
        final double[] emptyValues = {};

        Assertions.assertEquals(StatUtils.sum(emptyValues), stats.getSum());
        Assertions.assertEquals(StatUtils.mean(emptyValues), stats.getMean());

        final double expectedVariance = StatUtils.variance(emptyValues);
        Assertions.assertEquals(JdkMath.sqrt(expectedVariance), stats.getStandardDeviation());
        Assertions.assertEquals(expectedVariance, stats.getVariance());
        Assertions.assertEquals(StatUtils.max(emptyValues), stats.getMax());
        Assertions.assertEquals(StatUtils.min(emptyValues), stats.getMin());
    }
}
