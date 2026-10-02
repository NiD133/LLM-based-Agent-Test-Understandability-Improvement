package org.apache.commons.math4.legacy.stat.descriptive;

import org.apache.commons.math4.legacy.TestUtils;
import org.junit.Assert;
import org.junit.Test;

public class SummaryStatisticsTest_testGetSummary {

    private static final double TOLERANCE = 10E-15;

    protected SummaryStatistics createSummaryStatistics() {
        return new SummaryStatistics();
    }

    private void verifySummary(SummaryStatistics statistics, StatisticalSummary summary) {
        Assert.assertEquals("N", summary.getN(), statistics.getN());
        TestUtils.assertEquals("sum", summary.getSum(), statistics.getSum(), TOLERANCE);
        TestUtils.assertEquals("var", summary.getVariance(), statistics.getVariance(), TOLERANCE);
        TestUtils.assertEquals("std", summary.getStandardDeviation(), statistics.getStandardDeviation(), TOLERANCE);
        TestUtils.assertEquals("mean", summary.getMean(), statistics.getMean(), TOLERANCE);
        TestUtils.assertEquals("min", summary.getMin(), statistics.getMin(), TOLERANCE);
        TestUtils.assertEquals("max", summary.getMax(), statistics.getMax(), TOLERANCE);
    }

    @Test
    public void testGetSummary() {
        SummaryStatistics statistics = createSummaryStatistics();

        verifyCurrentSummary(statistics);

        statistics.addValue(1d);
        verifyCurrentSummary(statistics);

        statistics.addValue(2d);
        verifyCurrentSummary(statistics);

        statistics.addValue(2d);
        verifyCurrentSummary(statistics);
    }

    private void verifyCurrentSummary(SummaryStatistics statistics) {
        StatisticalSummary summary = statistics.getSummary();
        verifySummary(statistics, summary);
    }
}
