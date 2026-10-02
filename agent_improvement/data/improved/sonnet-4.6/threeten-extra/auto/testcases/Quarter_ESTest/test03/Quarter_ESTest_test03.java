package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Month;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test03 extends Quarter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_Q3_firstMonth_returnsJuly() throws Throwable {
        Quarter q3 = Quarter.Q3;
        Month firstMonthOfQ3 = q3.firstMonth();
        assertEquals(Month.JULY, firstMonthOfQ3);
    }
}
