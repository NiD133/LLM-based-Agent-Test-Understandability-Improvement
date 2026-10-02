package org.apache.commons.math4.legacy.stat.descriptive;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SummaryStatistics_ESTest_test01 extends SummaryStatistics_ESTest_scaffolding {

    /**
     * Verifies that calling clear() on a freshly created SummaryStatistics
     * leaves the sample count (N) at zero.
     */
    @Test(timeout = 4000)
    public void clearOnEmptyStatisticsKeepsCountAtZero() throws Throwable {
        SummaryStatistics statistics = new SummaryStatistics();

        statistics.clear();

        long expectedSampleCount = 0L;
        assertEquals(expectedSampleCount, statistics.getN());
    }
}
