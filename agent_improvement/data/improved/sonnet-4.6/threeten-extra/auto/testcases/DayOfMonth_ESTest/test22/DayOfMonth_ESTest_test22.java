package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfMonth_ESTest_test22 extends DayOfMonth_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void testNowReturnsCurrentDayOfMonthFromSystemClock() throws Throwable {
        // EvoSuite mocks the system clock to a fixed date where the day-of-month is 14
        DayOfMonth dayOfMonth = DayOfMonth.now();
        int dayValue = dayOfMonth.getValue();
        assertEquals(14, dayValue);
    }
}
