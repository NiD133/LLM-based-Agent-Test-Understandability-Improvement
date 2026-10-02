package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test08 extends Quarter_ESTest_scaffolding {

    /**
     * Q3 (July to September) always spans 92 days, and this is unaffected by
     * whether the year is a leap year.
     */
    @Test(timeout = 4000)
    public void lengthOfQ3InLeapYearIs92Days() throws Throwable {
        int daysInQ3 = Quarter.Q3.length(true);

        assertEquals(92, daysInQ3);
    }
}
