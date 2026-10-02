package org.apache.commons.math4.legacy.stat.descriptive;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SummaryStatistics_ESTest_test03 extends SummaryStatistics_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        SummaryStatistics summaryStatistics0 = new SummaryStatistics();
        summaryStatistics0.addValue(2536.6);
        summaryStatistics0.addValue((-4145.465349642632));
        double double0 = summaryStatistics0.getStandardDeviation();
        assertEquals(2L, summaryStatistics0.getN());
        assertEquals(4724.933721063963, double0, 0.01);
    }
}
