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
     * A day-of-year must lie between 1 and 366. Requesting a negative
     * day-of-year is out of range, so {@link DayOfYear#of(int)} is expected
     * to reject it with a {@link DateTimeException}.
     */
    @Test(timeout = 4000)
    public void of_withNegativeDayOfYear_throwsDateTimeException() throws Throwable {
        int invalidDayOfYear = -408;
        try {
            DayOfYear.of(invalidDayOfYear);
            fail("Expected DateTimeException for invalid day-of-year: " + invalidDayOfYear);
        } catch (DateTimeException expected) {
            // Message thrown by DayOfYear: "Invalid value for DayOfYear: -408"
            verifyException("org.threeten.extra.DayOfYear", expected);
        }
    }
}
