package org.apache.commons.math4.legacy.stat.descriptive;

import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Assert;
import org.junit.Test;

public class SummaryStatisticsTest_testStats {

    private static final double FIRST_VALUE = 1;
    private static final float SECOND_VALUE = 2;
    private static final long THIRD_VALUE = 2;
    private static final int FOURTH_VALUE = 3;

    private static final double EXPECTED_MEAN = 2;
    private static final double EXPECTED_SUM = 8;
    private static final double EXPECTED_VARIANCE = 0.666666666666666666667;
    private static final double EXPECTED_STANDARD_DEVIATION = JdkMath.sqrt(EXPECTED_VARIANCE);
    private static final double EXPECTED_COUNT = 4;
    private static final double EXPECTED_MINIMUM = 1;
    private static final double EXPECTED_MAXIMUM = 3;
    private static final double TOLERANCE = 10E-15;

    protected SummaryStatistics createSummaryStatistics() {
        return new SummaryStatistics();
    }

    /**
     * Verifies that SummaryStatistics reports the expected values for the data
     * set {1, 2, 2, 3}, then resets to an empty count after clear().
     */
    @Test
    public void testStats() {
        SummaryStatistics statistics = createSummaryStatistics();

        Assert.assertEquals("total count", 0, statistics.getN(), TOLERANCE);

        statistics.addValue(FIRST_VALUE);
        statistics.addValue(SECOND_VALUE);
        statistics.addValue(THIRD_VALUE);
        statistics.addValue(FOURTH_VALUE);

        Assert.assertEquals("N", EXPECTED_COUNT, statistics.getN(), TOLERANCE);
        Assert.assertEquals("sum", EXPECTED_SUM, statistics.getSum(), TOLERANCE);
        Assert.assertEquals("var", EXPECTED_VARIANCE, statistics.getVariance(), TOLERANCE);
        Assert.assertEquals("std", EXPECTED_STANDARD_DEVIATION, statistics.getStandardDeviation(), TOLERANCE);
        Assert.assertEquals("mean", EXPECTED_MEAN, statistics.getMean(), TOLERANCE);
        Assert.assertEquals("min", EXPECTED_MINIMUM, statistics.getMin(), TOLERANCE);
        Assert.assertEquals("max", EXPECTED_MAXIMUM, statistics.getMax(), TOLERANCE);

        statistics.clear();

        Assert.assertEquals("total count", 0, statistics.getN(), TOLERANCE);
    }
}
