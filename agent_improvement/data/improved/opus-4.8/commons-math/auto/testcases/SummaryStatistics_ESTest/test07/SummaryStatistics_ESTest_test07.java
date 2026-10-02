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
     * Copying a {@link SummaryStatistics} instance onto itself must be a safe
     * no-op: even after every pluggable statistic implementation has been
     * replaced with a custom one, copying source-to-source leaves the observed
     * sample count untouched. Since no values were ever added, the count stays 0.
     */
    @Test(timeout = 4000)
    public void copyOntoItselfKeepsSampleCountAtZero() throws Throwable {
        SummaryStatistics statistics = new SummaryStatistics();

        // Override every pluggable statistic with the same custom implementation.
        Statistics.StorelessGeometricMean geometricMean = Statistics.StorelessGeometricMean.create();
        statistics.setSumImpl(geometricMean);
        statistics.setMaxImpl(geometricMean);
        statistics.setVarianceImpl(geometricMean);
        statistics.setMinImpl(geometricMean);
        statistics.setMeanImpl(geometricMean);

        // Copying the instance onto itself should not corrupt its state.
        SummaryStatistics.copy(statistics, statistics);

        assertEquals("no values added, so the sample count must remain zero", 0L, statistics.getN());
    }
}
