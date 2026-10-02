package org.threeten.extra;

import static org.junit.Assert.assertEquals;

import java.time.Month;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test05 extends Quarter_ESTest_scaffolding {

    /**
     * Verifies that the fourth quarter (Q4) reports October as its first month,
     * since Q4 spans October, November and December.
     */
    @Test(timeout = 4000)
    public void firstMonthOfFourthQuarterIsOctober() throws Throwable {
        Month firstMonthOfQ4 = Quarter.Q4.firstMonth();

        assertEquals(Month.OCTOBER, firstMonthOfQ4);
    }
}
