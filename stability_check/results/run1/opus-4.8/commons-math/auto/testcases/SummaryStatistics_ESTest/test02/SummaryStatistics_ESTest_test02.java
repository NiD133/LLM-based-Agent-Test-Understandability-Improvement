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
     * zero, even after custom statistic implementations have been installed for
     * every aggregate (sum, max, variance, min and mean).
     */
    @Test(timeout = 4000)
    public void test02() throws Throwable {
        SummaryStatistics summaryStatistics = new SummaryStatistics();

        // Use a single storeless geometric-mean implementation for all aggregates.
        Statistics.StorelessGeometricMean geometricMeanImpl = Statistics.StorelessGeometricMean.create();
        summaryStatistics.setSumImpl(geometricMeanImpl);
        summaryStatistics.setMaxImpl(geometricMeanImpl);
        summaryStatistics.setVarianceImpl(geometricMeanImpl);
        summaryStatistics.setMinImpl(geometricMeanImpl);
        summaryStatistics.setMeanImpl(geometricMeanImpl);

        summaryStatistics.clear();

        assertEquals(0L, summaryStatistics.getN());
    }
}
