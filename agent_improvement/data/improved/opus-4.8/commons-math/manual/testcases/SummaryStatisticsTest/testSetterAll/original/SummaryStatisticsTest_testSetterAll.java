package org.apache.commons.math4.legacy.stat.descriptive;

import org.apache.commons.math4.legacy.TestUtils;
import org.apache.commons.math4.legacy.exception.MathIllegalArgumentException;
import org.apache.commons.math4.legacy.exception.MathIllegalStateException;
import org.apache.commons.math4.legacy.stat.StatUtils;
import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class SummaryStatisticsTest_testSetterAll {

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

    /**
     * Test when all the default implementations are overridden.
     */
    @Test
    public void testSetterAll() {
        final SummaryStatistics u = createSummaryStatistics();
        Assertions.assertThrows(NullPointerException.class, () -> u.setSumImpl(null));
        Assertions.assertThrows(NullPointerException.class, () -> u.setMinImpl(null));
        Assertions.assertThrows(NullPointerException.class, () -> u.setMaxImpl(null));
        Assertions.assertThrows(NullPointerException.class, () -> u.setMeanImpl(null));
        Assertions.assertThrows(NullPointerException.class, () -> u.setVarianceImpl(null));
        // Distinct implementations
        u.setSumImpl(new SumStat(1));
        u.setMinImpl(new SumStat(2));
        u.setMaxImpl(new SumStat(3));
        u.setMeanImpl(new SumStat(4));
        u.setVarianceImpl(new SumStat(5));
        u.addValue(1);
        Assertions.assertEquals(2, u.getSum());
        Assertions.assertEquals(3, u.getMin());
        Assertions.assertEquals(4, u.getMax());
        Assertions.assertEquals(5, u.getMean());
        Assertions.assertEquals(6, u.getVariance());
        // Test getters return the correct implementation
        Assertions.assertEquals(2, u.getSumImpl().getResult());
        Assertions.assertEquals(3, u.getMinImpl().getResult());
        Assertions.assertEquals(4, u.getMaxImpl().getResult());
        Assertions.assertEquals(5, u.getMeanImpl().getResult());
        Assertions.assertEquals(6, u.getVarianceImpl().getResult());
        // Test copy
        final SummaryStatistics v = u.copy();
        Assertions.assertEquals(2, v.getSum());
        Assertions.assertEquals(3, v.getMin());
        Assertions.assertEquals(4, v.getMax());
        Assertions.assertEquals(5, v.getMean());
        Assertions.assertEquals(6, v.getVariance());
        // Test the return NaN contract when empty
        u.clear();
        Assertions.assertEquals(Double.NaN, u.getSum());
        Assertions.assertEquals(Double.NaN, u.getMin());
        Assertions.assertEquals(Double.NaN, u.getMax());
        Assertions.assertEquals(Double.NaN, u.getMean());
        Assertions.assertEquals(Double.NaN, u.getVariance());
        // Test refilling
        u.addValue(1);
        Assertions.assertEquals(1, u.getSum());
        Assertions.assertEquals(1, u.getMin());
        Assertions.assertEquals(1, u.getMax());
        Assertions.assertEquals(1, u.getMean());
        Assertions.assertEquals(1, u.getVariance());
    }
}
