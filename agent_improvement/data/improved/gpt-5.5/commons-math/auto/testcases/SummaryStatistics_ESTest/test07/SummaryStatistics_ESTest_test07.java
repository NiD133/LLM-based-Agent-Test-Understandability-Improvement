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

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        SummaryStatistics statistics = new SummaryStatistics();
        Statistics.StorelessGeometricMean sharedGeometricMean = Statistics.StorelessGeometricMean.create();

        statistics.setSumImpl(sharedGeometricMean);
        statistics.setMaxImpl(sharedGeometricMean);
        statistics.setVarianceImpl(sharedGeometricMean);
        statistics.setMinImpl(sharedGeometricMean);
        statistics.setMeanImpl(sharedGeometricMean);

        SummaryStatistics.copy(statistics, statistics);

        assertEquals(0L, statistics.getN());
    }
}
