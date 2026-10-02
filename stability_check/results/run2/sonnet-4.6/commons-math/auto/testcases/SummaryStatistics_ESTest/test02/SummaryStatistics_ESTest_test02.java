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

    // Verifies that replacing all statistic implementations with a shared StorelessGeometricMean
    // and then calling clear() resets the sample count to zero.
    @Test(timeout = 4000)
    public void test_clearResetsCountToZeroAfterSettingAllImplsToStorelessGeometricMean() throws Throwable {
        SummaryStatistics stats = new SummaryStatistics();
        Statistics.StorelessGeometricMean geometricMeanImpl = Statistics.StorelessGeometricMean.create();

        stats.setSumImpl(geometricMeanImpl);
        stats.setMaxImpl(geometricMeanImpl);
        stats.setVarianceImpl(geometricMeanImpl);
        stats.setMinImpl(geometricMeanImpl);
        stats.setMeanImpl(geometricMeanImpl);

        stats.clear();

        assertEquals(0L, stats.getN());
    }
}
