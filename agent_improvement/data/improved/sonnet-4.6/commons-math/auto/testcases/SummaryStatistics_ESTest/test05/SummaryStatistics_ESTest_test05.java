package org.apache.commons.math4.legacy.stat.descriptive;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SummaryStatistics_ESTest_test05 extends SummaryStatistics_ESTest_scaffolding {

    // A freshly constructed SummaryStatistics has no custom mean implementation set,
    // so getMeanImpl() should return null before any override is supplied.
    @Test(timeout = 4000)
    public void test05_getMeanImpl_returnsNullOnNewInstance() throws Throwable {
        SummaryStatistics freshStats = new SummaryStatistics();

        StorelessUnivariateStatistic meanImpl = freshStats.getMeanImpl();

        assertNull(meanImpl);
    }
}
