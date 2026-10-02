package org.apache.commons.math4.legacy.stat.descriptive;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SummaryStatistics_ESTest_test12 extends SummaryStatistics_ESTest_scaffolding {

    // A freshly constructed SummaryStatistics has no data points yet, so every
    // statistical field should report NaN and the count (n) should be zero.
    private static final String EMPTY_STATS_STRING =
            "SummaryStatistics:\nn: 0\nmin: NaN\nmax: NaN\nsum: NaN\nmean: NaN\nvariance: NaN\nstandard deviation: NaN\n";

    @Test(timeout = 4000)
    public void test12_toStringOnEmptyStatisticsReturnsNaNForAllFields() throws Throwable {
        SummaryStatistics emptySummaryStatistics = new SummaryStatistics();

        String result = emptySummaryStatistics.toString();

        assertEquals(EMPTY_STATS_STRING, result);
    }
}
