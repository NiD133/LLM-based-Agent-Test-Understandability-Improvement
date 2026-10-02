/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
 * Test cases for the {@link SummaryStatistics} class.
 *
 * <p>The standard test dataset consists of four values added in this order:
 * {@code 1} (double), {@code 2} (float), {@code 2} (long), {@code 3} (int).
 * Expected statistics for this dataset:
 * <ul>
 *   <li>n    = 4</li>
 *   <li>sum  = 8</li>
 *   <li>mean = 2</li>
 *   <li>variance ≈ 0.6667  (population variance = 2/3)</li>
 *   <li>min  = 1, max = 3</li>
 * </ul>
 */
public class SummaryStatisticsTest {

    // --- Standard test dataset values ---
    private final double one   = 1;
    private final float  twoF  = 2;
    private final long   twoL  = 2;
    private final int    three = 3;

    // --- Expected statistics for the standard dataset {1, 2.0f, 2L, 3} ---
    private final double mean  = 2;
    private final double sumSq = 18;
    private final double sum   = 8;
    private final double var   = 0.666666666666666666667;
    private final double std   = JdkMath.sqrt(var);
    private final double n     = 4;
    private final double min   = 1;
    private final double max   = 3;

    private final double tolerance = 10E-15;

    protected SummaryStatistics createSummaryStatistics() {
        return new SummaryStatistics();
    }

    /**
     * Verifies that an empty {@link SummaryStatistics} instance returns the
     * same values as {@link StatUtils} computed over an empty array.
     */
    @Test
    public void testEmpty() {
        final SummaryStatistics stats = createSummaryStatistics();
        final double[] emptyArray = {};

        Assertions.assertEquals(StatUtils.sum(emptyArray),  stats.getSum());
        Assertions.assertEquals(StatUtils.mean(emptyArray), stats.getMean());
        final double emptyVariance = StatUtils.variance(emptyArray);
        Assertions.assertEquals(JdkMath.sqrt(emptyVariance), stats.getStandardDeviation());
        Assertions.assertEquals(emptyVariance,               stats.getVariance());
        Assertions.assertEquals(StatUtils.max(emptyArray),   stats.getMax());
        Assertions.assertEquals(StatUtils.min(emptyArray),   stats.getMin());
    }

    /**
     * Verifies that all statistics are correct after accumulating the standard
     * dataset, and that {@code clear()} resets the count to zero.
     */
    @Test
    public void testStats() {
        SummaryStatistics stats = createSummaryStatistics();
        Assert.assertEquals("total count", 0, stats.getN(), tolerance);

        stats.addValue(one);
        stats.addValue(twoF);
        stats.addValue(twoL);
        stats.addValue(three);

        Assert.assertEquals("N",    n,    stats.getN(),                 tolerance);
        Assert.assertEquals("sum",  sum,  stats.getSum(),               tolerance);
        Assert.assertEquals("var",  var,  stats.getVariance(),          tolerance);
        Assert.assertEquals("std",  std,  stats.getStandardDeviation(), tolerance);
        Assert.assertEquals("mean", mean, stats.getMean(),              tolerance);
        Assert.assertEquals("min",  min,  stats.getMin(),               tolerance);
        Assert.assertEquals("max",  max,  stats.getMax(),               tolerance);

        stats.clear();
        Assert.assertEquals("total count after clear", 0, stats.getN(), tolerance);
    }

    /**
     * Verifies NaN/zero edge cases for n=0, n=1, and n=2:
     * <ul>
     *   <li>n=0: mean, std, and variance are all NaN (undefined).</li>
     *   <li>n=1: mean equals the single value; std and variance are zero.</li>
     *   <li>n=2: std and variance become non-zero.</li>
     * </ul>
     */
    @Test
    public void testN0andN1Conditions() {
        SummaryStatistics stats = createSummaryStatistics();

        // n=0: dispersion metrics are undefined
        Assert.assertTrue("Mean of n=0 set should be NaN",
                Double.isNaN(stats.getMean()));
        Assert.assertTrue("Standard Deviation of n=0 set should be NaN",
                Double.isNaN(stats.getStandardDeviation()));
        Assert.assertTrue("Variance of n=0 set should be NaN",
                Double.isNaN(stats.getVariance()));

        // n=1: mean equals the value; no dispersion possible
        stats.addValue(one);
        Assert.assertEquals("mean should be one (n=1)", one, stats.getMean(), 0.0);
        Assert.assertEquals("Std should be zero (n=1)", 0.0, stats.getStandardDeviation(), 0.0);
        Assert.assertEquals("variance should be zero (n=1)", 0.0, stats.getVariance(), 0.0);

        // n=2: dispersion metrics are non-zero when values differ
        stats.addValue(twoF);
        Assert.assertTrue("Std should not be zero (n=2)",
                stats.getStandardDeviation() != 0.0);
        Assert.assertTrue("variance should not be zero (n=2)",
                stats.getVariance() != 0.0);
    }

