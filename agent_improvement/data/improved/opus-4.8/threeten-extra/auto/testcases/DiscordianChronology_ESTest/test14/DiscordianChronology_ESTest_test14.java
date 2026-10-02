package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DiscordianChronology_ESTest_test14 extends DiscordianChronology_ESTest_scaffolding {

    /**
     * Verifies that {@link DiscordianChronology#isLeapYear(long)} reports a
     * proleptic year as a leap year. The Discordian leap-year rule mirrors the
     * Gregorian one (after an internal offset), so the year -2454 is a leap year.
     */
    @Test(timeout = 4000)
    public void isLeapYearReturnsTrueForLeapYear() throws Throwable {
        DiscordianChronology chronology = new DiscordianChronology();

        boolean isLeapYear = chronology.isLeapYear(-2454L);

        assertTrue("Proleptic year -2454 should be a leap year", isLeapYear);
    }
}
