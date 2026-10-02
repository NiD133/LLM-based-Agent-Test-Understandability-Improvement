package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.Era;
import java.time.chrono.IsoEra;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.ValueRange;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.evosuite.runtime.mock.java.time.MockOffsetDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry454Chronology_ESTest_test17 extends Symmetry454Chronology_ESTest_scaffolding {

    /**
     * Verifies that Symmetry454Chronology.dateNow(Clock) produces the same date as
     * Symmetry454Date.now(Clock) when both are queried with the same minute-ticking UTC clock.
     * The chronology instance is retrieved from the date itself to ensure consistency.
     */
    @Test(timeout = 4000)
    public void test_dateNow_viaChronology_matchesDirectNow_withMinuteTickingUtcClock() throws Throwable {
        // Use a UTC zone offset and a clock that advances in minute increments
        ZoneOffset utcOffset = ZoneOffset.ofTotalSeconds(0);
        Clock minuteTickingClock = MockClock.tickMinutes(utcOffset);

        // Obtain today's Symmetry454 date directly from the clock
        Symmetry454Date directDate = Symmetry454Date.now(minuteTickingClock);

        // Obtain the same date via the chronology retrieved from that date
        Symmetry454Chronology chronology = directDate.getChronology();
        Symmetry454Date chronologyDate = chronology.dateNow(minuteTickingClock);

        // Both paths with the same clock must yield equal dates
        assertTrue(
            "dateNow(clock) via chronology should equal Symmetry454Date.now(clock)",
            chronologyDate.equals((Object) directDate)
        );
    }
}
