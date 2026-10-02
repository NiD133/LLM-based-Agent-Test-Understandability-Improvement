package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test09 extends Quarter_ESTest_scaffolding {

    /**
     * Verifies that Q2 (April–June) always spans 91 days, even in a leap year.
     * Unlike Q1, the length of Q2 is unaffected by the leapYear flag.
     */
    @Test(timeout = 4000)
    public void test09_Q2LengthIsAlways91DaysRegardlessOfLeapYear() throws Throwable {
        int daysInQ2DuringLeapYear = Quarter.Q2.length(true);
        assertEquals("Q2 should always have 91 days, even in a leap year", 91, daysInQ2DuringLeapYear);
    }
}
