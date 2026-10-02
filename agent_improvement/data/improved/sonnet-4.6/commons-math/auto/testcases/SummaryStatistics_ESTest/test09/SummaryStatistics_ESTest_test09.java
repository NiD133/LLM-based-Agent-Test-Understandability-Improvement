package org.apache.commons.math4.legacy.stat.descriptive;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SummaryStatistics_ESTest_test09 extends SummaryStatistics_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test09_getMaxImpl_returnsNullWhenNoCustomImplSet() throws Throwable {
        SummaryStatistics stats = new SummaryStatistics();

        // By default, no custom max implementation is registered, so getMaxImpl() returns null
        StorelessUnivariateStatistic maxImpl = stats.getMaxImpl();

        assertNull(maxImpl);
    }
}
