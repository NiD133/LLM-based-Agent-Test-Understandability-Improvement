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

import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.apache.commons.math4.legacy.TestUtils;
import org.apache.commons.math4.legacy.exception.MathIllegalArgumentException;
import org.apache.commons.math4.legacy.exception.MathIllegalStateException;
import org.apache.commons.math4.legacy.stat.StatUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

/**
 * Test cases for the {@link SummaryStatistics} class.
 */
public class SummaryStatisticsTest {

    private static final double FIRST_VALUE = 1;
    private static final float SECOND_VALUE_AS_FLOAT = 2;
    private static final long SECOND_VALUE_AS_LONG = 2;
    private static final int THIRD_VALUE = 3;

    private static final double EXPECTED_MEAN = 2;
    private static final double EXPECTED_SUM = 8;
    private static final double EXPECTED_VARIANCE = 0.666666666666666666667;
    private static final double EXPECTED_STANDARD_DEVIATION = JdkMath.sqrt(EXPECTED_VARIANCE);
    private static final double EXPECTED_N = 4;
    private static final double EXPECTED_MIN = 1;
    private static final double EXPECTED_MAX = 3;
    private static final double TOLERANCE = 10E-15;

    protected SummaryStatistics createSummaryStatistics() {
        return new SummaryStatistics();
    }

    @Test
    public void testEmpty() {
        final SummaryStatistics stats = createSummaryStatistics();

        final double[] values = {};
        Assertions.assertEquals(StatUtils.sum(values), stats.getSum());
        Assertions.assertEquals(StatUtils.mean(values), stats.getMean());
        final double variance = StatUtils.variance(values);
        Assertions.assertEquals(JdkMath.sqrt(variance), stats.getStandardDeviation());
        Assertions.assertEquals(variance, stats.getVariance());
        Assertions.assertEquals(StatUtils.max(values), stats.getMax());
        Assertions.assertEquals(StatUtils.min(values), stats.getMin());
    }

    @Test
    public void testStats() {
        final SummaryStatistics stats = createSummaryStatistics();
        Assert.assertEquals("total count", 0, stats.getN(), TOLERANCE);

        addCanonicalValues(stats);
        assertCanonicalStatistics(stats);

        stats.clear();
        Assert.assertEquals("total count", 0, stats.getN(), TOLERANCE);
    }

    @Test
    public void testN0andN1Conditions() {
        final SummaryStatistics stats = createSummaryStatistics();
        Assert.assertTrue("Mean of n = 0 set should be NaN",
                Double.isNaN(stats.getMean()));
        Assert.assertTrue("Standard Deviation of n = 0 set should be NaN",
                Double.isNaN(stats.getStandardDeviation()));
        Assert.assertTrue("Variance of n = 0 set should be NaN",
                Double.isNaN(stats.getVariance()));

        stats.addValue(FIRST_VALUE);
        Assert.assertEquals("mean should be one (n = 1)", FIRST_VALUE, stats.getMean(), 0.0);
        Assert.assertEquals("Std should be zero (n = 1)", 0.0, stats.getStandardDeviation(), 0.0);
        Assert.assertEquals("variance should be zero (n = 1)", 0.0, stats.getVariance(), 0.0);

        stats.addValue(SECOND_VALUE_AS_FLOAT);
        Assert.assertTrue("Std should not be zero (n = 2)",
                stats.getStandardDeviation() != 0.0);
        Assert.assertTrue("variance should not be zero (n = 2)",
                stats.getVariance() != 0.0);
    }

    @Test
    public void testNaNContracts() {
        final SummaryStatistics stats = createSummaryStatistics();
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
    }

