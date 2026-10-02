package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfYear_ESTest_test00 extends DayOfYear_ESTest_scaffolding {

    /**
     * DayOfYear.of only accepts values in the range 1 to 366. A negative
     * day-of-year such as -408 is out of range, so the factory method is
     * expected to reject it by throwing a DateTimeException.
     */
    @Test(timeout = 4000)
    public void of_withNegativeDayOfYear_throwsDateTimeException() throws Throwable {
        int invalidDayOfYear = -408;
        try {
            DayOfYear.of(invalidDayOfYear);
            fail("Expected DateTimeException for invalid day-of-year: " + invalidDayOfYear);
        } catch (DateTimeException e) {
            // Message reads: "Invalid value for DayOfYear: -408"
            verifyException("org.threeten.extra.DayOfYear", e);
        }
    }
}
