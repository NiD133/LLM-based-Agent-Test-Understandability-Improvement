package org.apache.commons.math4.legacy.stat.descriptive;

import org.apache.commons.math4.legacy.exception.MathIllegalArgumentException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Verifies that a custom {@link StorelessUnivariateStatistic} can replace the
 * default mean implementation in {@link SummaryStatistics}.  When the override
 * is active, {@code getMean()} must return the value produced by the custom
 * statistic rather than the built-in arithmetic mean.
 */
public class SummaryStatisticsTest_testOverrideMeanWithMathClass {

    /**
     * A simple running-sum statistic used as a stand-in mean implementation.
     * {@code evaluate(double[])} returns the sum of the array, and
     * {@code increment}/{@code getResult} maintain a running total so that
     * the statistic integrates correctly with {@link SummaryStatistics}.
     */
    private static final class SumStat implements StorelessUnivariateStatistic {

        private double runningSum = 0;

        SumStat() {}

        @Override
        public double evaluate(double[] values) throws MathIllegalArgumentException {
            double total = 0;
            for (double v : values) {
                total += v;
            }
            return total;
        }

        @Override
        public double evaluate(double[] values, int begin, int length)
                throws MathIllegalArgumentException {
            throw new IllegalStateException("not implemented");
        }

        @Override
        public void increment(double d) {
            runningSum += d;
        }

        @Override
        public void incrementAll(double[] values) throws MathIllegalArgumentException {
            throw new IllegalStateException("not implemented");
        }

        @Override
        public void incrementAll(double[] values, int start, int length)
                throws MathIllegalArgumentException {
            throw new IllegalStateException("not implemented");
        }

        @Override
        public double getResult() {
            return runningSum;
        }

        @Override
        public long getN() {
            throw new IllegalStateException("not implemented");
        }

        @Override
        public void clear() {
            runningSum = 0;
        }

        @Override
        public StorelessUnivariateStatistic copy() {
            SumStat copy = new SumStat();
            copy.runningSum = this.runningSum;
            return copy;
        }
    }

    @Test
    public void testOverrideMeanWithMathClass() {
        double[] scores = {1, 2, 3, 4};

        // Install SumStat as the mean implementation, then feed in the scores.
        SummaryStatistics stats = new SummaryStatistics();
        stats.setMeanImpl(new SumStat());
        for (double value : scores) {
            stats.addValue(value);
        }

        // The expected result is what SumStat produces on the full array
        // (i.e. the sum of the scores, not the arithmetic mean).
        double expected = new SumStat().evaluate(scores);
        Assert.assertEquals(expected, stats.getMean(), 0);
    }
}
