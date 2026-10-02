package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.time.Month;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test06 extends Quarter_ESTest_scaffolding {

    /**
     * Verifies that the first month of the first quarter (Q1) is January,
     * as Q1 spans January through March.
     */
    @Test(timeout = 4000)
    public void firstMonthOfFirstQuarterIsJanuary() throws Throwable {
        Month firstMonthOfQ1 = Quarter.Q1.firstMonth();

        assertEquals(Month.JANUARY, firstMonthOfQ1);
    }
}
