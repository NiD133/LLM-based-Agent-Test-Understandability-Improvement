package org.apache.commons.math4.legacy.stat;

import org.junit.Assert;
import org.junit.Test;

public class FrequencyTest_testPcts {

    private static final long ONE_LONG   = 1L;
    private static final long TWO_LONG   = 2L;
    private static final long THREE_LONG = 3L;

    /** Acceptable floating-point comparison tolerance (1e-14). */
    private static final double TOLERANCE = 10E-15d;

    /**
     * Verifies that {@link Frequency#getPct} and {@link Frequency#getCumPct}
     * return correct relative and cumulative percentages after adding values
     * {1, 2, 3, 3} (four observations total).
     *
     * Expected distribution:
     *   1 → 1 occurrence  → 25 % relative,  25 % cumulative
     *   2 → 1 occurrence  → 25 % relative,  50 % cumulative
     *   3 → 2 occurrences → 50 % relative, 100 % cumulative
     */
    @Test
    public void testPcts() {
        Frequency<Long> f = new Frequency<>();
        f.addValue(ONE_LONG);
        f.addValue(TWO_LONG);
        f.addValue(THREE_LONG);
        f.addValue(THREE_LONG);

        Assert.assertEquals("relative pct of 2 should be 0.25",
                0.25, f.getPct(TWO_LONG), TOLERANCE);
        Assert.assertEquals("cumulative pct up to 2 should be 0.50",
                0.50, f.getCumPct(TWO_LONG), TOLERANCE);
        Assert.assertEquals("cumulative pct up to 3 (last value) should be 1.0",
                1.0,  f.getCumPct(THREE_LONG), TOLERANCE);
    }
}
