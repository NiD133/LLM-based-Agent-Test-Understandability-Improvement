package org.apache.commons.math4.legacy.stat.descriptive;

import org.apache.commons.math4.legacy.TestUtils;
import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests that getSummary() returns a StatisticalSummary snapshot that stays in
 * sync with the parent SummaryStatistics as values are added.
 */
public class SummaryStatisticsTest_testGetSummary {

    private final double tolerance = 10E-15;

    protected SummaryStatistics createSummaryStatistics() {
        return new SummaryStatistics();
    }

    /**
     * Asserts that every statistical field in the snapshot equals the
     * corresponding field on the live accumulator.
     */
    private void verifySummaryMatchesAccumulator(StatisticalSummary snapshot, SummaryStatistics accumulator) {
        Assert.assertEquals("N",   snapshot.getN(),                 accumulator.getN());
        TestUtils.assertEquals("sum",  snapshot.getSum(),               accumulator.getSum(),               tolerance);
        TestUtils.assertEquals("var",  snapshot.getVariance(),          accumulator.getVariance(),          tolerance);
        TestUtils.assertEquals("std",  snapshot.getStandardDeviation(), accumulator.getStandardDeviation(), tolerance);
        TestUtils.assertEquals("mean", snapshot.getMean(),              accumulator.getMean(),              tolerance);
        TestUtils.assertEquals("min",  snapshot.getMin(),               accumulator.getMin(),               tolerance);
        TestUtils.assertEquals("max",  snapshot.getMax(),               accumulator.getMax(),               tolerance);
    }

    /**
     * Verifies that getSummary() reflects the current state of the accumulator
     * both on an empty instance and after each successive addValue() call.
     */
    @Test
    public void testGetSummary() {
        SummaryStatistics accumulator = createSummaryStatistics();

        // Empty accumulator: snapshot should show zeros/NaNs consistent with the live state.
        StatisticalSummary snapshot = accumulator.getSummary();
        verifySummaryMatchesAccumulator(snapshot, accumulator);

        // After each addValue the snapshot must mirror the updated accumulator.
        accumulator.addValue(1d);
        snapshot = accumulator.getSummary();
        verifySummaryMatchesAccumulator(snapshot, accumulator);

        accumulator.addValue(2d);
        snapshot = accumulator.getSummary();
        verifySummaryMatchesAccumulator(snapshot, accumulator);

        accumulator.addValue(2d);
        snapshot = accumulator.getSummary();
        verifySummaryMatchesAccumulator(snapshot, accumulator);
    }
}
