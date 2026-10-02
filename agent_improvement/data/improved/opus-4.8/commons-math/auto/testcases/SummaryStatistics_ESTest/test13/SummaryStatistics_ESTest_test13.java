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

    private static final double DELTA = 0.01;

    /**
     * A freshly created SummaryStatistics has no observed values, so the
     * snapshot returned by getSummary() should report a sum of NaN.
     */
    @Test(timeout = 4000)
    public void summaryOfEmptyStatisticsHasNaNSum() throws Throwable {
        SummaryStatistics emptyStatistics = new SummaryStatistics();

        StatisticalSummary summary = emptyStatistics.getSummary();

        assertEquals(Double.NaN, summary.getSum(), DELTA);
    }
}
