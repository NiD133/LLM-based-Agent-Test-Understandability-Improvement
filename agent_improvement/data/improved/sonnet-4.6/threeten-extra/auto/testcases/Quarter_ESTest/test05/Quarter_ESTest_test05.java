package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Month;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test05 extends Quarter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_Q4_firstMonth_returnsOctober() throws Throwable {
        Quarter q4 = Quarter.Q4;
        Month firstMonthOfQ4 = q4.firstMonth();
        assertEquals(Month.OCTOBER, firstMonthOfQ4);
    }
}
