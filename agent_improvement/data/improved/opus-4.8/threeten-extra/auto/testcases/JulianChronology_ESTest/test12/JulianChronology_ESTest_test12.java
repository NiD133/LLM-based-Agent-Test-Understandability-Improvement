package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JulianChronology_ESTest_test12 extends JulianChronology_ESTest_scaffolding {

    /**
     * Creating a Julian date with a month-of-year outside the valid range (1 - 12)
     * must be rejected. Here the month is 63, so {@code date} should fail while
     * validating the month value against its {@link java.time.temporal.ValueRange}.
     */
    @Test(timeout = 4000)
    public void dateWithOutOfRangeMonthThrowsDateTimeException() throws Throwable {
        JulianChronology julianChronology = JulianChronology.INSTANCE;

        int invalidMonth = 63;
        try {
            julianChronology.date(63, invalidMonth, 63);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            // Invalid value for MonthOfYear (valid values 1 - 12): 63
            verifyException("java.time.temporal.ValueRange", e);
        }
    }
}
