package org.apache.commons.math4.legacy.stat.descriptive;

import org.apache.commons.math4.legacy.exception.MathIllegalArgumentException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Verifies that {@link SummaryStatistics#setMeanImpl} lets the caller replace the
 * built-in mean calculation with a custom {@link StorelessUnivariateStatistic}.
 *
 * <p>The test injects an implementation that simply <em>sums</em> the incoming
 * values, then confirms that {@link SummaryStatistics#getMean()} now returns that
 * sum instead of the arithmetic mean -- proving the injected implementation is the
 * one actually being used.
 */
public class SummaryStatisticsTest_testOverrideMeanWithMathClass {

    /** The mean must match the reference value exactly, so no tolerance is allowed. */
    private static final double EXACT_MATCH = 0;

    @Test
    public void testOverrideMeanWithMathClass() {
        final double[] values = {1, 2, 3, 4};

        // Override the mean implementation with one that accumulates the sum.
        final SummaryStatistics stats = new SummaryStatistics();
        stats.setMeanImpl(new SummingStatistic());
        for (final double value : values) {
            stats.addValue(value);
        }

        // Since the mean implementation now sums, getMean() should equal the total (1+2+3+4 = 10).
        final double expectedSum = new SummingStatistic().evaluate(values);
        Assert.assertEquals(expectedSum, stats.getMean(), EXACT_MATCH);
    }

    /**
     * Test-only {@link StorelessUnivariateStatistic} whose "result" is the running
     * sum of every value it receives. It is used purely to override the default
     * mean calculation; methods not exercised by this test throw to make any
     * accidental use obvious.
     */
    private static final class SummingStatistic implements StorelessUnivariateStatistic {

        /** Running sum of the values supplied via {@link #increment(double)}. */
        private double sum;

        @Override
        public double evaluate(double[] values) throws MathIllegalArgumentException {
            double total = 0;
            for (final double value : values) {
                total += value;
            }
            return total;
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
            final SummingStatistic copy = new SummingStatistic();
            copy.sum = sum;
            return copy;
        }
    }
}
