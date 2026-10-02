package org.apache.commons.math4.legacy.stat.descriptive;

import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Assert;
import org.junit.Test;

/**
 * Verifies that {@link SummaryStatistics} computes the standard descriptive
 * statistics correctly for a small, known data set, and that {@link
 * SummaryStatistics#clear()} resets the accumulator.
 */
public class SummaryStatisticsTest_testStats {

    /**
     * The four sample values fed into the accumulator. They are intentionally
     * declared with different numeric types (double, float, long, int) so that
     * each {@code addValue} overload is exercised.
     */
    private static final double VALUE_1 = 1;   // double
    private static final float  VALUE_2 = 2;   // float
    private static final long   VALUE_3 = 2;   // long
    private static final int    VALUE_4 = 3;   // int

    /** Expected statistics for the data set {1, 2, 2, 3}. */
    private static final long   EXPECTED_COUNT    = 4;
    private static final double EXPECTED_SUM       = 8;
    private static final double EXPECTED_MEAN      = 2;
    private static final double EXPECTED_VARIANCE  = 0.666666666666666666667;
    private static final double EXPECTED_STD_DEV   = JdkMath.sqrt(EXPECTED_VARIANCE);
    private static final double EXPECTED_MIN       = 1;
    private static final double EXPECTED_MAX       = 3;

    /** Maximum allowed difference between expected and actual values. */
    private static final double TOLERANCE = 10E-15;

    @Test
    public void testStats() {
        SummaryStatistics stats = new SummaryStatistics();

        // A fresh accumulator holds no observations.
        Assert.assertEquals("total count", 0, stats.getN(), TOLERANCE);

        stats.addValue(VALUE_1);
        stats.addValue(VALUE_2);
        stats.addValue(VALUE_3);
        stats.addValue(VALUE_4);

        Assert.assertEquals("N",    EXPECTED_COUNT,   stats.getN(),               TOLERANCE);
        Assert.assertEquals("sum",  EXPECTED_SUM,     stats.getSum(),             TOLERANCE);
        Assert.assertEquals("var",  EXPECTED_VARIANCE, stats.getVariance(),       TOLERANCE);
        Assert.assertEquals("std",  EXPECTED_STD_DEV, stats.getStandardDeviation(), TOLERANCE);
        Assert.assertEquals("mean", EXPECTED_MEAN,    stats.getMean(),            TOLERANCE);
        Assert.assertEquals("min",  EXPECTED_MIN,     stats.getMin(),             TOLERANCE);
        Assert.assertEquals("max",  EXPECTED_MAX,     stats.getMax(),             TOLERANCE);

        // clear() returns the accumulator to its empty state.
        stats.clear();
        Assert.assertEquals("total count", 0, stats.getN(), TOLERANCE);
    }
}
