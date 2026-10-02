package org.apache.commons.math4.legacy.stat.descriptive;

import org.apache.commons.math4.legacy.exception.MathIllegalArgumentException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

/**
 * Verifies that {@link SummaryStatistics} honours custom statistic
 * implementations supplied through its {@code setXxxImpl} methods for every
 * statistic at once.
 */
public class SummaryStatisticsTest_testSetterAll {

    /**
     * Test when all the default statistic implementations are overridden.
     *
     * <p>Each statistic is backed by a {@link SeededSumStatistic} that starts
     * from a distinct seed (1..5) and simply adds every observed value to that
     * seed. Adding the single value {@code 1} therefore turns each seed
     * {@code k} into {@code k + 1}, which lets us confirm that every statistic
     * is wired to its own injected implementation.</p>
     */
    @Test
    public void testSetterAll() {
        final SummaryStatistics u = new SummaryStatistics();

        // A null implementation must be rejected for every statistic.
        Assertions.assertThrows(NullPointerException.class, () -> u.setSumImpl(null));
        Assertions.assertThrows(NullPointerException.class, () -> u.setMinImpl(null));
        Assertions.assertThrows(NullPointerException.class, () -> u.setMaxImpl(null));
        Assertions.assertThrows(NullPointerException.class, () -> u.setMeanImpl(null));
        Assertions.assertThrows(NullPointerException.class, () -> u.setVarianceImpl(null));

        // Inject a distinct, identifiable implementation for each statistic.
        u.setSumImpl(new SeededSumStatistic(1));
        u.setMinImpl(new SeededSumStatistic(2));
        u.setMaxImpl(new SeededSumStatistic(3));
        u.setMeanImpl(new SeededSumStatistic(4));
        u.setVarianceImpl(new SeededSumStatistic(5));

        // After observing one value, each seed k yields k + 1.
        u.addValue(1);
        Assertions.assertEquals(2, u.getSum());
        Assertions.assertEquals(3, u.getMin());
        Assertions.assertEquals(4, u.getMax());
        Assertions.assertEquals(5, u.getMean());
        Assertions.assertEquals(6, u.getVariance());

        // The getters must expose the very implementations we injected.
        Assertions.assertEquals(2, u.getSumImpl().getResult());
        Assertions.assertEquals(3, u.getMinImpl().getResult());
        Assertions.assertEquals(4, u.getMaxImpl().getResult());
        Assertions.assertEquals(5, u.getMeanImpl().getResult());
        Assertions.assertEquals(6, u.getVarianceImpl().getResult());

        // A copy must carry over the injected implementations and their state.
        final SummaryStatistics v = u.copy();
        Assertions.assertEquals(2, v.getSum());
        Assertions.assertEquals(3, v.getMin());
        Assertions.assertEquals(4, v.getMax());
        Assertions.assertEquals(5, v.getMean());
        Assertions.assertEquals(6, v.getVariance());

        // Clearing must honour the "return NaN when empty" contract.
        u.clear();
        Assertions.assertEquals(Double.NaN, u.getSum());
        Assertions.assertEquals(Double.NaN, u.getMin());
        Assertions.assertEquals(Double.NaN, u.getMax());
        Assertions.assertEquals(Double.NaN, u.getMean());
        Assertions.assertEquals(Double.NaN, u.getVariance());

        // After clearing, the (now reset) implementations keep working: each
        // seed is back to 0, so observing 1 yields 1 for every statistic.
        u.addValue(1);
        Assertions.assertEquals(1, u.getSum());
        Assertions.assertEquals(1, u.getMin());
        Assertions.assertEquals(1, u.getMax());
        Assertions.assertEquals(1, u.getMean());
        Assertions.assertEquals(1, u.getVariance());
    }

    /**
     * A test stub statistic that keeps a running sum, seeded with an initial
     * value so that statistics injected with different seeds can be told apart.
     * Only the increment/result/clear/copy behaviour exercised by this test is
     * implemented; the unused operations fail fast.
     */
    private static final class SeededSumStatistic implements StorelessUnivariateStatistic {
        private double sum;

        SeededSumStatistic() {
        }

        SeededSumStatistic(double seed) {
            sum = seed;
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
            final SeededSumStatistic r = new SeededSumStatistic();
            r.sum = sum;
            return r;
        }
    }
}
