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
     * Verifies that clearing a SummaryStatistics resets its observation count to
     * zero, even after every storeless statistic implementation has been
     * overridden with a custom one.
     */
    @Test(timeout = 4000)
    public void clearResetsCountToZeroAfterCustomImplsAreSet() throws Throwable {
        SummaryStatistics summaryStatistics = new SummaryStatistics();

        // Use one shared storeless implementation for all configurable statistics.
        Statistics.StorelessGeometricMean geometricMeanImpl =
                Statistics.StorelessGeometricMean.create();
        summaryStatistics.setSumImpl(geometricMeanImpl);
        summaryStatistics.setMaxImpl(geometricMeanImpl);
        summaryStatistics.setVarianceImpl(geometricMeanImpl);
        summaryStatistics.setMinImpl(geometricMeanImpl);
        summaryStatistics.setMeanImpl(geometricMeanImpl);

        summaryStatistics.clear();

        assertEquals(0L, summaryStatistics.getN());
    }
}
