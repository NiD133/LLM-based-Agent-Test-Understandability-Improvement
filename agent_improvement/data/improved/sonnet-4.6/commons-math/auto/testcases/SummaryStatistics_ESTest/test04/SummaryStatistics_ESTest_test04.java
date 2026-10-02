package org.apache.commons.math4.legacy.stat.descriptive;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SummaryStatistics_ESTest_test04 extends SummaryStatistics_ESTest_scaffolding {

    /**
     * Verifies that adding a NaN value to a SummaryStatistics instance with a custom
     * StorelessSum implementation increments the count (N) to 1, confirming that NaN
     * is treated as a valid observation for counting purposes.
     */
    @Test(timeout = 4000)
    public void test_addNaNValue_withCustomSumImpl_incrementsCount() throws Throwable {
        SummaryStatistics stats = new SummaryStatistics();

        // Replace the default sum implementation with a custom storeless sum
        Statistics.StorelessSum customSumImpl = Statistics.StorelessSum.create();
        stats.setSumImpl(customSumImpl);

        // Add a NaN value — NaN is still counted as an observation
        stats.addValue(Double.NaN);

        // Ensure toString() does not throw when NaN is part of the statistics
        stats.toString();

        // Exactly one value has been added, so the count must be 1
        assertEquals(1L, stats.getN());
    }
}
