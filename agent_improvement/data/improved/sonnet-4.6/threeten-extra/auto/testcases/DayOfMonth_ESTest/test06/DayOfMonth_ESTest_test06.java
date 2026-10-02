package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.JapaneseDate;
import java.time.chrono.ThaiBuddhistDate;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalField;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.evosuite.runtime.mock.java.time.MockLocalDateTime;
import org.evosuite.runtime.mock.java.time.MockOffsetDateTime;
import org.evosuite.runtime.mock.java.time.MockYearMonth;
import org.evosuite.runtime.mock.java.time.chrono.MockHijrahDate;
import org.evosuite.runtime.mock.java.time.chrono.MockJapaneseDate;
import org.evosuite.runtime.mock.java.time.chrono.MockMinguoDate;
import org.evosuite.runtime.mock.java.time.chrono.MockThaiBuddhistDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfMonth_ESTest_test06 extends DayOfMonth_ESTest_scaffolding {

    /**
     * Verifies that adjustInto returns the same LocalDateTime instance when the
     * DayOfMonth being applied already matches the day stored in the temporal.
     * Because the DayOfMonth was extracted from the LocalDateTime itself, the
     * with(DAY_OF_MONTH, ...) call inside adjustInto is a no-op and Java's
     * LocalDateTime optimisation returns the original object unchanged.
     */
    @Test(timeout = 4000)
    public void test06_adjustIntoReturnsSameInstanceWhenDayIsUnchanged() throws Throwable {
        // Build a deterministic clock fixed to UTC so the day value is reproducible.
        Clock utcClock = MockClock.system(ZoneOffset.UTC);
        LocalDateTime currentDateTime = MockLocalDateTime.now(utcClock);

        // Extract the day-of-month from the LocalDateTime we just created.
        DayOfMonth dayOfMonth = DayOfMonth.from(currentDateTime);

        // Adjusting a temporal with its own day-of-month must return the exact same object.
        Temporal adjustedTemporal = dayOfMonth.adjustInto(currentDateTime);
        assertSame(adjustedTemporal, currentDateTime);
    }
}
