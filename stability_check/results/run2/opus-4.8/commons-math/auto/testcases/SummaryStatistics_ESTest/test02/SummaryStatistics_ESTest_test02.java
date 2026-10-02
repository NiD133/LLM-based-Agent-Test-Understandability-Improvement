package org.apache.commons.math4.legacy.stat.descriptive;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SummaryStatistics_ESTest_test02 extends SummaryStatistics_ESTest_scaffolding {

    /**
     * Verifies that clear() resets the observation count to zero, even after
     * every pluggable statistic implementation has been overridden with a
     * custom StorelessGeometricMean instance.
     */
    @Test(timeout = 4000)
    public void clearResetsCountToZeroAfterCustomImplsAreSet() throws Throwable {
        SummaryStatistics summaryStatistics = new SummaryStatistics();

        // Use a single custom implementation for every configurable statistic.
        Statistics.StorelessGeometricMean customStatistic = Statistics.StorelessGeometricMean.create();
        summaryStatistics.setSumImpl(customStatistic);
        summaryStatistics.setMaxImpl(customStatistic);
        summaryStatistics.setVarianceImpl(customStatistic);
        summaryStatistics.setMinImpl(customStatistic);
        summaryStatistics.setMeanImpl(customStatistic);

        summaryStatistics.clear();

        assertEquals(0L, summaryStatistics.getN());
    }
}
