package org.apache.commons.math4.legacy.stat.descriptive;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SummaryStatistics_ESTest_test05 extends SummaryStatistics_ESTest_scaffolding {

    /**
     * A freshly constructed SummaryStatistics has no custom mean implementation
     * installed, so getMeanImpl() should report null until one is supplied.
     */
    @Test(timeout = 4000)
    public void getMeanImplIsNullForNewSummaryStatistics() throws Throwable {
        SummaryStatistics summaryStatistics = new SummaryStatistics();

        StorelessUnivariateStatistic meanImpl = summaryStatistics.getMeanImpl();

        assertNull(meanImpl);
    }
}
