package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test08 extends Quarter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_Q3_lengthInLeapYear_returns92Days() throws Throwable {
        // Q3 (July-September) always has 92 days, regardless of whether it is a leap year
        int daysInQ3LeapYear = Quarter.Q3.length(true);
        assertEquals(92, daysInQ3LeapYear);
    }
}
