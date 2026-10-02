package org.apache.commons.math4.legacy.stat.descriptive;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

/**
 * Tests that all five statistic implementations in SummaryStatistics can be
 * replaced simultaneously via the setter API (setSumImpl, setMinImpl, etc.).
 *
 * SumStat(seed) is a stub implementation whose getResult() returns
 * (seed + accumulated values). After one addValue(1) call, SumStat(n) returns
 * n+1. After clear(), the accumulated total resets to 0, so the next
 * addValue(1) returns 1 regardless of the original seed.
 */
public class SummaryStatisticsTest_testSetterAll {

    // Distinct seeds for each statistic slot so we can tell them apart.
    private static final int SUM_SEED      = 1;
    private static final int MIN_SEED      = 2;
    private static final int MAX_SEED      = 3;
    private static final int MEAN_SEED     = 4;
    private static final int VARIANCE_SEED = 5;

    // After addValue(1): SumStat(seed) returns seed + 1.
    private static final double SUM_AFTER_ADD      = SUM_SEED + 1;
    private static final double MIN_AFTER_ADD      = MIN_SEED + 1;
    private static final double MAX_AFTER_ADD      = MAX_SEED + 1;
    private static final double MEAN_AFTER_ADD     = MEAN_SEED + 1;
    private static final double VARIANCE_AFTER_ADD = VARIANCE_SEED + 1;

    // After clear() + addValue(1): the seed resets, so all results are 1.
    private static final double RESULT_AFTER_REFILL = 1;

    protected SummaryStatistics createSummaryStatistics() {
        return new SummaryStatistics();
    }

    /**
     * Test when all the default implementations are overridden.
     */
    @Test
    public void testSetterAll() {
        final SummaryStatistics u = createSummaryStatistics();

        // Setting any implementation to null must throw NullPointerException.
        Assertions.assertThrows(NullPointerException.class, () -> u.setSumImpl(null));
        Assertions.assertThrows(NullPointerException.class, () -> u.setMinImpl(null));
        Assertions.assertThrows(NullPointerException.class, () -> u.setMaxImpl(null));
        Assertions.assertThrows(NullPointerException.class, () -> u.setMeanImpl(null));
        Assertions.assertThrows(NullPointerException.class, () -> u.setVarianceImpl(null));

        // Install distinct stub implementations so each statistic returns a
        // uniquely identifiable value (seed + value) after accumulation.
        u.setSumImpl(new SumStat(SUM_SEED));
        u.setMinImpl(new SumStat(MIN_SEED));
        u.setMaxImpl(new SumStat(MAX_SEED));
        u.setMeanImpl(new SumStat(MEAN_SEED));
        u.setVarianceImpl(new SumStat(VARIANCE_SEED));

        u.addValue(1);

        // Each delegated accessor must return the stub's accumulated result.
        Assertions.assertEquals(SUM_AFTER_ADD,      u.getSum());
        Assertions.assertEquals(MIN_AFTER_ADD,      u.getMin());
        Assertions.assertEquals(MAX_AFTER_ADD,      u.getMax());
        Assertions.assertEquals(MEAN_AFTER_ADD,     u.getMean());
        Assertions.assertEquals(VARIANCE_AFTER_ADD, u.getVariance());

        // Getters for the implementation objects must expose the same stubs.
        Assertions.assertEquals(SUM_AFTER_ADD,      u.getSumImpl().getResult());
        Assertions.assertEquals(MIN_AFTER_ADD,      u.getMinImpl().getResult());
        Assertions.assertEquals(MAX_AFTER_ADD,      u.getMaxImpl().getResult());
        Assertions.assertEquals(MEAN_AFTER_ADD,     u.getMeanImpl().getResult());
        Assertions.assertEquals(VARIANCE_AFTER_ADD, u.getVarianceImpl().getResult());

        // A copy must carry the same accumulated state as the original.
        final SummaryStatistics v = u.copy();
        Assertions.assertEquals(SUM_AFTER_ADD,      v.getSum());
        Assertions.assertEquals(MIN_AFTER_ADD,      v.getMin());
        Assertions.assertEquals(MAX_AFTER_ADD,      v.getMax());
        Assertions.assertEquals(MEAN_AFTER_ADD,     v.getMean());
        Assertions.assertEquals(VARIANCE_AFTER_ADD, v.getVariance());

        // After clear(), every statistic must report NaN (no data).
        u.clear();
        Assertions.assertEquals(Double.NaN, u.getSum());
        Assertions.assertEquals(Double.NaN, u.getMin());
        Assertions.assertEquals(Double.NaN, u.getMax());
        Assertions.assertEquals(Double.NaN, u.getMean());
        Assertions.assertEquals(Double.NaN, u.getVariance());

        // After refilling with addValue(1), seeds have been reset so each
        // statistic accumulates from 0 and returns 1.
        u.addValue(1);
        Assertions.assertEquals(RESULT_AFTER_REFILL, u.getSum());
        Assertions.assertEquals(RESULT_AFTER_REFILL, u.getMin());
        Assertions.assertEquals(RESULT_AFTER_REFILL, u.getMax());
        Assertions.assertEquals(RESULT_AFTER_REFILL, u.getMean());
        Assertions.assertEquals(RESULT_AFTER_REFILL, u.getVariance());
    }
}
