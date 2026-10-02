package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test18 extends Days_ESTest_scaffolding {

    /**
     * A negative number of weeks is converted to the equivalent (negative)
     * number of days, and {@link Days#isNegative()} reports it as negative.
     */
    @Test(timeout = 4000)
    public void negativeWeeksGiveNegativeDays() throws Throwable {
        int weeks = -3574;
        Days days = Days.ofWeeks(weeks);

        int expectedDays = weeks * 7; // 7 days per week => -25018 days
        assertEquals(expectedDays, days.getAmount());
        assertTrue(days.isNegative());
    }
}