    /**
     * Verifies that all statistics return NaN on an empty instance, and
     * return correct values after the first data point is added.
     */
    @Test
    public void testNaNContracts() {
        SummaryStatistics stats = createSummaryStatistics();

        // Empty instance: all statistics are undefined
        Assert.assertTrue("sum not NaN",     Double.isNaN(stats.getSum()));
        Assert.assertTrue("mean not NaN",    Double.isNaN(stats.getMean()));
        Assert.assertTrue("std dev not NaN", Double.isNaN(stats.getStandardDeviation()));
        Assert.assertTrue("var not NaN",     Double.isNaN(stats.getVariance()));
        Assert.assertTrue("max not NaN",     Double.isNaN(stats.getMax()));
        Assert.assertTrue("min not NaN",     Double.isNaN(stats.getMin()));

        // After adding a single value: mean equals that value; variance is zero
        stats.addValue(1.0);
        Assert.assertEquals("mean not expected",     1.0, stats.getMean(),     Double.MIN_VALUE);
        Assert.assertEquals("variance not expected", 0.0, stats.getVariance(), Double.MIN_VALUE);
    }

    /**
     * Verifies that {@link SummaryStatistics#getSummary()} returns a snapshot
     * that is consistent with the live instance at each step of data addition.
     */
    @Test
    public void testGetSummary() {
        SummaryStatistics stats = createSummaryStatistics();
        StatisticalSummary summary = stats.getSummary();
        verifySummary(stats, summary);

        stats.addValue(1d);
        summary = stats.getSummary();
        verifySummary(stats, summary);

        stats.addValue(2d);
        summary = stats.getSummary();
        verifySummary(stats, summary);

        stats.addValue(2d);
        summary = stats.getSummary();
        verifySummary(stats, summary);
    }

    /**
     * Verifies that copying a {@link SummaryStatistics} instance produces an
     * independent copy with the same state, that both instances evolve
     * identically when fed the same values, and that static copy preserves
     * the implementation type (but not the same instance).
     */
    @Test
    public void testCopy() {
        SummaryStatistics original = createSummaryStatistics();
        original.addValue(2d);
        original.addValue(1d);
        original.addValue(3d);
        original.addValue(4d);

        SummaryStatistics copy = new SummaryStatistics(original);
        assertStatisticsEqual(original, copy);

        // Both instances should produce identical statistics when updated identically
        original.addValue(7d);
        original.addValue(9d);
        original.addValue(11d);
        original.addValue(23d);
        copy.addValue(7d);
        copy.addValue(9d);
        copy.addValue(11d);
        copy.addValue(23d);
        assertStatisticsEqual(original, copy);

        // Static copy preserves implementation type, but creates a distinct instance
        original.clear();
        original.setSumImpl(new SumStat());
        SummaryStatistics.copy(original, copy);
        Assert.assertNotSame(original.getSumImpl(), copy.getSumImpl());
        Assert.assertEquals(original.getSumImpl().getClass(), copy.getSumImpl().getClass());
    }

    /**
     * Asserts that two {@link SummaryStatistics} instances have equal
     * statistics (mean, variance, N, max, min, sum).
     */
    private static void assertStatisticsEqual(SummaryStatistics expected, SummaryStatistics actual) {
        Assert.assertArrayEquals(toStatisticsArray(expected), toStatisticsArray(actual), 0);
    }

    private static double[] toStatisticsArray(SummaryStatistics stats) {
        return new double[] {
            stats.getMean(),
            stats.getVariance(),
            stats.getN(),
            stats.getMax(),
            stats.getMin(),
            stats.getSum(),
        };
    }

