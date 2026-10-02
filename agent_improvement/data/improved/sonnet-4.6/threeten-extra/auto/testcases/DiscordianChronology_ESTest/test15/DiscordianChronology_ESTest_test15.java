package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.Era;
import java.time.chrono.HijrahEra;
import java.time.chrono.JapaneseEra;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.ValueRange;
import java.util.HashMap;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.evosuite.runtime.mock.java.time.MockZonedDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DiscordianChronology_ESTest_test15 extends DiscordianChronology_ESTest_scaffolding {

    /**
     * Discordian year 1266 maps to ISO year 100 (1266 - 1166 = 100).
     * ISO year 100 is divisible by 100 but not by 400, so it is not a leap year
     * under the Gregorian leap-year rule that the Discordian calendar follows.
     */
    @Test(timeout = 4000)
    public void test_isLeapYear_returnsFalse_forCenturyYearNotDivisibleBy400() throws Throwable {
        DiscordianChronology chronology = new DiscordianChronology();
        // Discordian year 1266 corresponds to ISO year 100 (century year, not leap)
        long discordianYear1266 = 1266L;

        boolean isLeap = chronology.isLeapYear(discordianYear1266);

        assertFalse(isLeap);
    }
}
