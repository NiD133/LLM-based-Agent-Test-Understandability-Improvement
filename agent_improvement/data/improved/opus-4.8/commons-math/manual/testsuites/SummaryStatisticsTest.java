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
 */
public class SummaryStatisticsTest {

    // ------------------------------------------------------------------
    // Sample data set used by the "happy path" tests.
    //
    // The same four numbers (1, 2, 2, 3) are added through the different
    // SummaryStatistics.addValue overloads (double / float / long / int)
    // to exercise each overload. The constants below are the values that
    // get added, followed by the statistics they are expected to produce.
    // ------------------------------------------------------------------

    /** First sample value, added as a {@code double}. */
    private final double sampleAsDouble = 1;
    /** Second sample value, added as a {@code float}. */
    private final float sampleAsFloat = 2;
    /** Third sample value, added as a {@code long}. */
    private final long sampleAsLong = 2;
    /** Fourth sample value, added as an {@code int}. */
    private final int sampleAsInt = 3;

    /** Expected mean of the sample {1, 2, 2, 3}. */
    private final double expectedMean = 2;
    /** Expected sum of squares of the sample. */
    private final double expectedSumSq = 18;
    /** Expected sum of the sample. */
    private final double expectedSum = 8;
    /** Expected (sample) variance of the sample. */
    private final double expectedVariance = 0.666666666666666666667;
    /** Expected standard deviation of the sample. */
    private final double expectedStdDev = JdkMath.sqrt(expectedVariance);
    /** Expected number of observations in the sample. */
    private final double expectedN = 4;
    /** Expected minimum of the sample. */
    private final double expectedMin = 1;
    /** Expected maximum of the sample. */
    private final double expectedMax = 3;

    /** Tolerance for floating point comparisons. */
    private final double tolerance = 10E-15;

    protected SummaryStatistics createSummaryStatistics() {
        return new SummaryStatistics();
    }

    /**
     * With no values added, every statistic should match the result of
     * computing it over an empty array via {@link StatUtils}.
     */
    @Test
    public void testEmpty() {
        final SummaryStatistics stats = createSummaryStatistics();

        final double[] empty = {};
        Assertions.assertEquals(StatUtils.sum(empty), stats.getSum());
        Assertions.assertEquals(StatUtils.mean(empty), stats.getMean());
        final double emptyVariance = StatUtils.variance(empty);
        Assertions.assertEquals(JdkMath.sqrt(emptyVariance), stats.getStandardDeviation());
        Assertions.assertEquals(emptyVariance, stats.getVariance());
        Assertions.assertEquals(StatUtils.max(empty), stats.getMax());
        Assertions.assertEquals(StatUtils.min(empty), stats.getMin());
    }

    /**
     * Adding the four sample values should yield the expected statistics,
     * and {@link SummaryStatistics#clear()} should reset the count to zero.
     */
    @Test
    public void testStats() {
        SummaryStatistics stats = createSummaryStatistics();
        Assert.assertEquals("total count", 0, stats.getN(), tolerance);

        stats.addValue(sampleAsDouble);
        stats.addValue(sampleAsFloat);
        stats.addValue(sampleAsLong);
        stats.addValue(sampleAsInt);

        Assert.assertEquals("N", expectedN, stats.getN(), tolerance);
        Assert.assertEquals("sum", expectedSum, stats.getSum(), tolerance);
        Assert.assertEquals("var", expectedVariance, stats.getVariance(), tolerance);
        Assert.assertEquals("std", expectedStdDev, stats.getStandardDeviation(), tolerance);
        Assert.assertEquals("mean", expectedMean, stats.getMean(), tolerance);
        Assert.assertEquals("min", expectedMin, stats.getMin(), tolerance);
        Assert.assertEquals("max", expectedMax, stats.getMax(), tolerance);

        stats.clear();
        Assert.assertEquals("total count", 0, stats.getN(), tolerance);
    }

