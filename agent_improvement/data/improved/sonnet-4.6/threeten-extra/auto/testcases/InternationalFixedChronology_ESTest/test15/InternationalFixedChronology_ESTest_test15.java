package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.Era;
import java.time.chrono.JapaneseEra;
import java.time.chrono.ThaiBuddhistEra;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalField;
import java.time.temporal.ValueRange;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InternationalFixedChronology_ESTest_test15 extends InternationalFixedChronology_ESTest_scaffolding {

    /**
     * Verifies that year 100 is not a leap year in the International Fixed chronology.
     * Year 100 is divisible by 4 and by 100, but NOT by 400, so it is skipped as a leap year.
     * A date obtained in the minimum zone offset (UTC-18) confirms the year has 365 days (non-leap).
     */
    @Test(timeout = 4000)
    public void test15() throws Throwable {
        // Arrange: create a date in the westernmost zone offset (UTC-18:00)
        ZoneOffset minZoneOffset = ZoneOffset.MIN;
        InternationalFixedDate today = InternationalFixedDate.now((ZoneId) minZoneOffset);
        InternationalFixedChronology chronology = today.getChronology();

        // Act: check whether year 100 is a leap year
        boolean year100IsLeap = chronology.isLeapYear(100L);

        // Assert: year 100 is not a leap year (divisible by 100 but not by 400),
        // and the current date's year has the standard 365 days
        assertEquals(365, today.lengthOfYear());
        assertFalse(year100IsLeap);
    }
}
