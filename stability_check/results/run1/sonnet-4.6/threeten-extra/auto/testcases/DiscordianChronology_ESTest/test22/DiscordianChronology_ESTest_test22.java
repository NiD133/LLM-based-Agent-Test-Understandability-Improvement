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
     * Year 0 is outside the valid Discordian year range (1–999999).
     * Calling dateYearDay with year=0 must throw DateTimeException.
     */
    @Test(timeout = 4000)
    public void test22_dateYearDay_throwsOnYearZero() throws Throwable {
        DiscordianChronology chronology = DiscordianChronology.INSTANCE;
        int invalidYear = 0;
        int dayOfYear = 0;

        try {
            chronology.dateYearDay(invalidYear, dayOfYear);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            // Year 0 is below the minimum valid year (1), so ValueRange rejects it
            verifyException("java.time.temporal.ValueRange", e);
        }
    }
}
