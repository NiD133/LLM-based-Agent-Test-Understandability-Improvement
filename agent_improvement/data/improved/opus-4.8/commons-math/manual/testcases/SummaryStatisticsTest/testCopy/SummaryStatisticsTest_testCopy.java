package org.apache.commons.math4.legacy.stat.descriptive;

import org.apache.commons.math4.legacy.stat.descriptive.summary.Sum;
import org.junit.Assert;
import org.junit.Test;

/**
 * Verifies the copy semantics of {@link SummaryStatistics}:
 * <ul>
 *   <li>the copy constructor produces a statistically identical instance,</li>
 *   <li>the copy is independent, so later updates applied to both yield the same results, and</li>
 *   <li>{@link SummaryStatistics#copy(SummaryStatistics, SummaryStatistics)} preserves the
 *       implementation type of the underlying statistics without sharing their instances.</li>
 * </ul>
 */
public class SummaryStatisticsTest_testCopy {

    /** Exact tolerance: copied statistics must match the source bit-for-bit. */
    private static final double EXACT = 0;

    protected SummaryStatistics createSummaryStatistics() {
        return new SummaryStatistics();
    }

    /** Asserts that two summaries expose identical statistical state. */
    private static void assertSameState(SummaryStatistics expected, SummaryStatistics actual) {
        Assert.assertArrayEquals(toStateArray(expected), toStateArray(actual), EXACT);
    }

    /** Snapshots the observable statistics of a summary into a comparable array. */
    private static double[] toStateArray(SummaryStatistics summary) {
        return new double[] {
            summary.getMean(),
            summary.getVariance(),
            summary.getN(),
            summary.getMax(),
            summary.getMin(),
            summary.getSum()
        };
    }

    @Test
    public void testCopy() {
        SummaryStatistics source = createSummaryStatistics();
        source.addValue(2d);
        source.addValue(1d);
        source.addValue(3d);
        source.addValue(4d);

        // The copy constructor must reproduce the source's state exactly.
        SummaryStatistics copy = new SummaryStatistics(source);
        assertSameState(source, copy);

        // The copy must be independent: feeding both the same extra values keeps them in sync.
        source.addValue(7d);
        source.addValue(9d);
        source.addValue(11d);
        source.addValue(23d);
        copy.addValue(7d);
        copy.addValue(9d);
        copy.addValue(11d);
        copy.addValue(23d);
        assertSameState(source, copy);

        // Copying via the static copy(...) must preserve the implementation type
        // while keeping the implementations as functionally distinct instances.
        source.clear();
        source.setSumImpl(new Sum());
        SummaryStatistics.copy(source, copy);
        Assert.assertNotSame(source.getSumImpl(), copy.getSumImpl());
        Assert.assertEquals(source.getSumImpl().getClass(), copy.getSumImpl().getClass());
    }
}
