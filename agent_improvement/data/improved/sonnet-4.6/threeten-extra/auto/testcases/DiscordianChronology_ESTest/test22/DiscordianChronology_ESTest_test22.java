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
     * Verifies that {@code dateYearDay} rejects year 0, which lies outside the valid
     * Discordian year range of 1–999999. The validation is delegated to
     * {@link java.time.temporal.ValueRange#checkValidIntValue}, so the resulting
     * {@link DateTimeException} originates from that class.
     */
    @Test(timeout = 4000)
    public void test22_dateYearDay_throwsForYearZero() throws Throwable {
        DiscordianChronology chronology = new DiscordianChronology();

        try {
            // Year 0 is invalid; valid Discordian years are 1 to 999999.
            chronology.dateYearDay(0, 0);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            verifyException("java.time.temporal.ValueRange", e);
        }
    }
}
