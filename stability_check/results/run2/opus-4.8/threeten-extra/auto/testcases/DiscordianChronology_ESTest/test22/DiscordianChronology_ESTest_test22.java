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
     * Year 0 is outside the valid Discordian year range (1 - 999999), so
     * dateYearDay must reject it with a DateTimeException raised while the
     * year value is validated against its ValueRange.
     */
    @Test(timeout = 4000)
    public void dateYearDayRejectsYearZero() throws Throwable {
        DiscordianChronology chronology = new DiscordianChronology();

        int invalidYear = 0;
        int dayOfYear = 0;
        try {
            chronology.dateYearDay(invalidYear, dayOfYear);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException expected) {
            // Invalid value for Year (valid values 1 - 999999): 0
            verifyException("java.time.temporal.ValueRange", expected);
        }
    }
}
