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
public class DayOfMonth_ESTest_test09 extends DayOfMonth_ESTest_scaffolding {

    /**
     * Verifies that a DayOfMonth extracted from a mocked OffsetDateTime is valid
     * for a mocked YearMonth, and that its numeric value matches the expected
     * day (14) produced by the mock clock.
     */
    @Test(timeout = 4000)
    public void test09() throws Throwable {
        // Arrange: obtain a mocked current OffsetDateTime and extract the day-of-month from it
        OffsetDateTime mockedNow = MockOffsetDateTime.now();
        DayOfMonth dayOfMonth = DayOfMonth.from(mockedNow);

        // Arrange: obtain a mocked current YearMonth to validate against
        YearMonth mockedYearMonth = MockYearMonth.now();

        // Act: check whether this day-of-month is a valid day within the mocked year-month
        boolean isValid = dayOfMonth.isValidYearMonth(mockedYearMonth);

        // Assert: the day is valid for the mocked year-month and its numeric value equals 14
        assertTrue(isValid);
        assertEquals(14, dayOfMonth.getValue());
    }
}
