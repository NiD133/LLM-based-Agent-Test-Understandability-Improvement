package org.threeten.extra.chrono;

import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;

import java.time.DateTimeException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DiscordianChronology_ESTest_test22 extends DiscordianChronology_ESTest_scaffolding {

    /**
     * Verifies that {@link DiscordianChronology#dateYearDay(int, int)} rejects a
     * proleptic-year of 0. Valid years range from 1 to 999999, so year 0 is out
     * of range and the underlying ValueRange check must throw a DateTimeException.
     */
    @Test(timeout = 4000)
    public void dateYearDayRejectsYearZero() throws Throwable {
        DiscordianChronology chronology = new DiscordianChronology();

        int invalidYear = 0;
        int dayOfYear = 0;
        try {
            chronology.dateYearDay(invalidYear, dayOfYear);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            // Invalid value for Year (valid values 1 - 999999): 0
            verifyException("java.time.temporal.ValueRange", e);
        }
    }
}
