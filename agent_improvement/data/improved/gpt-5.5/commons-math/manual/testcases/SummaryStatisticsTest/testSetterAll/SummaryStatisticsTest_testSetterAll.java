package org.apache.commons.math4.legacy.stat.descriptive;

import org.junit.jupiter.api.Assertions;
import org.junit.Test;

public class SummaryStatisticsTest_testSetterAll {

    private static final int FIRST_SUM_STAT_OFFSET = 1;
    private static final int SECOND_SUM_STAT_OFFSET = 2;
    private static final int THIRD_SUM_STAT_OFFSET = 3;
    private static final int FOURTH_SUM_STAT_OFFSET = 4;
    private static final int FIFTH_SUM_STAT_OFFSET = 5;

    private static final int FIRST_VALUE = 1;

    protected SummaryStatistics createSummaryStatistics() {
        return new SummaryStatistics();
    }

    /**
     * Test when all the default implementations are overridden.
     */
    @Test
    public void testSetterAll() {
        final SummaryStatistics summary = createSummaryStatistics();

        assertSetterRejectsNullImplementations(summary);
        setDistinctImplementations(summary);

        summary.addValue(FIRST_VALUE);
        assertStatisticValues(summary, 2, 3, 4, 5, 6);
        assertImplementationResults(summary, 2, 3, 4, 5, 6);

        final SummaryStatistics copy = summary.copy();
        assertStatisticValues(copy, 2, 3, 4, 5, 6);

        summary.clear();
        assertStatisticValues(summary, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN);

        summary.addValue(FIRST_VALUE);
        assertStatisticValues(summary, 1, 1, 1, 1, 1);
    }

    private void assertSetterRejectsNullImplementations(SummaryStatistics summary) {
        Assertions.assertThrows(NullPointerException.class, () -> summary.setSumImpl(null));
        Assertions.assertThrows(NullPointerException.class, () -> summary.setMinImpl(null));
        Assertions.assertThrows(NullPointerException.class, () -> summary.setMaxImpl(null));
        Assertions.assertThrows(NullPointerException.class, () -> summary.setMeanImpl(null));
        Assertions.assertThrows(NullPointerException.class, () -> summary.setVarianceImpl(null));
    }

    private void setDistinctImplementations(SummaryStatistics summary) {
        summary.setSumImpl(new SumStat(FIRST_SUM_STAT_OFFSET));
        summary.setMinImpl(new SumStat(SECOND_SUM_STAT_OFFSET));
        summary.setMaxImpl(new SumStat(THIRD_SUM_STAT_OFFSET));
        summary.setMeanImpl(new SumStat(FOURTH_SUM_STAT_OFFSET));
        summary.setVarianceImpl(new SumStat(FIFTH_SUM_STAT_OFFSET));
    }

    private void assertStatisticValues(SummaryStatistics summary,
                                       double expectedSum,
                                       double expectedMin,
                                       double expectedMax,
                                       double expectedMean,
                                       double expectedVariance) {
        Assertions.assertEquals(expectedSum, summary.getSum());
        Assertions.assertEquals(expectedMin, summary.getMin());
        Assertions.assertEquals(expectedMax, summary.getMax());
        Assertions.assertEquals(expectedMean, summary.getMean());
        Assertions.assertEquals(expectedVariance, summary.getVariance());
    }

    private void assertImplementationResults(SummaryStatistics summary,
                                             double expectedSum,
                                             double expectedMin,
                                             double expectedMax,
                                             double expectedMean,
                                             double expectedVariance) {
        Assertions.assertEquals(expectedSum, summary.getSumImpl().getResult());
        Assertions.assertEquals(expectedMin, summary.getMinImpl().getResult());
        Assertions.assertEquals(expectedMax, summary.getMaxImpl().getResult());
        Assertions.assertEquals(expectedMean, summary.getMeanImpl().getResult());
        Assertions.assertEquals(expectedVariance, summary.getVarianceImpl().getResult());
    }

    private static final class SumStat extends AbstractStorelessUnivariateStatistic {
        private double value;
        private long n;

        private SumStat(double initialValue) {
            this.value = initialValue;
        }

        @Override
        public void increment(double d) {
            value += d;
            n++;
        }

        @Override
        public double getResult() {
            return n == 0 ? Double.NaN : value;
        }

        @Override
        public long getN() {
            return n;
        }

        @Override
        public void clear() {
            value = 0;
            n = 0;
        }

        @Override
        public SumStat copy() {
            SumStat copy = new SumStat(value);
            copy.n = n;
            return copy;
        }
    }
}
