package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfYear_ESTest_test18 extends DayOfYear_ESTest_scaffolding {

    // The EvoSuite mock clock is fixed to a date whose day-of-year is 45 (February 14).
    @Test(timeout = 4000)
    public void testNowReturnsDay45WithMockedClock() throws Throwable {
        DayOfYear today = DayOfYear.now();
        int dayOfYear = today.getValue();
        assertEquals("DayOfYear.now() with mocked clock should return day 45", 45, dayOfYear);
    }
}
