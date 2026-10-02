package org.apache.commons.math4.legacy.stat.descriptive;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SummaryStatistics_ESTest_test04 extends SummaryStatistics_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        SummaryStatistics summaryStatistics0 = new SummaryStatistics();
        Statistics.StorelessSum statistics_StorelessSum0 = Statistics.StorelessSum.create();
        summaryStatistics0.setSumImpl(statistics_StorelessSum0);
        summaryStatistics0.addValue(Double.NaN);
        summaryStatistics0.toString();
        assertEquals(1L, summaryStatistics0.getN());
    }
}