    @Test
    public void testGetSummary() {
        final SummaryStatistics stats = createSummaryStatistics();
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
        final SummaryStatistics source = createSummaryStatistics();
        source.addValue(2d);
        source.addValue(1d);
        source.addValue(3d);
        source.addValue(4d);

        final SummaryStatistics target = new SummaryStatistics(source);
        assertEquals(source, target);

        source.addValue(7d);
        source.addValue(9d);
        source.addValue(11d);
        source.addValue(23d);
        target.addValue(7d);
        target.addValue(9d);
        target.addValue(11d);
        target.addValue(23d);
        assertEquals(source, target);

        source.clear();
        source.setSumImpl(new SumStat());
        SummaryStatistics.copy(source, target);
        Assert.assertNotSame(source.getSumImpl(), target.getSumImpl());
        Assert.assertEquals(source.getSumImpl().getClass(), target.getSumImpl().getClass());
    }

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
            summary.getSum(),
        };
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
    public void testSetterInjection() {
        final SummaryStatistics stats = createSummaryStatistics();
        stats.setMeanImpl(new SumStat());
        stats.addValue(1);
        stats.addValue(3);
        Assert.assertEquals(4, stats.getMean(), 1E-14);

        stats.clear();
        stats.addValue(1);
        stats.addValue(2);
        Assert.assertEquals(3, stats.getMean(), 1E-14);

        stats.clear();
        stats.setMeanImpl(new SumStat());
    }

    @Test
    public void testSetterIllegalState() {
        final SummaryStatistics stats = createSummaryStatistics();
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

        Assertions.assertEquals(2, stats.getSumImpl().getResult());
        Assertions.assertEquals(3, stats.getMinImpl().getResult());
        Assertions.assertEquals(4, stats.getMaxImpl().getResult());
        Assertions.assertEquals(5, stats.getMeanImpl().getResult());
        Assertions.assertEquals(6, stats.getVarianceImpl().getResult());

        final SummaryStatistics copy = stats.copy();
        Assertions.assertEquals(2, copy.getSum());
        Assertions.assertEquals(3, copy.getMin());
        Assertions.assertEquals(4, copy.getMax());
        Assertions.assertEquals(5, copy.getMean());
        Assertions.assertEquals(6, copy.getVariance());

        stats.clear();
        Assertions.assertEquals(Double.NaN, stats.getSum());
        Assertions.assertEquals(Double.NaN, stats.getMin());
        Assertions.assertEquals(Double.NaN, stats.getMax());
        Assertions.assertEquals(Double.NaN, stats.getMean());
        Assertions.assertEquals(Double.NaN, stats.getVariance());

        stats.addValue(1);
        Assertions.assertEquals(1, stats.getSum());
        Assertions.assertEquals(1, stats.getMin());
        Assertions.assertEquals(1, stats.getMax());
        Assertions.assertEquals(1, stats.getMean());
        Assertions.assertEquals(1, stats.getVariance());
    }

    /**
     * JIRA: MATH-691.
     * Setting the variance implementation causes the StandardDeviation to be NaN.
     */
    @Test
    public void testOverrideVarianceWithMathClass() {
        final double[] scores = {1, 2, 3, 4};
        final SummaryStatistics stats = new SummaryStatistics();
        stats.setVarianceImpl(new SumStat());
        for (double score : scores) {
            stats.addValue(score);
        }

        final double expected = new SumStat().evaluate(scores);
        Assert.assertEquals(expected, stats.getVariance(), 0);
        Assert.assertEquals(JdkMath.sqrt(expected), stats.getStandardDeviation(), 0);
    }

    @Test
    public void testOverrideMeanWithMathClass() {
        final double[] scores = {1, 2, 3, 4};
        final SummaryStatistics stats = new SummaryStatistics();
        stats.setMeanImpl(new SumStat());
        for (double score : scores) {
            stats.addValue(score);
        }

        final double expected = new SumStat().evaluate(scores);
        Assert.assertEquals(expected, stats.getMean(), 0);
    }

    @Test
    public void testToString() {
        final SummaryStatistics stats = createSummaryStatistics();
        for (int i = 0; i < 5; i++) {
            stats.addValue(i);
        }

        final String[] labels = {"min", "max", "sum", "variance", "standard deviation"};
        final double[] values = {stats.getMin(), stats.getMax(), stats.getSum(),
                stats.getVariance(), stats.getStandardDeviation()};
        final String toString = stats.toString();
        Assert.assertTrue(toString.indexOf("n: " + stats.getN()) > 0);
        for (int i = 0; i < values.length; i++) {
            Assert.assertTrue(toString.indexOf(labels[i] + ": " + String.valueOf(values[i])) > 0);
        }
    }

    private static void addCanonicalValues(SummaryStatistics stats) {
        stats.addValue(FIRST_VALUE);
        stats.addValue(SECOND_VALUE_AS_FLOAT);
        stats.addValue(SECOND_VALUE_AS_LONG);
        stats.addValue(THIRD_VALUE);
    }

    private static void assertCanonicalStatistics(SummaryStatistics stats) {
        Assert.assertEquals("N", EXPECTED_N, stats.getN(), TOLERANCE);
        Assert.assertEquals("sum", EXPECTED_SUM, stats.getSum(), TOLERANCE);
        Assert.assertEquals("var", EXPECTED_VARIANCE, stats.getVariance(), TOLERANCE);
        Assert.assertEquals("std", EXPECTED_STANDARD_DEVIATION, stats.getStandardDeviation(), TOLERANCE);
        Assert.assertEquals("mean", EXPECTED_MEAN, stats.getMean(), TOLERANCE);
        Assert.assertEquals("min", EXPECTED_MIN, stats.getMin(), TOLERANCE);
        Assert.assertEquals("max", EXPECTED_MAX, stats.getMax(), TOLERANCE);
    }

    private static final class SumStat implements StorelessUnivariateStatistic {
        private double sum = 0;

        SumStat() {
        }

        SumStat(double sum) {
            this.sum = sum;
        }

        @Override
        public double evaluate(double[] values) throws MathIllegalArgumentException {
            double result = 0;
            for (final double value : values) {
                result += value;
            }
            return result;
        }

        @Override
        public double evaluate(double[] values, int begin, int length) throws MathIllegalArgumentException {
            throw new IllegalStateException();
        }

        @Override
        public void increment(double d) {
            sum += d;
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
            return sum;
        }

        @Override
        public long getN() {
            throw new IllegalStateException();
        }

        @Override
        public void clear() {
            sum = 0;
        }

        @Override
        public StorelessUnivariateStatistic copy() {
            final SumStat copy = new SumStat();
            copy.sum = sum;
            return copy;
        }
    }
}
