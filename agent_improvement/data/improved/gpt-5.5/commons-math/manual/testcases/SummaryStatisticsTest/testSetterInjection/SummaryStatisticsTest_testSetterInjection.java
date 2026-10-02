package org.apache.commons.math4.legacy.stat.descriptive;

import org.junit.Assert;
import org.junit.Test;

public class SummaryStatisticsTest_testSetterInjection {

    private static final double TOLERANCE = 1E-14;

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
            for (int i = begin; i < begin + length; i++) {
                increment(values[i]);
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
            sum = 0;
            count = 0;
        }

        @Override
        public double evaluate(double[] values) {
            return evaluate(values, 0, values.length);
        }

        @Override
        public double evaluate(double[] values, int begin, int length) {
            double result = 0;
            for (int i = begin; i < begin + length; i++) {
                result += values[i];
            }
            return result;
        }

        @Override
        public StorelessUnivariateStatistic copy() {
            SumStat copy = new SumStat();
            copy.sum = sum;
            copy.count = count;
            return copy;
        }
    }

    protected SummaryStatistics createSummaryStatistics() {
        return new SummaryStatistics();
    }

    @Test
    public void testSetterInjection() {
        SummaryStatistics statistics = createSummaryStatistics();

        statistics.setMeanImpl(new SumStat());
        statistics.addValue(1);
        statistics.addValue(3);
        Assert.assertEquals(4, statistics.getMean(), TOLERANCE);

        statistics.clear();
        statistics.addValue(1);
        statistics.addValue(2);
        Assert.assertEquals(3, statistics.getMean(), TOLERANCE);

        // Setter injection is allowed again after clearing all values.
        statistics.clear();
        statistics.setMeanImpl(new SumStat());
    }
}
