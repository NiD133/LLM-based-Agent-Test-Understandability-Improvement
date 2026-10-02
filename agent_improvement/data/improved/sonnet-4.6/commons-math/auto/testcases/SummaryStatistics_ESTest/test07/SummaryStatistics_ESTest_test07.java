package org.apache.commons.math4.legacy.stat.descriptive;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SummaryStatistics_ESTest_test07 extends SummaryStatistics_ESTest_scaffolding {

    /**
     * Verifies that copying a SummaryStatistics instance onto itself preserves the count (N)
     * when all internal statistic implementations have been replaced with a shared
     * StorelessGeometricMean instance. N should remain 0 since no values were added.
     */
    @Test(timeout = 4000)
    public void test07() throws Throwable {
        SummaryStatistics stats = new SummaryStatistics();

        // Replace all statistic implementations with a single shared geometric mean instance.
        // This exercises the setXxxImpl API and ensures copy() handles shared implementations.
        Statistics.StorelessGeometricMean geometricMean = Statistics.StorelessGeometricMean.create();
        stats.setSumImpl(geometricMean);
        stats.setMaxImpl(geometricMean);
        stats.setVarianceImpl(geometricMean);
        stats.setMinImpl(geometricMean);
        stats.setMeanImpl(geometricMean);

        // Copy the instance onto itself — a self-copy should be a no-op.
        SummaryStatistics.copy(stats, stats);

        // No values were added, so the count must still be zero.
        assertEquals(0L, stats.getN());
    }
}
