package org.apache.commons.math4.legacy.stat.descriptive;

import org.junit.Assert;
import org.junit.Test;

public class SummaryStatisticsTest_testToString {

    private static final String[] SUMMARY_VALUE_LABELS = {
        "min",
        "max",
        "sum",
        "variance",
        "standard deviation"
    };

    protected SummaryStatistics createSummaryStatistics() {
        return new SummaryStatistics();
    }

    @Test
    public void testToString() {
        SummaryStatistics statistics = createSummaryStatistics();
        for (int value = 0; value < 5; value++) {
            statistics.addValue(value);
        }

        final double[] summaryValues = {
            statistics.getMin(),
            statistics.getMax(),
            statistics.getSum(),
            statistics.getVariance(),
            statistics.getStandardDeviation()
        };
        final String summaryText = statistics.toString();

        Assert.assertTrue(summaryText.indexOf("n: " + statistics.getN()) > 0);
        for (int i = 0; i < summaryValues.length; i++) {
            Assert.assertTrue(summaryText.indexOf(SUMMARY_VALUE_LABELS[i] + ": " + String.valueOf(summaryValues[i])) > 0);
        }
    }
}
