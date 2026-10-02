package org.apache.commons.math4.legacy.stat.descriptive;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SummaryStatistics_ESTest_test13 extends SummaryStatistics_ESTest_scaffolding {

    // An empty SummaryStatistics (no data added) should return NaN for sum
    // because there are no values to sum.
    @Test(timeout = 4000)
    public void test_getSummary_returnsNaNForSum_whenNoDataAdded() throws Throwable {
        SummaryStatistics emptyStats = new SummaryStatistics();
        StatisticalSummary summary = emptyStats.getSummary();
        assertEquals(Double.NaN, summary.getSum(), 0.01);
    }
}
