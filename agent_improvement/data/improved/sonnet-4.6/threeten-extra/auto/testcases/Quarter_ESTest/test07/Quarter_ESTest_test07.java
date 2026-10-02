package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test07 extends Quarter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_Q1_lengthInStandardYear_is90Days() throws Throwable {
        boolean isLeapYear = false;
        int days = Quarter.Q1.length(isLeapYear);
        assertEquals(90, days);
    }
}
