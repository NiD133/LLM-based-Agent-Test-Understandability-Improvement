package org.apache.commons.math4.legacy.stat.descriptive;

import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Assert;
import org.junit.Test;

public class SummaryStatisticsTest_testOverrideVarianceWithMathClass {

    private static final double[] SCORES = {1, 2, 3, 4};
    private static final double EXACT_TOLERANCE = 0;

    /**
     * JIRA: MATH-691.
     * Setting the variance implementation must not make standard deviation NaN.
     */
    @Test
    public void testOverrideVarianceWithMathClass() {
        SummaryStatistics statistics = new SummaryStatistics();
        statistics.setVarianceImpl(new SumStat());

        for (double score : SCORES) {
            statistics.addValue(score);
        }

        final double expectedVariance = new SumStat().evaluate(SCORES);
        Assert.assertEquals(expectedVariance, statistics.getVariance(), EXACT_TOLERANCE);
        Assert.assertEquals(JdkMath.sqrt(expectedVariance), statistics.getStandardDeviation(), EXACT_TOLERANCE);
    }

    private static class SumStat implements StorelessUnivariateStatistic {
        private static final long serialVersionUID = 1L;

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
        public SumStat copy() {
            SumStat copy = new SumStat();
            copy.sum = sum;
            copy.count = count;
            return copy;
        }
    }
}
