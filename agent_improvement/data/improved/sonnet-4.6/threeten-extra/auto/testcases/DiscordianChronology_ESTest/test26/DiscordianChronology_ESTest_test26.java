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
public class DiscordianChronology_ESTest_test26 extends DiscordianChronology_ESTest_scaffolding {

    /**
     * Verifies that a date obtained from the Discordian chronology using the
     * system-default-zone clock belongs to the YOLD (Year of Our Lady of Discord) era.
     */
    @Test(timeout = 4000)
    public void test_dateNow_withSystemDefaultZoneClock_returnsYOLDEra() throws Throwable {
        // Constructor call is kept to match original test behaviour (constructor is deprecated but public)
        new DiscordianChronology();

        Clock systemDefaultZoneClock = MockClock.systemDefaultZone();
        DiscordianDate today = DiscordianChronology.INSTANCE.dateNow(systemDefaultZoneClock);

        assertEquals(DiscordianEra.YOLD, today.getEra());
    }
}
