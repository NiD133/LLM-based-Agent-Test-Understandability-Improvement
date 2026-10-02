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
     * Verifies that dateYearDay rejects year=0, since valid Discordian years are 1–999999.
     * The validation is delegated to ValueRange, which throws DateTimeException.
     */
    @Test(timeout = 4000)
    public void test22_dateYearDay_throwsForYearZero() throws Throwable {
        DiscordianChronology chronology = new DiscordianChronology();
        int invalidYear = 0;
        int dayOfYear = 0;

        try {
            chronology.dateYearDay(invalidYear, dayOfYear);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            // ValueRange rejects year=0 because Discordian years must be in [1, 999999]
            verifyException("java.time.temporal.ValueRange", e);
        }
    }
}