    private void verifySummary(SummaryStatistics stats, StatisticalSummary summary) {
        Assert.assertEquals("N",    summary.getN(),                     stats.getN());
        TestUtils.assertEquals("sum",  summary.getSum(),               stats.getSum(),               tolerance);
        TestUtils.assertEquals("var",  summary.getVariance(),          stats.getVariance(),          tolerance);
        TestUtils.assertEquals("std",  summary.getStandardDeviation(), stats.getStandardDeviation(), tolerance);
        TestUtils.assertEquals("mean", summary.getMean(),              stats.getMean(),              tolerance);
        TestUtils.assertEquals("min",  summary.getMin(),               stats.getMin(),               tolerance);
        TestUtils.assertEquals("max",  summary.getMax(),               stats.getMax(),               tolerance);
    }

    /**
     * Verifies that replacing the mean implementation via
     * {@link SummaryStatistics#setMeanImpl} causes {@code getMean()} to
     * delegate to the custom implementation. Also checks that the
     * implementation can be replaced after a {@code clear()}.
     */
    @Test
    public void testSetterInjection() {
        SummaryStatistics stats = createSummaryStatistics();

        // SumStat computes a running sum, so getMean() returns the accumulated sum
        stats.setMeanImpl(new SumStat());
        stats.addValue(1);
        stats.addValue(3);
        Assert.assertEquals(4, stats.getMean(), 1E-14);

        stats.clear();
        stats.addValue(1);
        stats.addValue(2);
        Assert.assertEquals(3, stats.getMean(), 1E-14);

        stats.clear();
        stats.setMeanImpl(new SumStat()); // replacing after clear is valid
    }

    /**
     * Verifies that calling {@code setMeanImpl} after data has been added
     * throws {@link MathIllegalStateException}.
     */
    @Test
    public void testSetterIllegalState() {
        SummaryStatistics stats = createSummaryStatistics();
        stats.addValue(1);
        stats.addValue(3);
        try {
            stats.setMeanImpl(new SumStat());
            Assert.fail("Expecting MathIllegalStateException");
        } catch (MathIllegalStateException ex) {
            // expected
        }
    }

    /**
     * Verifies behavior when all five statistical implementations are
     * replaced simultaneously:
     * <ol>
     *   <li>Null implementations are rejected.</li>
     *   <li>Custom implementations accumulate correctly.</li>
     *   <li>{@code copy()} propagates all custom implementations.</li>
     *   <li>NaN contract holds after {@code clear()} even with custom impls.</li>
     *   <li>Refilling after clear works correctly.</li>
     * </ol>
     */
    @Test
    public void testSetterAll() {
        final SummaryStatistics stats = createSummaryStatistics();

        // Null implementations must be rejected
        Assertions.assertThrows(NullPointerException.class, () -> stats.setSumImpl(null));
        Assertions.assertThrows(NullPointerException.class, () -> stats.setMinImpl(null));
        Assertions.assertThrows(NullPointerException.class, () -> stats.setMaxImpl(null));
        Assertions.assertThrows(NullPointerException.class, () -> stats.setMeanImpl(null));
        Assertions.assertThrows(NullPointerException.class, () -> stats.setVarianceImpl(null));

        // Install distinct SumStat instances with different initial offsets (1–5)
        // so each implementation can be identified by its result
        stats.setSumImpl(new SumStat(1));
        stats.setMinImpl(new SumStat(2));
        stats.setMaxImpl(new SumStat(3));
        stats.setMeanImpl(new SumStat(4));
        stats.setVarianceImpl(new SumStat(5));

        // addValue(1) increments each impl by 1, so result = initial offset + 1
        stats.addValue(1);
        Assertions.assertEquals(2, stats.getSum());
        Assertions.assertEquals(3, stats.getMin());
        Assertions.assertEquals(4, stats.getMax());
        Assertions.assertEquals(5, stats.getMean());
        Assertions.assertEquals(6, stats.getVariance());

        // Getters return the stored impl objects with their accumulated results
        Assertions.assertEquals(2, stats.getSumImpl().getResult());
        Assertions.assertEquals(3, stats.getMinImpl().getResult());
        Assertions.assertEquals(4, stats.getMaxImpl().getResult());
        Assertions.assertEquals(5, stats.getMeanImpl().getResult());
        Assertions.assertEquals(6, stats.getVarianceImpl().getResult());

        // copy() propagates all custom implementations and their current state
        final SummaryStatistics statsCopy = stats.copy();
        Assertions.assertEquals(2, statsCopy.getSum());
        Assertions.assertEquals(3, statsCopy.getMin());
        Assertions.assertEquals(4, statsCopy.getMax());
        Assertions.assertEquals(5, statsCopy.getMean());
        Assertions.assertEquals(6, statsCopy.getVariance());

        // After clear(), the NaN contract holds even with custom implementations
        stats.clear();
        Assertions.assertEquals(Double.NaN, stats.getSum());
        Assertions.assertEquals(Double.NaN, stats.getMin());
        Assertions.assertEquals(Double.NaN, stats.getMax());
        Assertions.assertEquals(Double.NaN, stats.getMean());
        Assertions.assertEquals(Double.NaN, stats.getVariance());

        // Refilling after clear resets the custom impls and accumulates normally
        stats.addValue(1);
        Assertions.assertEquals(1, stats.getSum());
        Assertions.assertEquals(1, stats.getMin());
        Assertions.assertEquals(1, stats.getMax());
        Assertions.assertEquals(1, stats.getMean());
        Assertions.assertEquals(1, stats.getVariance());
    }

