package org.apache.commons.math4.legacy.stat.descriptive;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SummaryStatistics_ESTest_test00 extends SummaryStatistics_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        SummaryStatistics statistics = new SummaryStatistics();
        Statistics.StorelessMax replacementVariance = Statistics.StorelessMax.create();

        statistics.addValue(0.0);

        try {
            statistics.setVarianceImpl(replacementVariance);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            //
            // 1 values have been added before statistic is configured
            //
            verifyException("org.apache.commons.math4.legacy.stat.descriptive.SummaryStatistics", e);
        }
    }
}
