package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test08 extends Quarter_ESTest_scaffolding {

    // Q3 (July–September) always has 92 days regardless of whether the year is a leap year.
    private static final int Q3_LENGTH_IN_DAYS = 92;

    @Test(timeout = 4000)
    public void test_Q3_lengthInLeapYear_is92Days() throws Throwable {
        int actualDays = Quarter.Q3.length(true);
        assertEquals(Q3_LENGTH_IN_DAYS, actualDays);
    }
}
