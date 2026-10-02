package org.apache.commons.math4.legacy.stat.descriptive;

import org.apache.commons.math4.legacy.stat.descriptive.summary.Sum;
import org.junit.Assert;
import org.junit.Test;

/**
 * Verifies that a custom statistic implementation can be injected into
 * {@link SummaryStatistics} via {@code setMeanImpl(...)}.
 *
 * <p>The injected {@code SumStat} computes a sum rather than a mean, so once it
 * is installed as the "mean" implementation, {@link SummaryStatistics#getMean()}
 * returns the running sum of the added values instead of their average. The test
 * also confirms that {@code clear()} resets the accumulated values and that a new
 * implementation may be injected again after a {@code clear()}.
 */
public class SummaryStatisticsTest_testSetterInjection {

    /** Tolerance for floating-point sum comparisons. */
    private static final double TOLERANCE = 1E-14;

    /**
     * A statistic that accumulates a sum. It is injected in place of the mean
     * implementation so that {@code getMean()} reports the running sum.
     */
    private static final class SumStat extends Sum {
    }

    @Test
    public void testSetterInjection() {
        SummaryStatistics stats = new SummaryStatistics();

        // Inject SumStat as the mean implementation, so getMean() returns the sum.
        stats.setMeanImpl(new SumStat());

        // Sum of {1, 3} = 4.
        stats.addValue(1);
        stats.addValue(3);
        Assert.assertEquals(4, stats.getMean(), TOLERANCE);

        // clear() discards the accumulated values; sum of {1, 2} = 3.
        stats.clear();
        stats.addValue(1);
        stats.addValue(2);
        Assert.assertEquals(3, stats.getMean(), TOLERANCE);

        // Re-injecting an implementation is allowed once the statistics are cleared.
        stats.clear();
        stats.setMeanImpl(new SumStat());
    }
}
