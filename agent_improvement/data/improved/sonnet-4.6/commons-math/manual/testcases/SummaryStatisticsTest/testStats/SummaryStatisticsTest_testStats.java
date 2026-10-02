package org.apache.commons.math4.legacy.stat.descriptive;

import org.apache.commons.math4.legacy.TestUtils;
import org.apache.commons.math4.legacy.exception.MathIllegalArgumentException;
import org.apache.commons.math4.legacy.exception.MathIllegalStateException;
import org.apache.commons.math4.legacy.stat.StatUtils;
import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

/**
 * Tests for {@link SummaryStatistics} using the dataset {1, 2, 2, 3}.
 *
 * <p>The four input values are added as different numeric types (double, float, long, int)
 * to verify that all primitive widening conversions are handled correctly.
 *
 * <p>Pre-computed expected results for the dataset {1, 2, 2, 3}:
 * <ul>
 *   <li>n    = 4</li>
 *   <li>sum  = 8</li>
 *   <li>mean = 2</li>
 *   <li>var  = 2/3 ≈ 0.6667  (population-style, divided by n-1 gives the sample var)</li>
 *   <li>std  = sqrt(var)</li>
 *   <li>min  = 1</li>
 *   <li>max  = 3</li>
 * </ul>
 */
public class SummaryStatisticsTest_testStats {

    // --- Input values (each uses a different primitive type) ---
    private final double one   = 1;   // added as double
    private final float  twoF  = 2;   // added as float
    private final long   twoL  = 2;   // added as long
    private final int    three = 3;   // added as int

    // --- Pre-computed expected statistics for dataset {1, 2, 2, 3} ---
    private final double n      = 4;
    private final double sum    = 8;
    private final double sumSq  = 18;
    private final double mean   = 2;
    private final double var    = 0.666666666666666666667;
    private final double std    = JdkMath.sqrt(var);
    private final double min    = 1;
    private final double max    = 3;

    private final double tolerance = 10E-15;

    protected SummaryStatistics createSummaryStatistics() {
        return new SummaryStatistics();
    }

    // --- Helper: field-by-field equality between two SummaryStatistics instances ---
    private static void assertEquals(SummaryStatistics summary, SummaryStatistics summary2) {
        Assert.assertArrayEquals(toArray(summary), toArray(summary2), 0);
    }

    private static double[] toArray(SummaryStatistics summary) {
        return new double[] {
            summary.getMean(),
            summary.getVariance(),
            summary.getN(),
            summary.getMax(),
            summary.getMin(),
            summary.getSum()
        };
    }

    private void verifySummary(SummaryStatistics u, StatisticalSummary s) {
        Assert.assertEquals("N",   s.getN(),                 u.getN(),                 tolerance);
        TestUtils.assertEquals("sum",  s.getSum(),           u.getSum(),               tolerance);
        TestUtils.assertEquals("var",  s.getVariance(),      u.getVariance(),          tolerance);
        TestUtils.assertEquals("std",  s.getStandardDeviation(), u.getStandardDeviation(), tolerance);
        TestUtils.assertEquals("mean", s.getMean(),          u.getMean(),              tolerance);
        TestUtils.assertEquals("min",  s.getMin(),           u.getMin(),               tolerance);
        TestUtils.assertEquals("max",  s.getMax(),           u.getMax(),               tolerance);
    }

    /**
     * Verifies that {@link SummaryStatistics} correctly computes all basic
     * descriptive statistics for the dataset {1, 2, 2, 3} and resets to zero
     * after {@link SummaryStatistics#clear()}.
     */
    @Test
    public void testStats() {
        SummaryStatistics u = createSummaryStatistics();

        // Initially the accumulator must be empty
        Assert.assertEquals("total count", 0, u.getN(), tolerance);

        // Add the four values using different numeric primitive types
        u.addValue(one);
        u.addValue(twoF);
        u.addValue(twoL);
        u.addValue(three);

        // Verify all statistics match the pre-computed expectations
        Assert.assertEquals("N",    n,    u.getN(),                 tolerance);
        Assert.assertEquals("sum",  sum,  u.getSum(),               tolerance);
        Assert.assertEquals("var",  var,  u.getVariance(),          tolerance);
        Assert.assertEquals("std",  std,  u.getStandardDeviation(), tolerance);
        Assert.assertEquals("mean", mean, u.getMean(),              tolerance);
        Assert.assertEquals("min",  min,  u.getMin(),               tolerance);
        Assert.assertEquals("max",  max,  u.getMax(),               tolerance);

        // After clear(), the accumulator must be empty again
        u.clear();
        Assert.assertEquals("total count", 0, u.getN(), tolerance);
    }
}
