package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test10 extends Quarter_ESTest_scaffolding {

    /**
     * Q1 (January to March) spans 91 days in a leap year,
     * since February contributes 29 days instead of 28.
     */
    @Test(timeout = 4000)
    public void q1LengthInLeapYearIs91Days() throws Throwable {
        Quarter firstQuarter = Quarter.Q1;

        int lengthInDays = firstQuarter.length(true);

        assertEquals(91, lengthInDays);
    }
}