    /**
     * Verifies the documented behaviour for sample sizes of 0, 1 and 2:
     * with no values the location statistics are NaN; with a single value
     * the spread statistics are zero; with two values they are non-zero.
     */
    @Test
    public void testN0andN1Conditions() {
        SummaryStatistics stats = createSummaryStatistics();
        Assert.assertTrue("Mean of n = 0 set should be NaN",
                Double.isNaN(stats.getMean()));
        Assert.assertTrue("Standard Deviation of n = 0 set should be NaN",
                Double.isNaN(stats.getStandardDeviation()));
        Assert.assertTrue("Variance of n = 0 set should be NaN",
                Double.isNaN(stats.getVariance()));

        /* n=1 */
        stats.addValue(sampleAsDouble);
        Assert.assertEquals("mean should be one (n = 1)", sampleAsDouble, stats.getMean(), 0.0);
        Assert.assertEquals("Std should be zero (n = 1)", 0.0, stats.getStandardDeviation(), 0.0);
        Assert.assertEquals("variance should be zero (n = 1)", 0.0, stats.getVariance(), 0.0);

        /* n=2 */
        stats.addValue(sampleAsFloat);
        Assert.assertTrue("Std should not be zero (n = 2)",
                stats.getStandardDeviation() != 0.0);
        Assert.assertTrue("variance should not be zero (n = 2)",
                stats.getVariance() != 0.0);
    }

    /**
     * Before any value is added every statistic is NaN; once a single value
     * is added the mean and variance take their expected values.
     */
    @Test
    public void testNaNContracts() {
        SummaryStatistics stats = createSummaryStatistics();
        Assert.assertTrue("sum not NaN", Double.isNaN(stats.getSum()));
        Assert.assertTrue("mean not NaN", Double.isNaN(stats.getMean()));
        Assert.assertTrue("std dev not NaN", Double.isNaN(stats.getStandardDeviation()));
        Assert.assertTrue("var not NaN", Double.isNaN(stats.getVariance()));
        Assert.assertTrue("max not NaN", Double.isNaN(stats.getMax()));
        Assert.assertTrue("min not NaN", Double.isNaN(stats.getMin()));

        stats.addValue(1.0);

        Assert.assertEquals("mean not expected", 1.0,
                stats.getMean(), Double.MIN_VALUE);
        Assert.assertEquals("variance not expected", 0.0,
                stats.getVariance(), Double.MIN_VALUE);

        //FiXME: test all other NaN contract specs
    }

    /**
     * The snapshot returned by {@link SummaryStatistics#getSummary()} must
     * stay consistent with the live statistics after each value is added.
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
     * A copy must be equal to the original, must continue to track new
     * values identically, and must preserve the implementation types while
     * remaining functionally independent (no shared state).
     */
    @Test
    public void testCopy() {
        SummaryStatistics original = createSummaryStatistics();
        original.addValue(2d);
        original.addValue(1d);
        original.addValue(3d);
        original.addValue(4d);
        SummaryStatistics copy = new SummaryStatistics(original);
        assertEquals(original, copy);

        // Make sure both behave the same with additional values added
        original.addValue(7d);
        original.addValue(9d);
        original.addValue(11d);
        original.addValue(23d);
        copy.addValue(7d);
        copy.addValue(9d);
        copy.addValue(11d);
        copy.addValue(23d);
        assertEquals(original, copy);

        // Check implementation pointers are preserved
        original.clear();
        original.setSumImpl(new SumStat());
        SummaryStatistics.copy(original, copy);
        // This copy should be functionally distinct (i.e. no shared state) but
        // the implementation type should be the same.
        Assert.assertNotSame(original.getSumImpl(), copy.getSumImpl());
        Assert.assertEquals(original.getSumImpl().getClass(), copy.getSumImpl().getClass());
    }

    /** Asserts that two instances expose identical statistics. */
    private static void assertEquals(SummaryStatistics summary, SummaryStatistics summary2) {
        Assert.assertArrayEquals(toArray(summary), toArray(summary2), 0);
    }

