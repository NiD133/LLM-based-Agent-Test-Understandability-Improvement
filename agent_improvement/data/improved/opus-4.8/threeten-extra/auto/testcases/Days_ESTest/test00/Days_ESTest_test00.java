package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test00 extends Days_ESTest_scaffolding {

    /**
     * Two {@code Days} instances created from the same number of weeks should be
     * equal, and the underlying day amount should be the week count multiplied
     * by 7 (-3574 weeks = -25018 days).
     */
    @Test(timeout = 4000)
    public void ofWeeks_equalInstancesAndDayAmount() throws Throwable {
        int weeks = -3574;
        int expectedDays = weeks * 7; // -25018

        Days daysFromWeeks = Days.ofWeeks(weeks);
        Days sameDaysFromWeeks = Days.ofWeeks(weeks);

        assertTrue(daysFromWeeks.equals(sameDaysFromWeeks));
        assertEquals(expectedDays, sameDaysFromWeeks.getAmount());
    }
}
