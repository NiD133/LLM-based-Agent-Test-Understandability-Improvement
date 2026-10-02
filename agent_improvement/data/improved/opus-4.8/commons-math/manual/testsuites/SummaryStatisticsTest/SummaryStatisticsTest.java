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
    // The reference data set used by testStats(): the four values
    // 1, 2, 2 and 3. Each value is declared with a different numeric type
    // so that the test also exercises every addValue(...) overload.
    // ------------------------------------------------------------------
    private final double valueOneAsDouble = 1;
    private final float  valueTwoAsFloat  = 2;
    private final long   valueTwoAsLong   = 2;
    private final int    valueThreeAsInt  = 3;

    // Expected statistics for the data set {1, 2, 2, 3}.
    private final double expectedMean              = 2;
    private final double expectedSumOfSquares      = 18;
    private final double expectedSum               = 8;
    private final double expectedVariance          = 0.666666666666666666667;
    private final double expectedStandardDeviation = JdkMath.sqrt(expectedVariance);
    private final double expectedCount             = 4;
    private final double expectedMin               = 1;
    private final double expectedMax               = 3;

    /** Floating-point comparison tolerance. */
    private final double tolerance = 10E-15;

    protected SummaryStatistics createSummaryStatistics() {
        return new SummaryStatistics();
    }

    @Test
    public void testEmpty() {
        final SummaryStatistics stats = createSummaryStatistics();

        // With no values added, every statistic must match the result that
        // StatUtils computes for an empty array.
        final double[] empty = {};
        Assertions.assertEquals(StatUtils.sum(empty), stats.getSum());
        Assertions.assertEquals(StatUtils.mean(empty), stats.getMean());
        final double emptyVariance = StatUtils.variance(empty);
        Assertions.assertEquals(JdkMath.sqrt(emptyVariance), stats.getStandardDeviation());
        Assertions.assertEquals(emptyVariance, stats.getVariance());
        Assertions.assertEquals(StatUtils.max(empty), stats.getMax());
        Assertions.assertEquals(StatUtils.min(empty), stats.getMin());
    }

    /** Add the four reference values and verify every statistic, then clear. */
    @Test
    public void testStats() {
        SummaryStatistics stats = createSummaryStatistics();
        Assert.assertEquals("total count", 0, stats.getN(), tolerance);

        stats.addValue(valueOneAsDouble);
        stats.addValue(valueTwoAsFloat);
        stats.addValue(valueTwoAsLong);
        stats.addValue(valueThreeAsInt);

        Assert.assertEquals("N", expectedCount, stats.getN(), tolerance);
        Assert.assertEquals("sum", expectedSum, stats.getSum(), tolerance);
        Assert.assertEquals("var", expectedVariance, stats.getVariance(), tolerance);
        Assert.assertEquals("std", expectedStandardDeviation, stats.getStandardDeviation(), tolerance);
        Assert.assertEquals("mean", expectedMean, stats.getMean(), tolerance);
        Assert.assertEquals("min", expectedMin, stats.getMin(), tolerance);
        Assert.assertEquals("max", expectedMax, stats.getMax(), tolerance);

        stats.clear();
        Assert.assertEquals("total count", 0, stats.getN(), tolerance);
    }

    @Test
    public void testN0andN1Conditions() {
        SummaryStatistics stats = createSummaryStatistics();

        // n = 0: mean, standard deviation and variance are undefined (NaN).
        Assert.assertTrue("Mean of n = 0 set should be NaN",
                Double.isNaN(stats.getMean()));
        Assert.assertTrue("Standard Deviation of n = 0 set should be NaN",
                Double.isNaN(stats.getStandardDeviation()));
        Assert.assertTrue("Variance of n = 0 set should be NaN",
                Double.isNaN(stats.getVariance()));

        // n = 1: mean equals the single value; spread statistics are exactly zero.
        stats.addValue(valueOneAsDouble);
        Assert.assertEquals("mean should be one (n = 1)", valueOneAsDouble, stats.getMean(), 0.0);
        Assert.assertEquals("Std should be zero (n = 1)", 0.0, stats.getStandardDeviation(), 0.0);
        Assert.assertEquals("variance should be zero (n = 1)", 0.0, stats.getVariance(), 0.0);

        // n = 2: two distinct values give a non-zero spread.
        stats.addValue(valueTwoAsFloat);
        Assert.assertTrue("Std should not be zero (n = 2)",
                stats.getStandardDeviation() != 0.0);
        Assert.assertTrue("variance should not be zero (n = 2)",
                stats.getVariance() != 0.0);
    }

    @Test
    public void testNaNContracts() {
        SummaryStatistics stats = createSummaryStatistics();

        // Every statistic is NaN while the collection is empty.
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

    @Test
    public void testGetSummary() {
        SummaryStatistics stats = createSummaryStatistics();

        // The snapshot returned by getSummary() must always agree with the
        // live instance, after every incremental update.
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

    @Test
    public void testCopy() {
        SummaryStatistics original = createSummaryStatistics();
        original.addValue(2d);
        original.addValue(1d);
        original.addValue(3d);
        original.addValue(4d);
        SummaryStatistics copy = new SummaryStatistics(original);
        assertEquals(original, copy);

        // Make sure both behave the same with additional values added.
        original.addValue(7d);
        original.addValue(9d);
        original.addValue(11d);
        original.addValue(23d);
        copy.addValue(7d);
        copy.addValue(9d);
        copy.addValue(11d);
        copy.addValue(23d);
        assertEquals(original, copy);

        // Check implementation pointers are preserved.
        original.clear();
        original.setSumImpl(new SumStat());
        SummaryStatistics.copy(original, copy);
        // This copy should be functionally distinct (i.e. no shared state) but
        // the implementation type should be the same.
        Assert.assertNotSame(original.getSumImpl(), copy.getSumImpl());
        Assert.assertEquals(original.getSumImpl().getClass(), copy.getSumImpl().getClass());
    }

    private static void assertEquals(SummaryStatistics expected, SummaryStatistics actual) {
        Assert.assertArrayEquals(toArray(expected), toArray(actual), 0);
    }

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

    private void verifySummary(SummaryStatistics stats, StatisticalSummary summary) {
        Assert.assertEquals("N", summary.getN(), stats.getN());
        TestUtils.assertEquals("sum", summary.getSum(), stats.getSum(), tolerance);
        TestUtils.assertEquals("var", summary.getVariance(), stats.getVariance(), tolerance);
        TestUtils.assertEquals("std", summary.getStandardDeviation(), stats.getStandardDeviation(), tolerance);
        TestUtils.assertEquals("mean", summary.getMean(), stats.getMean(), tolerance);
        TestUtils.assertEquals("min", summary.getMin(), stats.getMin(), tolerance);
        TestUtils.assertEquals("max", summary.getMax(), stats.getMax(), tolerance);
    }

    @Test
    public void testSetterInjection() {
        SummaryStatistics stats = createSummaryStatistics();

        // Inject a "sum" implementation as the mean: getMean() now returns the sum.
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

    @Test
    public void testSetterIllegalState() {
        SummaryStatistics stats = createSummaryStatistics();
        stats.addValue(1);
        stats.addValue(3);
        // Replacing an implementation after data has been added is not allowed.
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

        // Null implementations are rejected.
        Assertions.assertThrows(NullPointerException.class, () -> stats.setSumImpl(null));
        Assertions.assertThrows(NullPointerException.class, () -> stats.setMinImpl(null));
        Assertions.assertThrows(NullPointerException.class, () -> stats.setMaxImpl(null));
        Assertions.assertThrows(NullPointerException.class, () -> stats.setMeanImpl(null));
        Assertions.assertThrows(NullPointerException.class, () -> stats.setVarianceImpl(null));

        // Each statistic uses a SumStat seeded with a different starting value,
        // so a single addValue(1) yields a distinct, predictable result per statistic.
        stats.setSumImpl(new SumStat(1));
        stats.setMinImpl(new SumStat(2));
        stats.setMaxImpl(new SumStat(3));
        stats.setMeanImpl(new SumStat(4));
        stats.setVarianceImpl(new SumStat(5));
        stats.addValue(1);
        Assertions.assertEquals(2, stats.getSum());
        Assertions.assertEquals(3, stats.getMin());
        Assertions.assertEquals(4, stats.getMax());
        Assertions.assertEquals(5, stats.getMean());
        Assertions.assertEquals(6, stats.getVariance());

        // Test getters return the correct implementation.
        Assertions.assertEquals(2, stats.getSumImpl().getResult());
        Assertions.assertEquals(3, stats.getMinImpl().getResult());
        Assertions.assertEquals(4, stats.getMaxImpl().getResult());
        Assertions.assertEquals(5, stats.getMeanImpl().getResult());
        Assertions.assertEquals(6, stats.getVarianceImpl().getResult());

        // Test copy: the copy carries the same overridden results.
        final SummaryStatistics copy = stats.copy();
        Assertions.assertEquals(2, copy.getSum());
        Assertions.assertEquals(3, copy.getMin());
        Assertions.assertEquals(4, copy.getMax());
        Assertions.assertEquals(5, copy.getMean());
        Assertions.assertEquals(6, copy.getVariance());

        // Test the return NaN contract when empty.
        stats.clear();
        Assertions.assertEquals(Double.NaN, stats.getSum());
        Assertions.assertEquals(Double.NaN, stats.getMin());
        Assertions.assertEquals(Double.NaN, stats.getMax());
        Assertions.assertEquals(Double.NaN, stats.getMean());
        Assertions.assertEquals(Double.NaN, stats.getVariance());

        // Test refilling: after clear() the SumStat seeds are reset to zero.
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
        // Standard deviation must stay consistent with the overridden variance.
        final double expectedVariance = new SumStat().evaluate(scores);
        Assert.assertEquals(expectedVariance, stats.getVariance(), 0);
        Assert.assertEquals(JdkMath.sqrt(expectedVariance), stats.getStandardDeviation(), 0);
    }

    @Test
    public void testOverrideMeanWithMathClass() {
        double[] scores = {1, 2, 3, 4};
        SummaryStatistics stats = new SummaryStatistics();
        stats.setMeanImpl(new SumStat());
        for (double score : scores) {
            stats.addValue(score);
        }
        final double expectedMean = new SumStat().evaluate(scores);
        Assert.assertEquals(expectedMean, stats.getMean(), 0);
    }

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
     * A minimal {@link StorelessUnivariateStatistic} that simply accumulates a
     * running sum. It is used throughout this test to substitute one statistic's
     * computation for another and to verify implementation injection.
     */
    private static final class SumStat implements StorelessUnivariateStatistic {
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