    /** Collects the statistics of an instance into an array for comparison. */
    private static double[] toArray(SummaryStatistics summary) {
        return new double[] {
            summary.getMean(),
            summary.getVariance(),
            summary.getN(),
            summary.getMax(),
            summary.getMin(),
            summary.getSum(),
        };
    }

    /** Asserts that a summary snapshot matches the live statistics. */
    private void verifySummary(SummaryStatistics stats, StatisticalSummary summary) {
        Assert.assertEquals("N", summary.getN(), stats.getN());
        TestUtils.assertEquals("sum", summary.getSum(), stats.getSum(), tolerance);
        TestUtils.assertEquals("var", summary.getVariance(), stats.getVariance(), tolerance);
        TestUtils.assertEquals("std", summary.getStandardDeviation(), stats.getStandardDeviation(), tolerance);
        TestUtils.assertEquals("mean", summary.getMean(), stats.getMean(), tolerance);
        TestUtils.assertEquals("min", summary.getMin(), stats.getMin(), tolerance);
        TestUtils.assertEquals("max", summary.getMax(), stats.getMax(), tolerance);
    }

    /**
     * A custom mean implementation injected before any value is added must
     * be used, and injection must again be allowed after {@code clear()}.
     */
    @Test
    public void testSetterInjection() {
        SummaryStatistics stats = createSummaryStatistics();
        // SumStat computes a sum, so the "mean" here is actually the sum.
        stats.setMeanImpl(new SumStat());
        stats.addValue(1);
        stats.addValue(3);
        Assert.assertEquals(4, stats.getMean(), 1E-14);
        stats.clear();
        stats.addValue(1);
        stats.addValue(2);
        Assert.assertEquals(3, stats.getMean(), 1E-14);
        stats.clear();
        stats.setMeanImpl(new SumStat()); // OK after clear
    }

    /**
     * Replacing an implementation while values are already present must be
     * rejected with a {@link MathIllegalStateException}.
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
     * Test when all the default implementations are overridden.
     */
    @Test
    public void testSetterAll() {
        final SummaryStatistics stats = createSummaryStatistics();
        Assertions.assertThrows(NullPointerException.class, () -> stats.setSumImpl(null));
        Assertions.assertThrows(NullPointerException.class, () -> stats.setMinImpl(null));
        Assertions.assertThrows(NullPointerException.class, () -> stats.setMaxImpl(null));
        Assertions.assertThrows(NullPointerException.class, () -> stats.setMeanImpl(null));
        Assertions.assertThrows(NullPointerException.class, () -> stats.setVarianceImpl(null));
        // Distinct implementations, each seeded with a different starting sum
        // so we can tell them apart in the assertions below.
        stats.setSumImpl(new SumStat(1));
        stats.setMinImpl(new SumStat(2));
        stats.setMaxImpl(new SumStat(3));
        stats.setMeanImpl(new SumStat(4));
        stats.setVarianceImpl(new SumStat(5));
        // Adding 1 increments every seeded sum by 1.
        stats.addValue(1);
        Assertions.assertEquals(2, stats.getSum());
        Assertions.assertEquals(3, stats.getMin());
        Assertions.assertEquals(4, stats.getMax());
        Assertions.assertEquals(5, stats.getMean());
        Assertions.assertEquals(6, stats.getVariance());
        // Test getters return the correct implementation
        Assertions.assertEquals(2, stats.getSumImpl().getResult());
        Assertions.assertEquals(3, stats.getMinImpl().getResult());
        Assertions.assertEquals(4, stats.getMaxImpl().getResult());
        Assertions.assertEquals(5, stats.getMeanImpl().getResult());
        Assertions.assertEquals(6, stats.getVarianceImpl().getResult());
        // Test copy
        final SummaryStatistics copy = stats.copy();
        Assertions.assertEquals(2, copy.getSum());
        Assertions.assertEquals(3, copy.getMin());
        Assertions.assertEquals(4, copy.getMax());
        Assertions.assertEquals(5, copy.getMean());
        Assertions.assertEquals(6, copy.getVariance());
        // Test the return NaN contract when empty
        stats.clear();
        Assertions.assertEquals(Double.NaN, stats.getSum());
        Assertions.assertEquals(Double.NaN, stats.getMin());
        Assertions.assertEquals(Double.NaN, stats.getMax());
        Assertions.assertEquals(Double.NaN, stats.getMean());
        Assertions.assertEquals(Double.NaN, stats.getVariance());
        // Test refilling
        stats.addValue(1);
        Assertions.assertEquals(1, stats.getSum());
        Assertions.assertEquals(1, stats.getMin());
        Assertions.assertEquals(1, stats.getMax());
        Assertions.assertEquals(1, stats.getMean());
        Assertions.assertEquals(1, stats.getVariance());
    }

