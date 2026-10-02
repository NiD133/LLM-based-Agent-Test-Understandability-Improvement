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
public class DayOfYear_ESTest_test00 extends DayOfYear_ESTest_scaffolding {

    /**
     * DayOfYear only accepts day-of-year values from 1 to 366.
     * Requesting a negative day such as -408 must be rejected with a
     * DateTimeException thrown by DayOfYear.of.
     */
    @Test(timeout = 4000)
    public void of_withNegativeDayOfYear_throwsDateTimeException() throws Throwable {
        int invalidDayOfYear = -408;
        try {
            DayOfYear.of(invalidDayOfYear);
            fail("Expected DateTimeException for invalid day-of-year: " + invalidDayOfYear);
        } catch (DateTimeException expected) {
            // Thrown by DayOfYear.of when the value is outside the 1..366 range.
            verifyException("org.threeten.extra.DayOfYear", expected);
        }
    }
}
