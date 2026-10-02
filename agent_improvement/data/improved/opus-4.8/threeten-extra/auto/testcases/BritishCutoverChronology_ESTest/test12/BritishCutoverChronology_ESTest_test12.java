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
public class BritishCutoverChronology_ESTest_test12 extends BritishCutoverChronology_ESTest_scaffolding {

    /**
     * Verifies that creating a date from a year and day-of-year rejects an
     * out-of-range day-of-year. A day-of-year of -1002 is far outside the
     * valid range (1 - 365/366), so a DateTimeException is expected.
     */
    @Test(timeout = 4000)
    public void dateYearDayWithInvalidDayOfYearThrowsDateTimeException() throws Throwable {
        BritishCutoverChronology chronology = BritishCutoverChronology.INSTANCE;

        int invalidProlepticYear = -1002;
        int invalidDayOfYear = -1002;

        try {
            chronology.dateYearDay(invalidProlepticYear, invalidDayOfYear);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            // Invalid value for DayOfYear (valid values 1 - 365/366): -1002
            verifyException("java.time.temporal.ValueRange", e);
        }
    }
}