    /**
     * JIRA: MATH-691.
     * Setting the variance implementation causes the StandardDevitaion to be NaN.
     */
    @Test
    public void testOverrideVarianceWithMathClass() {
        double[] scores = {1, 2, 3, 4};
        SummaryStatistics stats = new SummaryStatistics();
        stats.setVarianceImpl(new SumStat());
        for (double score : scores) {
            stats.addValue(score);
        }
        // With the variance backed by SumStat, the variance is the sum and
        // the standard deviation is its square root.
        final double expected = new SumStat().evaluate(scores);
        Assert.assertEquals(expected, stats.getVariance(), 0);
        Assert.assertEquals(JdkMath.sqrt(expected), stats.getStandardDeviation(), 0);
    }

    /**
     * A custom mean implementation must drive {@code getMean()} for a stream
     * of added values.
     */
    @Test
    public void testOverrideMeanWithMathClass() {
        double[] scores = {1, 2, 3, 4};
        SummaryStatistics stats = new SummaryStatistics();
        stats.setMeanImpl(new SumStat());
        for (double score : scores) {
            stats.addValue(score);
        }
        // With the mean backed by SumStat, the reported mean is the sum.
        final double expected = new SumStat().evaluate(scores);
        Assert.assertEquals(expected, stats.getMean(), 0);
    }

    /**
     * {@code toString()} must report the count and every labelled statistic.
     */
    @Test
    public void testToString() {
        SummaryStatistics stats = createSummaryStatistics();
        for (int i = 0; i < 5; i++) {
            stats.addValue(i);
        }
        final String[] labels = {"min", "max", "sum", "variance", "standard deviation"};
        final double[] values = {stats.getMin(), stats.getMax(), stats.getSum(),
                stats.getVariance(), stats.getStandardDeviation()};
        final String toString = stats.toString();
        Assert.assertTrue(toString.indexOf("n: " + stats.getN()) > 0); // getN() returns a long
        for (int i = 0; i < values.length; i++) {
            Assert.assertTrue(toString.indexOf(labels[i] + ": " + String.valueOf(values[i])) > 0);
        }
    }

    /**
     * A simple {@link StorelessUnivariateStatistic} that accumulates a sum.
     *
     * <p>It is used as a stand-in implementation for the various statistic
     * slots so the tests can verify that injected implementations are
     * actually consulted. Operations that are not needed by these tests
     * deliberately throw to make accidental use obvious.</p>
     */
    private static final class SumStat implements StorelessUnivariateStatistic {
        /** The running sum. */
        private double s = 0;

        /** Create an instance. */
        SumStat() {}

        /**
         * Create an instance.
         *
         * @param sum the sum
         */
        SumStat(double sum) {
            s = sum;
        }

        @Override
        public double evaluate(double[] values) throws MathIllegalArgumentException {
            double s = 0;
            for (final double x : values) {
                s += x;
            }
            return s;
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
            final SumStat r = new SumStat();
            r.s = s;
            return r;
        }
    }
}
