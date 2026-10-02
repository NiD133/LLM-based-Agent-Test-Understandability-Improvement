package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfMonth_ESTest_test22 extends DayOfMonth_ESTest_scaffolding {

    /**
     * DayOfMonth.now() reads the current date from the (mocked) system clock.
     * Under EvoSuite's mocked time, the clock is fixed so that the
     * day-of-month is 14, which getValue() should report back.
     */
    @Test(timeout = 4000)
    public void now_returnsDayOfMonthFromMockedSystemClock() throws Throwable {
        DayOfMonth today = DayOfMonth.now();

        int dayOfMonthValue = today.getValue();

        assertEquals(14, dayOfMonthValue);
    }
}
