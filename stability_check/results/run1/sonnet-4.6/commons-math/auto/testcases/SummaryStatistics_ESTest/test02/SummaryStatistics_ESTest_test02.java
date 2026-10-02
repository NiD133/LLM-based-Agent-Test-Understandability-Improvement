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
     * Verifies that after replacing all statistic implementations with a shared StorelessGeometricMean
     * and then calling clear(), the observation count resets to zero.
     */
    @Test(timeout = 4000)
    public void test02_clearResetsCountAfterCustomImplementationsSet() throws Throwable {
        SummaryStatistics stats = new SummaryStatistics();

        // Use a single geometric mean instance as the implementation for all statistics
        Statistics.StorelessGeometricMean geometricMeanImpl = Statistics.StorelessGeometricMean.create();
        stats.setSumImpl(geometricMeanImpl);
        stats.setMaxImpl(geometricMeanImpl);
        stats.setVarianceImpl(geometricMeanImpl);
        stats.setMinImpl(geometricMeanImpl);
        stats.setMeanImpl(geometricMeanImpl);

        // After clear(), the count of observations should be reset to zero
        stats.clear();
        assertEquals(0L, stats.getN());
    }
}
