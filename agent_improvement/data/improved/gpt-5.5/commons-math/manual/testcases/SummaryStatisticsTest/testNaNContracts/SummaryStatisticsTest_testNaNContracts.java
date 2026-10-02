package org.apache.commons.math4.legacy.stat.descriptive;

import org.junit.Assert;
import org.junit.Test;

public class SummaryStatisticsTest_testNaNContracts {

    private static final double SINGLE_VALUE = 1.0;
    private static final double EXACT_DOUBLE_TOLERANCE = Double.MIN_VALUE;

    protected SummaryStatistics createSummaryStatistics() {
        return new SummaryStatistics();
    }

    @Test
    public void testNaNContracts() {
        SummaryStatistics statistics = createSummaryStatistics();

        assertStatisticIsNaN("sum", statistics.getSum());
        assertStatisticIsNaN("mean", statistics.getMean());
        assertStatisticIsNaN("std dev", statistics.getStandardDeviation());
        assertStatisticIsNaN("var", statistics.getVariance());
        assertStatisticIsNaN("max", statistics.getMax());
        assertStatisticIsNaN("min", statistics.getMin());

        statistics.addValue(SINGLE_VALUE);

        Assert.assertEquals("mean not expected", SINGLE_VALUE, statistics.getMean(), EXACT_DOUBLE_TOLERANCE);
        Assert.assertEquals("variance not expected", 0.0, statistics.getVariance(), EXACT_DOUBLE_TOLERANCE);
        // FiXME: test all other NaN contract specs
    }

    private static void assertStatisticIsNaN(String statisticName, double value) {
        Assert.assertTrue(statisticName + " not NaN", Double.isNaN(value));
    }
}
