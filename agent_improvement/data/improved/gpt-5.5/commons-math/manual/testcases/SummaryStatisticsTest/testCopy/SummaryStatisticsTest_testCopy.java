package org.apache.commons.math4.legacy.stat.descriptive;

import org.junit.Assert;
import org.junit.Test;

public class SummaryStatisticsTest_testCopy {

    private static final double EXACT_DOUBLE_TOLERANCE = 0d;

    protected SummaryStatistics createSummaryStatistics() {
        return new SummaryStatistics();
    }

    @Test
    public void testCopy() {
        SummaryStatistics original = createPopulatedSummaryStatistics();
        SummaryStatistics copy = new SummaryStatistics(original);

        assertSameSummaryValues(original, copy);

        addSameAdditionalValues(original, copy);

        assertSameSummaryValues(original, copy);

        original.clear();
        original.setSumImpl(new SumStat());
        SummaryStatistics.copy(original, copy);

        Assert.assertNotSame(original.getSumImpl(), copy.getSumImpl());
        Assert.assertEquals(original.getSumImpl().getClass(), copy.getSumImpl().getClass());
    }

    private SummaryStatistics createPopulatedSummaryStatistics() {
        SummaryStatistics statistics = createSummaryStatistics();
        statistics.addValue(2d);
        statistics.addValue(1d);
        statistics.addValue(3d);
        statistics.addValue(4d);
        return statistics;
    }

    private static void addSameAdditionalValues(SummaryStatistics original, SummaryStatistics copy) {
        original.addValue(7d);
        original.addValue(9d);
        original.addValue(11d);
        original.addValue(23d);

        copy.addValue(7d);
        copy.addValue(9d);
        copy.addValue(11d);
        copy.addValue(23d);
    }

    private static void assertSameSummaryValues(SummaryStatistics expected, SummaryStatistics actual) {
        Assert.assertArrayEquals(toSummaryArray(expected), toSummaryArray(actual), EXACT_DOUBLE_TOLERANCE);
    }

    private static double[] toSummaryArray(SummaryStatistics statistics) {
        return new double[] {
            statistics.getMean(),
            statistics.getVariance(),
            statistics.getN(),
            statistics.getMax(),
            statistics.getMin(),
            statistics.getSum()
        };
    }

    private static final class SumStat implements StorelessUnivariateStatistic {
        private double sum;
        private long count;

        @Override
        public void increment(double value) {
            sum += value;
            count++;
        }

        @Override
        public void incrementAll(double[] values) {
            incrementAll(values, 0, values.length);
        }

        @Override
        public void incrementAll(double[] values, int begin, int length) {
            for (int index = begin; index < begin + length; index++) {
                increment(values[index]);
            }
        }

        @Override
        public double getResult() {
            return sum;
        }

        @Override
        public long getN() {
            return count;
        }

        @Override
        public void clear() {
            sum = 0d;
            count = 0L;
        }

        @Override
        public double evaluate(double[] values) {
            return evaluate(values, 0, values.length);
        }

        @Override
        public double evaluate(double[] values, int begin, int length) {
            double result = 0d;
            for (int index = begin; index < begin + length; index++) {
                result += values[index];
            }
            return result;
        }

        @Override
        public SumStat copy() {
            SumStat copy = new SumStat();
            copy.sum = sum;
            copy.count = count;
            return copy;
        }
    }
}
