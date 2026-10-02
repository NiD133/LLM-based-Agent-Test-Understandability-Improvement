package org.apache.commons.math4.legacy.stat.descriptive;

import org.junit.Assert;
import org.junit.Test;

public class SummaryStatisticsTest_testOverrideMeanWithMathClass {

    @Test
    public void testOverrideMeanWithMathClass() {
        final double[] scores = { 1, 2, 3, 4 };
        final SummaryStatistics statistics = new SummaryStatistics();

        statistics.setMeanImpl(new SumStat());
        for (double score : scores) {
            statistics.addValue(score);
        }

        final double expectedMean = new SumStat().evaluate(scores);
        Assert.assertEquals(expectedMean, statistics.getMean(), 0);
    }

    private static final class SumStat implements StorelessUnivariateStatistic {
        private static final long serialVersionUID = 1L;

        private double sum;
        private long n;

        @Override
        public void increment(double value) {
            sum += value;
            n++;
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
            return n;
        }

        @Override
        public void clear() {
            sum = 0;
            n = 0;
        }

        @Override
        public double evaluate(double[] values) {
            return evaluate(values, 0, values.length);
        }

        @Override
        public double evaluate(double[] values, int begin, int length) {
            double total = 0;
            for (int i = begin; i < begin + length; i++) {
                total += values[i];
            }
            return total;
        }

        @Override
        public SumStat copy() {
            SumStat copy = new SumStat();
            copy.sum = sum;
            copy.n = n;
            return copy;
        }
    }
}
