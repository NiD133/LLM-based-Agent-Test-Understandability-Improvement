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

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        SummaryStatistics summaryStatistics0 = new SummaryStatistics();
        Statistics.StorelessGeometricMean statistics_StorelessGeometricMean0 = Statistics.StorelessGeometricMean.create();
        summaryStatistics0.setSumImpl(statistics_StorelessGeometricMean0);
        summaryStatistics0.setMaxImpl(statistics_StorelessGeometricMean0);
        summaryStatistics0.setVarianceImpl(statistics_StorelessGeometricMean0);
        summaryStatistics0.setMinImpl(statistics_StorelessGeometricMean0);
        summaryStatistics0.setMeanImpl(statistics_StorelessGeometricMean0);
        summaryStatistics0.clear();
        assertEquals(0L, summaryStatistics0.getN());
    }
}
