package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.time.Month;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test04 extends Quarter_ESTest_scaffolding {

    /**
     * Verifies that the second quarter (Q2) begins in April, since Q2 spans
     * April to June.
     */
    @Test(timeout = 4000)
    public void firstMonthOfQ2IsApril() throws Throwable {
        Month firstMonthOfQ2 = Quarter.Q2.firstMonth();

        assertEquals(Month.APRIL, firstMonthOfQ2);
    }
}
