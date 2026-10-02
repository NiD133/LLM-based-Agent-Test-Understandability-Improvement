package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test10 extends Quarter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_Q1_lengthInLeapYear_is91Days() throws Throwable {
        Quarter q1 = Quarter.Q1;
        int days = q1.length(true); // true = leap year
        assertEquals("Q1 in a leap year should have 91 days", 91, days);
    }
}
