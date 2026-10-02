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
public class DiscordianChronology_ESTest_test13 extends DiscordianChronology_ESTest_scaffolding {

    /**
     * For the Discordian chronology there is only a single era (YOLD), and the
     * proleptic year is defined to be identical to the year-of-era. This test
     * verifies that {@code prolepticYear} simply echoes the supplied year-of-era
     * back when the era is the (only) valid Discordian era.
     */
    @Test(timeout = 4000)
    public void prolepticYearEqualsYearOfEraForDiscordianEra() throws Throwable {
        DiscordianChronology chronology = DiscordianChronology.INSTANCE;

        // The era of any Discordian date is always YOLD, the chronology's single era.
        DiscordianEra yoldEra = DiscordianDate.now().getEra();

        int yearOfEra = 47;
        int prolepticYear = chronology.prolepticYear(yoldEra, yearOfEra);

        assertEquals(yearOfEra, prolepticYear);
    }
}
