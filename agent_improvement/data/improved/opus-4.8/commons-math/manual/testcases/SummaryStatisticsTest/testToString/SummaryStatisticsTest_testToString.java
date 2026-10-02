package org.apache.commons.math4.legacy.stat.descriptive;

import org.junit.Assert;
import org.junit.Test;

/**
 * Verifies that {@link SummaryStatistics#toString()} reports each computed
 * statistic, formatted as "label: value".
 */
public class SummaryStatisticsTest_testToString {

    /** Values fed into the statistics object: 0, 1, 2, 3, 4. */
    private static final int VALUE_COUNT = 5;

    protected SummaryStatistics createSummaryStatistics() {
        return new SummaryStatistics();
    }

    @Test
    public void testToString() {
        final SummaryStatistics stats = createSummaryStatistics();
        for (int value = 0; value < VALUE_COUNT; value++) {
            stats.addValue(value);
        }

        final String summary = stats.toString();

        // getN() returns a long; it is reported under the "n" label.
        Assert.assertTrue(summary.indexOf("n: " + stats.getN()) > 0);

        // Each remaining statistic is reported as "<label>: <value>".
        assertReports(summary, "min", stats.getMin());
        assertReports(summary, "max", stats.getMax());
        assertReports(summary, "sum", stats.getSum());
        assertReports(summary, "variance", stats.getVariance());
        assertReports(summary, "standard deviation", stats.getStandardDeviation());
    }

    /** Asserts that {@code summary} contains the entry "{@code label}: {@code value}". */
    private static void assertReports(String summary, String label, double value) {
        Assert.assertTrue(summary.indexOf(label + ": " + String.valueOf(value)) > 0);
    }
}
