package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test24 extends Quarter_ESTest_scaffolding {

    /**
     * Subtracting zero quarters should leave the quarter unchanged.
     */
    @Test(timeout = 4000)
    public void minusZeroQuarters_returnsSameQuarter() throws Throwable {
        Quarter unchanged = Quarter.Q4.minus(0L);

        assertEquals(Quarter.Q4, unchanged);
    }
}
