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

    /**
     * Verifies that setting the variance implementation after data has already been added
     * throws a RuntimeException, because the implementation can only be configured before
     * any values are accumulated.
     */
    @Test(timeout = 4000)
    public void test_setVarianceImpl_afterDataAdded_throwsRuntimeException() throws Throwable {
        SummaryStatistics stats = new SummaryStatistics();

        // Add a value so the statistics object already has accumulated data
        stats.addValue(0.0);

        // Attempting to change the variance implementation after data has been added
        // should fail because the state is already initialized
        Statistics.StorelessMax maxAsVarianceImpl = Statistics.StorelessMax.create();
        try {
            stats.setVarianceImpl(maxAsVarianceImpl);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            //
            // 1 values have been added before statistic is configured
            //
            verifyException("org.apache.commons.math4.legacy.stat.descriptive.SummaryStatistics", e);
        }
    }
}