    /**
     * JIRA: MATH-691.
     * Verifies that overriding the variance implementation does not break
     * {@code getStandardDeviation()}, which derives its value from variance.
     */
    @Test
    public void testOverrideVarianceWithMathClass() {
        double[] scores = {1, 2, 3, 4};
        SummaryStatistics stats = new SummaryStatistics();
        stats.setVarianceImpl(new SumStat());
        for (double score : scores) {
            stats.addValue(score);
        }
        final double expected = new SumStat().evaluate(scores);
        Assert.assertEquals(expected, stats.getVariance(), 0);
        Assert.assertEquals(JdkMath.sqrt(expected), stats.getStandardDeviation(), 0);
    }

    /**
     * Verifies that overriding the mean implementation causes {@code getMean()}
     * to delegate to the custom implementation.
     */
    @Test
    public void testOverrideMeanWithMathClass() {
        double[] scores = {1, 2, 3, 4};
        SummaryStatistics stats = new SummaryStatistics();
        stats.setMeanImpl(new SumStat());
        for (double score : scores) {
            stats.addValue(score);
        }
        final double expected = new SumStat().evaluate(scores);
        Assert.assertEquals(expected, stats.getMean(), 0);
    }

    /**
     * Verifies that {@code toString()} includes the count and all key
     * statistics by checking that each label-value pair appears in the output.
     */
    @Test
    public void testToString() {
        SummaryStatistics stats = createSummaryStatistics();
        for (int i = 0; i < 5; i++) {
            stats.addValue(i);
        }
        final String[] labels = {"min", "max", "sum", "variance", "standard deviation"};
        final double[] values = {
            stats.getMin(), stats.getMax(), stats.getSum(),
            stats.getVariance(), stats.getStandardDeviation()
        };
        final String toString = stats.toString();
        Assert.assertTrue(toString.indexOf("n: " + stats.getN()) > 0);
        for (int i = 0; i < values.length; i++) {
            Assert.assertTrue(toString.indexOf(labels[i] + ": " + String.valueOf(values[i])) > 0);
        }
    }

    // ---------------------------------------------------------------------------
    // Helper: a StorelessUnivariateStatistic that accumulates a running sum.
    // Used to verify that set*Impl() methods correctly delegate to custom impls.
    // ---------------------------------------------------------------------------
    private static final class SumStat implements StorelessUnivariateStatistic {
        private double s = 0;

        /** Creates an instance with an initial accumulated value of zero. */
        SumStat() {}

        /**
         * Creates an instance with the given initial accumulated value.
         *
         * @param sum the initial accumulated value
         */
        SumStat(double sum) {
            s = sum;
        }

        @Override
        public double evaluate(double[] values) throws MathIllegalArgumentException {
            double total = 0;
            for (final double x : values) {
                total += x;
            }
            return total;
        }

        @Override
        public double evaluate(double[] values, int begin, int length) throws MathIllegalArgumentException {
            throw new IllegalStateException();
        }

        @Override
        public void increment(double d) {
            s += d;
        }

        @Override
        public void incrementAll(double[] values) throws MathIllegalArgumentException {
            throw new IllegalStateException();
        }

        @Override
        public void incrementAll(double[] values, int start, int length) throws MathIllegalArgumentException {
            throw new IllegalStateException();
        }

        @Override
        public double getResult() {
            return s;
        }

        @Override
        public long getN() {
            throw new IllegalStateException();
        }

        @Override
        public void clear() {
            s = 0;
        }

        @Override
        public StorelessUnivariateStatistic copy() {
            final SumStat result = new SumStat();
            result.s = s;
            return result;
        }
    }
}
