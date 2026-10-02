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
public class DayOfMonth_ESTest_test14 extends DayOfMonth_ESTest_scaffolding {

    /**
     * Verifies that DayOfMonth obtained from the system clock supports the
     * DAY_OF_MONTH field and that its value equals the mocked current day (14).
     */
    @Test(timeout = 4000)
    public void test_nowReturnsDayOfMonth14_andSupportsDayOfMonthField() throws Throwable {
        // The EvoSuite mock clock is fixed so that DayOfMonth.now() returns day 14
        DayOfMonth currentDay = DayOfMonth.now();

        // DAY_OF_MONTH is the only ChronoField supported by DayOfMonth
        boolean isDayOfMonthSupported = currentDay.isSupported(ChronoField.DAY_OF_MONTH);
        assertTrue(isDayOfMonthSupported);

        assertEquals(14, currentDay.getValue());
    }
}
