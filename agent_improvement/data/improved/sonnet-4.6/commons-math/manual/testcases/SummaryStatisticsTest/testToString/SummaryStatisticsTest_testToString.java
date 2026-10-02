package org.apache.commons.math4.legacy.stat.descriptive;

import org.junit.Assert;
import org.junit.Test;

public class SummaryStatisticsTest_testToString {

    protected SummaryStatistics createSummaryStatistics() {
        return new SummaryStatistics();
    }

    /**
     * Verifies that {@link SummaryStatistics#toString()} includes each statistic
     * in "label: value" format, using the values returned by the corresponding
     * getter at the time {@code toString()} is called.
     */
    @Test
    public void testToString() {
        SummaryStatistics stats = createSummaryStatistics();
        for (int i = 0; i < 5; i++) {
            stats.addValue(i);
        }

        final String[] labels = { "min", "max", "sum", "variance", "standard deviation" };
        final double[] values = {
            stats.getMin(),
            stats.getMax(),
            stats.getSum(),
            stats.getVariance(),
            stats.getStandardDeviation()
        };
        final String output = stats.toString();

        // getN() returns a long, so its label uses "n" in the output
        Assert.assertTrue(output.indexOf("n: " + stats.getN()) > 0);

        for (int i = 0; i < labels.length; i++) {
            String expected = labels[i] + ": " + String.valueOf(values[i]);
            Assert.assertTrue(
                "Expected toString() to contain \"" + expected + "\"",
                output.indexOf(expected) > 0
            );
        }
    }
}
