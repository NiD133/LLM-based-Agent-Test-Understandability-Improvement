package org.apache.commons.math4.legacy.stat.descriptive;

import org.apache.commons.math4.legacy.TestUtils;
import org.apache.commons.math4.legacy.exception.MathIllegalArgumentException;
import org.apache.commons.math4.legacy.exception.MathIllegalStateException;
import org.apache.commons.math4.legacy.stat.StatUtils;
import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class SummaryStatisticsTest_testSetterIllegalState {

    private final double one = 1;

    private final float twoF = 2;

    private final long twoL = 2;

    private final int three = 3;

    private final double mean = 2;

    private final double sumSq = 18;

    private final double sum = 8;

    private final double var = 0.666666666666666666667;

    private final double std = JdkMath.sqrt(var);

    private final double n = 4;

    private final double min = 1;

    private final double max = 3;

    private final double tolerance = 10E-15;

    protected SummaryStatistics createSummaryStatistics() {
        return new SummaryStatistics();
    }

    private static void assertEquals(SummaryStatistics summary, SummaryStatistics summary2) {
        Assert.assertArrayEquals(toArray(summary), toArray(summary2), 0);
    }

    private static double[] toArray(SummaryStatistics summary) {
        return new double[] { summary.getMean(), summary.getVariance(), summary.getN(), summary.getMax(), summary.getMin(), summary.getSum() };
    }

    private void verifySummary(SummaryStatistics u, StatisticalSummary s) {
        Assert.assertEquals("N", s.getN(), u.getN());
        TestUtils.assertEquals("sum", s.getSum(), u.getSum(), tolerance);
        TestUtils.assertEquals("var", s.getVariance(), u.getVariance(), tolerance);
        TestUtils.assertEquals("std", s.getStandardDeviation(), u.getStandardDeviation(), tolerance);
        TestUtils.assertEquals("mean", s.getMean(), u.getMean(), tolerance);
        TestUtils.assertEquals("min", s.getMin(), u.getMin(), tolerance);
        TestUtils.assertEquals("max", s.getMax(), u.getMax(), tolerance);
    }

    @Test
    public void testSetterIllegalState() {
        SummaryStatistics u = createSummaryStatistics();
        u.addValue(1);
        u.addValue(3);
        try {
            u.setMeanImpl(new SumStat());
            Assert.fail("Expecting MathIllegalStateException");
        } catch (MathIllegalStateException ex) {
            // expected
        }
    }
}
