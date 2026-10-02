package org.apache.commons.math4.legacy.stat.descriptive;

import org.apache.commons.math4.legacy.exception.MathIllegalStateException;
import org.junit.Assert;
import org.junit.Test;

public class SummaryStatisticsTest_testSetterIllegalState {

    protected SummaryStatistics createSummaryStatistics() {
        return new SummaryStatistics();
    }

    private static class SumStat extends AbstractStorelessUnivariateStatistic {
        private static final long serialVersionUID = 1L;

        @Override
        public void increment(double value) {
        }

        @Override
        public double getResult() {
            return 0;
        }

        @Override
        public long getN() {
            return 0;
        }

        @Override
        public void clear() {
        }

        @Override
        public SumStat copy() {
            return new SumStat();
        }
    }

    @Test
    public void testSetterIllegalState() {
        SummaryStatistics statistics = createSummaryStatistics();
        statistics.addValue(1);
        statistics.addValue(3);

        try {
            statistics.setMeanImpl(new SumStat());
            Assert.fail("Expecting MathIllegalStateException");
        } catch (MathIllegalStateException expected) {
            // expected
        }
    }
}
