package org.apache.commons.math4.legacy.stat.descriptive;

import org.apache.commons.math4.legacy.exception.MathIllegalArgumentException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests the copy behaviour of {@link SummaryStatistics}: both the copy constructor
 * and the static {@code SummaryStatistics.copy(src, dst)} method.
 */
public class SummaryStatisticsTest_testCopy {

    protected SummaryStatistics createSummaryStatistics() {
        return new SummaryStatistics();
    }

    /**
     * Asserts that two {@link SummaryStatistics} instances report identical values
     * for mean, variance, N, max, min, and sum.
     */
    private static void assertStatisticsEqual(SummaryStatistics expected, SummaryStatistics actual) {
        Assert.assertArrayEquals(toStatisticsArray(expected), toStatisticsArray(actual), 0);
    }

    /** Returns the six key statistics as an array for easy comparison. */
    private static double[] toStatisticsArray(SummaryStatistics s) {
        return new double[] {
            s.getMean(),
            s.getVariance(),
            s.getN(),
            s.getMax(),
            s.getMin(),
            s.getSum()
        };
    }

    /**
     * Verifies three copy semantics:
     * <ol>
     *   <li>The copy constructor ({@code new SummaryStatistics(src)}) produces an equal snapshot.</li>
     *   <li>Original and copy remain equal after the same values are added to both.</li>
     *   <li>{@code SummaryStatistics.copy(src, dst)} preserves the implementation type of each
     *       statistic accumulator, but does not share the accumulator instance (no aliasing).</li>
     * </ol>
     */
    @Test
    public void testCopy() {
        // --- Phase 1: copy constructor produces an equal snapshot ---
        SummaryStatistics original = createSummaryStatistics();
        original.addValue(2d);
        original.addValue(1d);
        original.addValue(3d);
        original.addValue(4d);

        SummaryStatistics copyOfOriginal = new SummaryStatistics(original);
        assertStatisticsEqual(original, copyOfOriginal);

        // --- Phase 2: original and copy stay equal when the same values are added ---
        double[] additionalValues = {7d, 9d, 11d, 23d};
        for (double v : additionalValues) {
            original.addValue(v);
            copyOfOriginal.addValue(v);
        }
        assertStatisticsEqual(original, copyOfOriginal);

        // --- Phase 3: static copy preserves implementation type without sharing instances ---
        original.clear();
        original.setSumImpl(new SumStat());

        SummaryStatistics destination = new SummaryStatistics();
        SummaryStatistics.copy(original, destination);

        // The sum accumulator in destination must be the same type as the one in original …
        Assert.assertEquals(original.getSumImpl().getClass(), destination.getSumImpl().getClass());
        // … but must not be the same object (no shared mutable state).
        Assert.assertNotSame(original.getSumImpl(), destination.getSumImpl());
    }

    /**
     * A minimal {@link StorelessUnivariateStatistic} that simply accumulates a running sum.
     * Used to verify that custom implementation types survive a copy operation.
     */
    private static final class SumStat implements StorelessUnivariateStatistic {

        private double s = 0;

        SumStat() {}

        @Override
        public double evaluate(double[] values) throws MathIllegalArgumentException {
            double total = 0;
            for (double x : values) {
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
            SumStat copy = new SumStat();
            copy.s = s;
            return copy;
        }
    }
}
