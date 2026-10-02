package org.apache.commons.math4.legacy.stat.descriptive;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SummaryStatistics_ESTest_test04 extends SummaryStatistics_ESTest_scaffolding {

    /**
     * Verifies that adding a single value (here NaN) increments the observation
     * count to 1, even when a custom storeless sum implementation is plugged in,
     * and that toString() can be invoked on the resulting state.
     */
    @Test(timeout = 4000)
    public void addingSingleValueIncrementsCountToOne() throws Throwable {
        SummaryStatistics summaryStatistics = new SummaryStatistics();

        // Replace the default sum implementation with a custom storeless one.
        Statistics.StorelessSum customSumImpl = Statistics.StorelessSum.create();
        summaryStatistics.setSumImpl(customSumImpl);

        summaryStatistics.addValue(Double.NaN);

        // Should not throw, regardless of the NaN value held in the statistics.
        summaryStatistics.toString();

        // Exactly one observation has been recorded.
        assertEquals(1L, summaryStatistics.getN());
    }
}
