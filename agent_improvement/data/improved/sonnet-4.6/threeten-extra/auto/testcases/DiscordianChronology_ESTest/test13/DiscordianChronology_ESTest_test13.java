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
     * Verifies that prolepticYear returns the yearOfEra value unchanged
     * when called with the YOLD era obtained from the current date.
     * The Discordian calendar has a single era (YOLD), so prolepticYear
     * simply validates and returns the provided year-of-era as-is.
     */
    @Test(timeout = 4000)
    public void test13() throws Throwable {
        new DiscordianChronology();
        DiscordianDate today = DiscordianDate.now();
        DiscordianEra yoldEra = today.getEra();
        int prolepticYear = DiscordianChronology.INSTANCE.prolepticYear(yoldEra, 47);
        assertEquals(47, prolepticYear);
    }
}
