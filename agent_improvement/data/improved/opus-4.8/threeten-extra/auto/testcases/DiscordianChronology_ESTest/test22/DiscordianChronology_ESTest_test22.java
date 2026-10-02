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
public class DiscordianChronology_ESTest_test22 extends DiscordianChronology_ESTest_scaffolding {

    /**
     * Verifies that {@link DiscordianChronology#dateYearDay(int, int)} rejects a
     * proleptic-year of 0. The valid year range is 1 - 999999, so year 0 is out
     * of range and the chronology must throw a {@link DateTimeException} raised by
     * the underlying {@link java.time.temporal.ValueRange} validation.
     */
    @Test(timeout = 4000)
    public void dateYearDay_withYearZero_throwsDateTimeException() throws Throwable {
        DiscordianChronology chronology = new DiscordianChronology();

        int invalidProlepticYear = 0;
        int dayOfYear = 0;

        try {
            chronology.dateYearDay(invalidProlepticYear, dayOfYear);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            // Invalid value for Year (valid values 1 - 999999): 0
            verifyException("java.time.temporal.ValueRange", e);
        }
    }
}
