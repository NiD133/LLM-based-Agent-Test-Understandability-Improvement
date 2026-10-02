package org.threeten.extra;

import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;

import java.time.DateTimeException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfMonth_ESTest_test00 extends DayOfMonth_ESTest_scaffolding {

    /**
     * DayOfMonth.of(int) accepts values from 1 to 31 only.
     * Passing 0 is below the valid range, so it must throw a DateTimeException
     * ("Invalid value for DayOfMonth: 0").
     */
    @Test(timeout = 4000)
    public void of_withDayBelowValidRange_throwsDateTimeException() throws Throwable {
        try {
            DayOfMonth.of(0);
            fail("Expected DateTimeException for day-of-month value 0");
        } catch (DateTimeException e) {
            // Exception is raised by DayOfMonth.of when the value is out of range.
            verifyException("org.threeten.extra.DayOfMonth", e);
        }
    }
}
