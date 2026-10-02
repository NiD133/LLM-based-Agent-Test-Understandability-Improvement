package org.apache.commons.math4.legacy.stat.descriptive;

import org.apache.commons.math4.legacy.exception.MathIllegalArgumentException;
import org.apache.commons.math4.legacy.exception.MathIllegalStateException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests that calling setMeanImpl() on a SummaryStatistics that already has data
 * throws MathIllegalStateException, as implementations cannot be changed mid-accumulation.
 */
public class SummaryStatisticsTest_testSetterIllegalState {

    @Test
    public void testSetterIllegalState() {
        SummaryStatistics u = new SummaryStatistics();
        u.addValue(1);
        u.addValue(3);
        try {
            // Changing the mean implementation after data has been added must throw
            u.setMeanImpl(new SumStat());
            Assert.fail("Expecting MathIllegalStateException");
        } catch (MathIllegalStateException ex) {
            // expected
        }
    }

    /** Minimal StorelessUnivariateStatistic that accumulates a running sum. */
    private static final class SumStat implements StorelessUnivariateStatistic {
        private double s = 0;

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
            SumStat r = new SumStat();
            r.s = s;
            return r;
        }
    }
}
