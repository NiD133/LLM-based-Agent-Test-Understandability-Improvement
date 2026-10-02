package org.apache.commons.math4.legacy.stat.descriptive;

import org.apache.commons.math4.legacy.exception.MathIllegalArgumentException;
import org.apache.commons.math4.legacy.exception.MathIllegalStateException;
import org.junit.Test;

import static org.junit.Assert.assertThrows;

/**
 * Verifies that the statistic implementations of a {@link SummaryStatistics}
 * may no longer be swapped out once values have been added.
 */
public class SummaryStatisticsTest_testSetterIllegalState {

    @Test
    public void testSetterIllegalState() {
        SummaryStatistics statistics = new SummaryStatistics();
        statistics.addValue(1);
        statistics.addValue(3);

        // Replacing an implementation after data has been collected is illegal.
        assertThrows(MathIllegalStateException.class,
                     () -> statistics.setMeanImpl(new SumStat()));
    }

    /**
     * Minimal {@link StorelessUnivariateStatistic} used only as a candidate
     * replacement implementation; its behaviour is irrelevant because the
     * setter rejects it before it is ever used.
     */
    private static final class SumStat implements StorelessUnivariateStatistic {

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
            final SumStat copy = new SumStat();
            copy.sum = sum;
            return copy;
        }
    }
}
